package com.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
