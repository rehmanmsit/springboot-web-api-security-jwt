package com.rehmanmsit.repository.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
*
* @author Rehman
*
*/

@Document
@Data
public class User {

	@Id
	private String id;

	private String name;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String designation;

	private List<Role> roles;

}
