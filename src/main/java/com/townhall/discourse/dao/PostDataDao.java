package com.townhall.discourse.dao;

import com.townhall.discourse.entities.PostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDataDao extends JpaRepository<PostData,Integer> {
    List<PostData> findAll();
    @Query("SELECT p FROM PostData p WHERE p.userData.id = :userId")
    List<PostData> findPostsByUserId(@Param("userId") int userId);
}
