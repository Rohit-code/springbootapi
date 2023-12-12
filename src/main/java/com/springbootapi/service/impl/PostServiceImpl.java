package com.springbootapi.service.impl;

import com.springbootapi.entity.Category;
import com.springbootapi.entity.Post;
import com.springbootapi.exception.ResourceNotFoundException;
import com.springbootapi.payload.PostDto;
import com.springbootapi.payload.PostResponse;
import com.springbootapi.repository.CategoryRepository;
import com.springbootapi.repository.PostRepository;
import com.springbootapi.service.PostService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper mapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository=categoryRepository;
    }

    @Override
    @Transactional
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->
                new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToDto(newPost);
        return postResponse;

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> ListOfPosts = posts.getContent();
        List<PostDto> content =  ListOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","Id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updateedPost = postRepository.save(post);
        return mapToDto(updateedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","Id",id));
        postRepository.delete(post);

    }

    //converted entity into dto
    private PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        /*PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());*/
        return postDto;
    }
    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
        /*Post post = new Post();

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/
        return post;
    }

}
