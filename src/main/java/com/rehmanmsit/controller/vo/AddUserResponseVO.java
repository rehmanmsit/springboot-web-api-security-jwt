package com.rehmanmsit.controller.vo;

import com.rehmanmsit.repository.entity.User;

import lombok.Data;

/**
*
* @author Rehman
*
*/

@Data
public class AddUserResponseVO {
	
	private String message;
	
	private User user;

}
