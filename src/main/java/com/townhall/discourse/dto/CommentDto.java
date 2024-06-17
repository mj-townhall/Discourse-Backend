package com.townhall.discourse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private int id;
    private long timeStampMillis;
    private String content;
    private int userId;
    private int postId;
    private String email;
    private String firstName;
    private String lastName;
}
