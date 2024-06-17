package com.townhall.discourse.service;

import com.townhall.discourse.dao.PostDataDao;
import com.townhall.discourse.dto.PostDto;
import com.townhall.discourse.dto.Status;
import com.townhall.discourse.entities.PostData;
import com.townhall.discourse.mapper.UserMapper;
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
        postData.setTimeStampMillis(System.currentTimeMillis());
        postDataDao.save(postData);
        return Status.builder()
                .id(postData.getId())
                .message("Post succesfully added with id = "+postData.getId())
                .build();
    }
//    public static void main(String[] args){
//        PostData postData=PostData.builder()
//                .content("mkdnkndc")
//                .timeStampMillis(System.currentTimeMillis())
//                .build();
//        PostService postService=new PostService();
//        Status status=postService.addPost(postData);
//        System.out.println(status.getMessage());
//    }
}
