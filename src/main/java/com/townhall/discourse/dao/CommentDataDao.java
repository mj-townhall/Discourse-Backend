package com.townhall.discourse.dao;

import com.townhall.discourse.dto.CommentDto;
import com.townhall.discourse.entities.CommentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentDataDao extends JpaRepository<CommentData,Integer> {

    @Query("SELECT NEW com.townhall.discourse.dto.CommentDto(" +
            "c.id, " +
            "c.createdAt, " +
            "c.content, " +
            "c.userData.id, " +
            "c.postId, " +
            "u.email, " +
            "u.firstName, " +
            "u.lastName, " +
            "c.votes) " +
            "FROM CommentData c LEFT JOIN UserData u ON u.id = c.userData.id WHERE c.postId = :postId")
    List<CommentDto> findCommentsByPostId(@Param("postId") int postId);
}
