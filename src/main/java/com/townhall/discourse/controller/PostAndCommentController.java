package com.townhall.discourse.controller;

import com.townhall.discourse.dto.CommentDto;
import com.townhall.discourse.dto.PostDto;
import com.townhall.discourse.dto.Status;
import com.townhall.discourse.entities.CommentData;
import com.townhall.discourse.entities.PostData;
import com.townhall.discourse.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostAndCommentController {
    @Autowired
    PostService postService;

    @GetMapping("/post")
    ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> postDtoList=postService.getAllPosts();
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/post/{userId}")
    ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId){
        System.out.println(userId);
        List<PostDto> postDtoList=postService.getPostsByUser(userId);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @PostMapping("/post")
    ResponseEntity<Status> addPost(@RequestBody PostData postData){
        Status status=postService.addPost(postData);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
    @PostMapping("/comment")
    ResponseEntity<Status> addComment(@RequestBody CommentData commentData){
        Status status=postService.addComment(commentData);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    ResponseEntity<Status> deletePost(@PathVariable int postId){
        Status status = postService.deletePost(postId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
    @DeleteMapping("/comment/{cId}")
    ResponseEntity<Status> deleteComment(@PathVariable int commentId){
        Status status = postService.deleteComment(commentId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/comment/{postId}")
    ResponseEntity<List<CommentDto>> getComments(@PathVariable int postId){
        List<CommentDto> commentDtoList=postService.getComments(postId);
        return new ResponseEntity<>(commentDtoList,HttpStatus.OK);
    }

    @PutMapping("/post")
    ResponseEntity<Status> editPost(@RequestBody PostData postData){
        Status status=postService.editPost(postData);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
