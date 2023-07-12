package com.jdbc.demo.service;

import org.springframework.stereotype.Service;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;

@Service
public interface SignUpService {

	public Response createUser(SignUpModel value);

	public Response updateUser(SignUpModel value);

	public Response deleteUser(String sNo);

	public Response getUser();

	public Response getOneUser(String sNo);

	public Response scamUser(String sNo);
	
	public Response userLogin(SignUpModel value);
}
