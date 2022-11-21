package com.blog.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.CategoryNotFoundException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.exceptions.UserNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repo.CategoryRepo;
import com.blog.repo.PostRepo;
import com.blog.repo.UserRepo;
import com.blog.service.FileService;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileService fileService;
	
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("User", "UserId", userId));
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new CategoryNotFoundException("Category", "CategoryId", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("Default.png");
		post.setAddedDate(new Date(25));
		post.setUser(user);
		post.setCategories(cat);
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost,PostDto.class);
		
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId :", postId)) ;
		post.setPostTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setAddedDate(postDto.getAddedDate());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost,PostDto.class);
		
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId :", postId)) ;
		this.postRepo.delete(post);
	}

 
	public PostResponse getAllPost(Integer pageNumber , Integer pageSize, String sortBy, String sortDirection) {
		
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).descending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
	
		org.springframework.data.domain.Pageable pagesConcept = PageRequest.of(pageNumber,pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pagesConcept);		
	List<Post> allPosts = pagePost.getContent();
	
	List<PostDto> allPostDto = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	
	PostResponse postResponse = new PostResponse();
	postResponse.setContent(allPostDto);
	postResponse.setPageNumber(pagePost.getNumber());
	postResponse.setPageSize(pagePost.getSize());
	postResponse.setTotalElements(pagePost.getTotalElements());
	postResponse.setTotalPages(pagePost.getTotalPages());
	postResponse.setLastPage(pagePost.isLast());
	
	return postResponse;
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
//		Post post = this.postRepo.getById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
//		
//		return this.modelMapper.map(post,PostDto.class);
		Post post = this.postRepo.getById(postId);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(null, null, categoryId));
		List<Post> posts = this.postRepo.findByCategories(cat);
		
		List<PostDto> allPostDto = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPostDto;
			
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","User_Id :" , userId));
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> allPostDto = posts.stream().map((post)-> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		
		return allPostDto;
		
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> allPosts = this.postRepo.findByPostTitle("%"+keyword+"%");
		List<PostDto> allPostDto = allPosts.stream().map((posts)-> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return allPostDto;
		
	}
	

	
}
