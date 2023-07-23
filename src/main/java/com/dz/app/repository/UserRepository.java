package com.dz.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dz.app.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findByEmailIgnoreCase(String emial);
	
	@Query("select u from UserEntity u where u.email =:email")
	public UserEntity getUserByUserName(@Param("email") String email);

}
