package com.townhall.discourse.service;

import com.townhall.discourse.dao.CommentDataDao;
import com.townhall.discourse.dao.PostDataDao;
import com.townhall.discourse.dao.PostVoteDataDao;
import com.townhall.discourse.dto.CommentDto;
import com.townhall.discourse.dto.PostDto;
import com.townhall.discourse.dto.Status;
import com.townhall.discourse.entities.CommentData;
import com.townhall.discourse.entities.PostData;
import com.townhall.discourse.entities.PostVoteData;
import com.townhall.discourse.entities.VoteType;
import com.townhall.discourse.exception.AppException;
import com.townhall.discourse.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    @Autowired
    PostDataDao postDataDao;
    @Autowired
    CommentDataDao commentDataDao;
    @Autowired
    PostVoteDataDao postVoteDataDao;

    @PersistenceContext
    private EntityManager entityManager;
    private final UserMapper userMapper;
    public List<PostDto> getAllPosts(){
        List<PostData> postDataList=postDataDao.findAll();
        List<PostDto> postDtoList=new ArrayList<>();
        for(PostData p:postDataList){
            PostDto postDto= userMapper.toPostDto(p);
            postDto.setUserId(p.getUserData().getId());
            postDto.setEmail(p.getUserData().getEmail());
            postDto.setFirstName(p.getUserData().getFirstName());
            postDto.setLastName(p.getUserData().getLastName());
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    public List<PostDto> getPostsByUser(int userId){
        List<PostData> postDataList=postDataDao.findPostsByUserId(userId);
        List<PostDto> postDtoList=new ArrayList<>();
        for(PostData p:postDataList){
            PostDto postDto= userMapper.toPostDto(p);
            postDto.setUserId(p.getUserData().getId());
            postDto.setEmail(p.getUserData().getEmail());
            postDto.setFirstName(p.getUserData().getFirstName());
            postDto.setLastName(p.getUserData().getLastName());
            postDtoList.add(postDto);
        }
        return postDtoList;
    }
    public Status addPost(PostData postData){
        try {
            postData.setCreatedAt(System.currentTimeMillis());
            postDataDao.save(postData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Status.builder()
                .id(postData.getId())
                .message("Post succesfully added with id = "+postData.getId())
                .build();
    }

    @Transactional
    public Status editPost(PostData postData){
        PostData postToBeUpdated=entityManager.find(PostData.class,postData.getId());
        String message="";
        if(postToBeUpdated!=null){
            int voteChange = postData.getVotes()-postToBeUpdated.getVotes();
            if(voteChange!=0){
                voteChange= handleVoteUpdateForPost(postData.getId(),postData.getEditBy(), voteChange); // allowed no of vote change
                if(voteChange==0)message="already voted";
            }
            postToBeUpdated.setVotes(postToBeUpdated.getVotes()+voteChange);
            postToBeUpdated.setContent(postData.getContent());
            postToBeUpdated.setTitle(postData.getTitle());
            entityManager.merge(postToBeUpdated);
            return Status.builder().id(postData.getId()).message(message.isEmpty()? "post updated succesfully":message ).build();
        }
        throw new AppException("no post found with id = "+postData.getId(), HttpStatus.BAD_REQUEST);
    }

    public int handleVoteUpdateForPost(int postId, int userId, int voteChange){
        PostVoteData postVoteData=postVoteDataDao.findByPostIdAndUserId(postId,userId);
        PostVoteData updatedPostVoteData=postVoteData;
        if(postVoteData!=null){
            if(voteChange>0) {
                if (postVoteData.getVoteType().getValue() == 1){
                    return 0; //already upvoted
                }
                else if(postVoteData.getVoteType().getValue()==0){
                    updatedPostVoteData.setVoteType(VoteType.UPVOTE);
                    entityManager.merge(updatedPostVoteData);
                    return 1; // first vote
                }
                else{
                    updatedPostVoteData.setVoteType(VoteType.UPVOTE);
                    entityManager.merge(updatedPostVoteData);
                    return 2;  // upvoting after a downvote
                }
            }
            else if(voteChange<0){
                if (postVoteData.getVoteType().getValue() == 1){
                    updatedPostVoteData.setVoteType(VoteType.DOWNVOTE);
                    entityManager.merge(updatedPostVoteData);
                    return -2;  //down voting after an upvote
                }
                else if(postVoteData.getVoteType().getValue()==0){
                    updatedPostVoteData.setVoteType(VoteType.DOWNVOTE);
                    entityManager.merge(updatedPostVoteData);
                    return -1; // first vote
                }
                else return 0; //already down voted
            }
            else return 0;
        }
        else {
            PostVoteData newPostVoteData=PostVoteData.builder()
                    .voteType(VoteType.fromValue(voteChange))
                    .postId(postId)
                    .userId(userId)
                    .build();
            postVoteDataDao.save(newPostVoteData);
            return voteChange;
        }
    }

    public Status deletePost(int postId){
        try {
            postDataDao.deleteById(postId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Status status= Status.builder()
                .id(postId)
                .message("post succesfully deleted with id = "+postId)
                .build();
        return  status;
    }
    public Status addComment(CommentData commentData){
        try {
            commentData.setCreatedAt(System.currentTimeMillis());
            commentDataDao.save(commentData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Status.builder().id(commentData.getId()).
                message("Comment succesfully added by id = "+ commentData.getId())
                .build();

    }
    public List<CommentDto> getComments(int postId){
        List<CommentDto> commentDtoList=commentDataDao.findCommentsByPostId(postId);
        return commentDtoList;
    }

    public Status deleteComment(int commentId){
        try {
            commentDataDao.deleteById(commentId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Status status= Status.builder()
                .id(commentId)
                .message("post succesfully deleted with id = "+commentId)
                .build();
        return  status;
    }
}
