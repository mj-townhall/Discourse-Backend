package com.townhall.discourse.dao;

import com.townhall.discourse.entities.PostVoteData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostVoteDataDao extends JpaRepository<PostVoteData,Integer> {

//    @Query("SELECT p FROM PostVoteData p WHERE p.postId = :userId")
    PostVoteData findByPostIdAndUserId(int postId, int userId);
}
