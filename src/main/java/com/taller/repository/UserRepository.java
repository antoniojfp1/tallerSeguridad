package com.taller.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	public Optional<UserEntity> findByUsernameAndPassword(String username, String password);
	public Optional<UserEntity> findByUsername(String username);

}
