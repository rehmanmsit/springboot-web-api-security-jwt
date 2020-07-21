package com.rehmanmsit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rehmanmsit.controller.vo.AddUserRequestVO;
import com.rehmanmsit.controller.vo.AddUserResponseVO;
import com.rehmanmsit.repository.UserRepository;
import com.rehmanmsit.repository.entity.User;

/**
*
* @author Rehman
*
*/

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping(value = "/addUser",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public AddUserResponseVO handleInitialPostRequest(@RequestBody AddUserRequestVO request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setRoles(request.getRoles());
		user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		user.setDesignation(request.getDesignation());
		if(null != userRepo.save(user)) {
			AddUserResponseVO response = new AddUserResponseVO();
			response.setMessage("Insertion Successful");
			response.setUser(user);
			return response;
		} else {
			AddUserResponseVO response = new AddUserResponseVO();
			response.setMessage("Insertion not Successful, Please try again");
			response.setUser(null);
			return response;
		}
	}

}
