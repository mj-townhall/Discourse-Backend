package com.townhall.discourse.dao;

import com.townhall.discourse.entities.CommentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDataDao extends JpaRepository<CommentData,Integer> {
}
