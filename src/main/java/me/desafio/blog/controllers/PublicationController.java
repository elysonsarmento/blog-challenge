package me.desafio.blog.controllers;


import jakarta.validation.Valid;
import me.desafio.blog.dto.ApiResponse;
import me.desafio.blog.dto.PaginationRespone;
import me.desafio.blog.dto.publication.PublicationCreateDTO;
import me.desafio.blog.dto.publication.PublicationDTO;
import me.desafio.blog.infra.JwtUtil;
import me.desafio.blog.service.PublicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/publication")
public class PublicationController {

    private final PublicationService publicationService;
    private final JwtUtil jwtService;

    public PublicationController(PublicationService publicationService, JwtUtil jwtService) {
        this.publicationService = publicationService;
        this.jwtService = jwtService;
    }


    @PostMapping
    public ResponseEntity<PublicationDTO> createPublication(@RequestBody @Valid PublicationCreateDTO publicationDTO, @RequestHeader("Authorization") String token, UriComponentsBuilder uriBuilder) {
        String userIdFromToken = jwtService.extractUserId(
                token.replace("Bearer ", "")
        );

        if (!userIdFromToken.equals(publicationDTO.authorId())) {
            return ResponseEntity.status(403).build();
        }

        PublicationDTO publication = publicationService.createPublication(publicationDTO);

        URI uri = uriBuilder.path("/publication/{id}").buildAndExpand(publication.id()).toUri();

        return ResponseEntity.created(uri).body(publication);


    }

    @GetMapping
    public ResponseEntity<ApiResponse<PublicationDTO>> getPublications(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size) {
        var publications = publicationService.getPublications(page, size);

        return ResponseEntity.ok(new ApiResponse<>(

                PaginationRespone.fromPage(publications), publications.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublicationById(@PathVariable String id) {

        var publication = publicationService.getPublicationById(id);
        return ResponseEntity.ok(publication);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicationById(@PathVariable String id) {
        publicationService.deletePublicationById(id);
        return ResponseEntity.noContent().build();
    }


}
