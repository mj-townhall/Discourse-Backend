package com.townhall.discourse.controller;

import com.townhall.discourse.dto.PostDto;
import com.townhall.discourse.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class AppController {
    @Autowired
    PostService postService;

    @GetMapping("/posts")
    ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> postDtoList=postService.getAllPosts();
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }
}
