package com.townhall.discourse.service;

import com.townhall.discourse.dao.CommentDataDao;
import com.townhall.discourse.dao.PostDataDao;
import com.townhall.discourse.dto.CommentDto;
import com.townhall.discourse.dto.PostDto;
import com.townhall.discourse.dto.Status;
import com.townhall.discourse.entities.CommentData;
import com.townhall.discourse.entities.PostData;
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
        if(postToBeUpdated!=null){
            postToBeUpdated=postData;
            entityManager.merge(postData);
            return Status.builder().id(postData.getId()).message("post updated with id = "+postData.getId()).build();
        }
        throw new AppException("no post found with id = "+postData.getId(), HttpStatus.BAD_REQUEST);

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
        List<CommentData> commentDataList=commentDataDao.findCommentsByPostId(postId);
        List<CommentDto> commentDtoList=new ArrayList<>();
        for(CommentData c:commentDataList){
            CommentDto commentDto=userMapper.toCommentDto(c);
            commentDto.builder()
                    .userId(c.getUserData().getId())
                    .postId(c.getPostData().getId())
                    .email(c.getUserData().getEmail())
                    .firstName(c.getUserData().getFirstName())
                    .lastName(c.getUserData().getLastName())
                    .build();
            commentDtoList.add(commentDto);
        }
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
