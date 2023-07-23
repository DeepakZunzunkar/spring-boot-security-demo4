package com.dz.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dz.app.entity.BaseProperties;
import com.dz.app.entity.UserEntity;
import com.dz.app.repository.UserRepository;
import com.dz.app.utility.DateUtils;

@RestController
@RequestMapping("restuser")
public class UserRestController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public ResponseEntity<List<UserEntity>> getAllEmployees() {
		List<UserEntity> userList = null;
		try {
			userList = userRepository.findAll();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.of(Optional.of(userList));
	}

	@GetMapping(value = "{uid}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable("uid") Long id) {

		Optional<UserEntity> user = userRepository.findById(id);
		if (user != null && user.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(user.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(value = "{pagenumber}/{pagesize}")
	public ResponseEntity<Page<UserEntity>> getAllUserByid(@PathVariable("pagenumber") Integer pageNumber,
			@PathVariable("pagesize") Integer pageSize) {
		Page<UserEntity> findAll = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
		if (findAll.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(findAll);
	}

	@PostMapping
	public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
		try {

			UserEntity sqlEmps = userRepository.findByEmailIgnoreCase(user.getEmail());
			if (sqlEmps != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			} else {
				user.setBaseProperties(new BaseProperties("A",DateUtils.convertJUtilDateTimeToString(new Date()), "spring-boot-security-demo4", null, null));
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				UserEntity save = userRepository.save(user);
				return ResponseEntity.status(HttpStatus.CREATED).body(save);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping
	public ResponseEntity<UserEntity> updateEmployee(@RequestBody UserEntity trn) {
		try {
			if (trn.getId() != null) {

				Optional<UserEntity> trnOptional = userRepository.findById(trn.getId());
				if (trnOptional != null && trnOptional.isPresent()) {

					UserEntity trnSql = trnOptional.get();
					trnSql.getBaseProperties().setUpdatedBy("spring-boot-security-demo4");
					trnSql.getBaseProperties().setUpdatedOn(DateUtils.convertJUtilDateTimeToString(new Date()));

					trnSql.setEmail(trn.getEmail());
					trnSql.setUsername(trn.getUsername());
					trnSql.setPassword(passwordEncoder.encode(trn.getPassword()));
					trnSql.setRole(trn.getRole());
					userRepository.save(trnSql);
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(trnSql);
				} else {
					return ResponseEntity.status(HttpStatus.CONFLICT).build();
				}

			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("{uid}")
	public ResponseEntity<Boolean> deleteEmployee(@PathVariable("uid") Long id) {

		try {
			UserEntity sqlUser = userRepository.findById(id).orElse(null);
			if (sqlUser != null) {
				userRepository.delete(sqlUser);
				return ResponseEntity.ok().body(true);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
