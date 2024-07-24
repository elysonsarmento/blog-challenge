package me.desafio.blog.service;

import me.desafio.blog.dto.publication.PublicationCreateDTO;
import me.desafio.blog.dto.publication.PublicationDTO;
import me.desafio.blog.infra.exception.PublicationNotException;
import me.desafio.blog.repositories.PublicationRepository;
import me.desafio.blog.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PublicationService {

    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    public PublicationService(UserRepository userRepository, PublicationRepository publicationRepository) {
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
    }


    public Page<PublicationDTO> getPublications(Integer page, Integer size) {
        var pageable = PageRequest.of(page - 1, size);
        var publications = publicationRepository.findAll(pageable);
        return publications.map(PublicationDTO::publicationEntity);
    }

    public PublicationDTO createPublication(PublicationCreateDTO publicationDTO) {
        var user = userRepository.findById(publicationDTO.authorId()).orElseThrow(() -> new PublicationNotException(publicationDTO.authorId()));
        var publication = publicationDTO.publicationEntity();
        publication.setAuthorId(user.getId());
        return PublicationDTO.publicationEntity(publicationRepository.save(publication));

    }

    public PublicationDTO getPublicationById(String id) {
        var publication = publicationRepository.findById(id).orElseThrow(() -> new PublicationNotException(id));
        return PublicationDTO.publicationEntity(publication);
    }

    public void deletePublicationById(String id) {
        if (!publicationRepository.existsById(id)) {
            throw new PublicationNotException(id);
        }
        publicationRepository.deleteById(id);
    }
}
