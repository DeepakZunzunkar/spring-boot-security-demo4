package com.dz.app.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class UserEntity {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;
	private String email;
	private String role;
	

    @Embedded
    	@AttributeOverrides({
    		@AttributeOverride(name="createdon",column=@Column(name="CREATEDON",updatable= false))
    	})
    private BaseProperties baseProperties;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public BaseProperties getBaseProperties() {
		return baseProperties;
	}


	public void setBaseProperties(BaseProperties baseProperties) {
		this.baseProperties = baseProperties;
	}
    
    
}
