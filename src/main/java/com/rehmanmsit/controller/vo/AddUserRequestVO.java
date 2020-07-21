package com.rehmanmsit.controller.vo;

import java.util.List;

import com.rehmanmsit.repository.entity.Role;

import lombok.Data;

/**
*
* @author Rehman
*
*/

@Data
public class AddUserRequestVO {

	private String name;
	
	private String email;
	
	private String password;
	
	private String designation;
	
	private List<Role> roles;

}