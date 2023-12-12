package com.springbootapi.service;

import com.springbootapi.entity.Post;
import com.springbootapi.payload.PostDto;
import com.springbootapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);

    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);


}
