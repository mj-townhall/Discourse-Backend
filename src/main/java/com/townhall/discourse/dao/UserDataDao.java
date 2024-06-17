package com.townhall.discourse.dao;

import com.townhall.discourse.entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataDao extends JpaRepository<UserData,Integer> {
  UserData getByEmail(String email);
  boolean existsByEmail(String email);
}
