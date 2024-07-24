package me.desafio.blog.service;

import me.desafio.blog.dto.publication.CommentCreateDTO;
import me.desafio.blog.dto.publication.CommentDTO;
import me.desafio.blog.repositories.CommentRepository;
import me.desafio.blog.repositories.PublicationRepository;
import me.desafio.blog.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CommentService {

    private Logger logger = Logger.getLogger(CommentService.class.getName());

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PublicationRepository publicationRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
    }


    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }

    public CommentDTO createComment(CommentCreateDTO commentDTO) {
        if (!userRepository.existsById(commentDTO.authorId())) {
            throw new IllegalArgumentException("Author not found");
        }
        if (!publicationRepository.existsById(commentDTO.publicationId())) {
            throw new IllegalArgumentException("Publication not found");
        }

        var publication = publicationRepository.findById(commentDTO.publicationId());


        publicationRepository.save(publication.get());

        logger.info("Publication found: " + publication.get().getId());

        var comment = commentRepository.save(commentDTO.commentEntity());

        logger.info("Comment created: " + comment.getId());

        comment.setPublication(publication.get());

        publication.get().addComment(comment.getId());

        publicationRepository.save(publication.get());
        return CommentDTO.commentEntity(comment, publication.get());


    }

    public Page<CommentDTO> getComments(Integer page, Integer size) {
        var pageable = PageRequest.of(page - 1, size);
        var comments = commentRepository.findAll(pageable);
        return comments.map(comment -> {
            var publication = publicationRepository.findById(comment.getPublication().getId());
            return CommentDTO.commentEntity(comment, publication.get());
        });
    }

    public CommentDTO getCommentById(String id) {
        var comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Comment not found")
        );
        var publication = publicationRepository.findById(comment.getPublication().getId());
        return CommentDTO.commentEntity(comment, publication.get());
    }


}
