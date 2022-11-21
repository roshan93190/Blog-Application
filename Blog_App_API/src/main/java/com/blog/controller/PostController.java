package com.blog.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstant;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.service.FileService;
import com.blog.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService; 
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/categories/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			)
	{
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
//	get post by user details
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}

//	Get posts by category details	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
// Get all posts 
	
	@GetMapping("posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE ,required = false)Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY ,required = false)String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = AppConstant.SORT_DIR,required = false)String sortDirection
			
			){
		 PostResponse postResponse= this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDirection);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
//	get post detail by post_id;
	@GetMapping("posts/{postId}")
	public ResponseEntity<PostDto> getPostDetailById(@PathVariable Integer postId){
		PostDto post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
//	Delete post by postId
	@DeleteMapping("posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post delted successfully",true);
	}

	
//	update post by postId
	
	@PutMapping("posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId){
		PostDto post = this.postService.updatePost(postDto,postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.ACCEPTED);
	}
//	Search post by title
	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		List<PostDto> allPosts = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
	}
	
// Post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto,postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	
	@GetMapping(value ="/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public String  downloadImage( @PathVariable String imageName,
		HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	return "Post got successfully";
	}
}
