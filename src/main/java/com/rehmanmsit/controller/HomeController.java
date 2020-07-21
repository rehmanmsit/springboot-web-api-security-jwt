package com.rehmanmsit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rehmanmsit.repository.UserRepository;
import com.rehmanmsit.repository.entity.User;

/**
*
* @author Rehman
*
*/

@RestController
@RequestMapping("/user")
public class HomeController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/getUsers")
	public List<User> getUser() {
		return userRepo.findAll();
	}

}
