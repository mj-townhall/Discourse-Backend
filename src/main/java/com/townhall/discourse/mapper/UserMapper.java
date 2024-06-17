package com.townhall.discourse.mapper;

import com.townhall.discourse.dto.CommentDto;
import com.townhall.discourse.dto.PostDto;
import com.townhall.discourse.dto.RegisterDto;
import com.townhall.discourse.dto.UserDto;
import com.townhall.discourse.entities.CommentData;
import com.townhall.discourse.entities.PostData;
import com.townhall.discourse.entities.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

    @Mapper(componentModel = "spring")
    public interface UserMapper {

        UserDto toUserDto(UserData userData);


        @Mapping(target = "userId", ignore = true)
        PostDto toPostDto(PostData postData);

        @Mapping(target = "userId", ignore = true)
        @Mapping(target = "postId", ignore = true)
        CommentDto toCommentDto(CommentData commentData);

        @Mapping(target = "password", ignore = true)
        UserData signUpToUser(RegisterDto registerDto);

    }

