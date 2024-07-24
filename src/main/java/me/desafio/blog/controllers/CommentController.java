package me.desafio.blog.controllers;


import jakarta.validation.Valid;
import me.desafio.blog.dto.ApiResponse;
import me.desafio.blog.dto.PaginationRespone;
import me.desafio.blog.dto.publication.CommentCreateDTO;
import me.desafio.blog.dto.publication.CommentDTO;
import me.desafio.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<CommentDTO>> getComments(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size) {
        var comments = commentService.getComments(page, size);

        return ResponseEntity.ok(new ApiResponse<>(PaginationRespone.fromPage(comments), comments.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable String id) {

        var comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);

    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody @Valid CommentCreateDTO commentDTO) {
        var comment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(comment);
    }
}
