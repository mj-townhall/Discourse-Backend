package com.townhall.discourse.dao;

import com.townhall.discourse.entities.CommentData;
import com.townhall.discourse.entities.PostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentDataDao extends JpaRepository<CommentData,Integer> {

    @Query("SELECT c FROM CommentData c WHERE c.postData.id = :postId")
    List<CommentData> findCommentsByPostId(@Param("postId") int postId);
}
