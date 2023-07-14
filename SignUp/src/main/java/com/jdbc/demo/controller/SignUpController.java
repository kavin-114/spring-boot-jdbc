package com.jdbc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.demo.dao.SignUpDao;
import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;

@RestController("*")
@CrossOrigin
@RequestMapping("/auth")
public class SignUpController {

	@Autowired
	SignUpDao dao;

	@PostMapping("/create")
	public ResponseEntity<Response> createUser(@RequestBody SignUpModel value) {
		
		return ResponseEntity.ok(dao.createUser(value));
	}
	@PostMapping("/verify")
	public ResponseEntity<Response> verifyUser(@RequestParam String otp) {

		return ResponseEntity.ok(dao.verifyUser(otp));
	}

	@PutMapping("/update")
	public ResponseEntity<Response> updateUser(@RequestBody SignUpModel value) {

		return ResponseEntity.ok(dao.updateUser(value));
	}

	@GetMapping("/read")
	public ResponseEntity<Response> getUser() {

		return ResponseEntity.ok(dao.getUser());
	}

	@GetMapping("/getUser")
	public ResponseEntity<Response> getOneUser(@RequestParam String sNo) {

		return ResponseEntity.ok(dao.getOneUser(sNo));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteUser(@RequestParam String sNo) {

		return ResponseEntity.ok(dao.deleteUser(sNo));
	}

	@DeleteMapping("/scam")
	public ResponseEntity<Response> scamUser(@RequestParam String sNo) {

		return ResponseEntity.ok(dao.scamUser(sNo));
	}

	// User Login
	@PostMapping("/login")
	public ResponseEntity<Response> userLogin(@RequestBody SignUpModel value) {

		return ResponseEntity.ok(dao.userLogin(value));
	}

	// Sending OTP mail
	@PostMapping("/otp")
	public ResponseEntity<Response> otpMail(@RequestParam String toEmail) {
		
		return ResponseEntity.ok(dao.otpMail(toEmail));
	}

}
