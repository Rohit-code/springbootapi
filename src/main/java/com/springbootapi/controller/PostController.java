package com.springbootapi.controller;



import com.springbootapi.payload.PostDto;
import com.springbootapi.payload.PostResponse;
import com.springbootapi.service.PostService;
import com.springbootapi.service.impl.PostServiceImpl;
import com.springbootapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CURD Rest API for Post Resource"
)
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Add @PostMapping annotation to map the HTTP POST method
    @PostMapping
    @CrossOrigin
    @Operation(
            summary = "Create Post Rest API",
            description = "Create Post Rest API is used to create and save post in database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 created"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping
    @Operation(
            summary = "Get all Posts",
            description = "Get All Posts that are created in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("{Id}")
    @Operation(
            summary = "Get Post By ID",
            description = "Get Post By the ID that are created in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    public ResponseEntity<PostDto> getPosById(@PathVariable(name = "Id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Update Post by Id",
            description = "Update the Post and it gets Updated In database by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto postResonse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResonse, HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Delete Post by Id",
            description = "Delete the Post and it gets Deleted In database by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted",HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Get Post by Id",
            description = "Get the Post from database by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    public ResponseEntity<List<PostDto>> getPostById(@PathVariable("id") Long categoryId){
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}
