package com.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer>  {

	List<Post> findByUser(User user);
	List<Post> findByCategories(Category category);
	
	@Query("Select p from Post p where p.postTitle like :key")
	List<Post> findByPostTitle(@Param("key") String title);
	
}
