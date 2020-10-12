package com.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
