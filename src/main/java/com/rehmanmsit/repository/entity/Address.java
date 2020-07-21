package com.rehmanmsit.repository.entity;

import lombok.Data;

/**
*
* @author Rehman
*
*/

@Data
public class Address {

	private String addressLine1;

	private String addressLine2;

	private String street;

	private String city;

	private String district;

	private String state;

	private String country;

	private String pincode;

}