package com.townhall.discourse.controller;

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
@RequestMapping("/user")
public class UserController {
    @Autowired
    PostService postService;

    @GetMapping("/posts/{userId}")
    ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId){
        System.out.println(userId);
        List<PostDto> postDtoList=postService.getPostsByUser(userId);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @PostMapping("/addPost")
    ResponseEntity<Status> addPost(@RequestBody PostData postData){
        Status status=postService.addPost(postData);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
    @PostMapping("/addComment")
    CommentData addComment(@RequestBody CommentData commentData){
        return commentData;
    }

    @DeleteMapping("/delete/{postId}")
    ResponseEntity<Status> deletePost(@PathVariable int postId){
        Status status= Status.builder()
                .id(postId)
                .message("post succesfully deleted with id = "+postId)
                .build();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


}