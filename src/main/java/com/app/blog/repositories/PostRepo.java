package com.app.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.blog.entities.Category;
import com.app.blog.entities.Post;
import com.app.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByCategoryTitle(String title);
	List<Post> findByUserId(Integer userid);
	
	//method to find by title
	List<Post> findByPostTitleContaining(String keyword);
}
