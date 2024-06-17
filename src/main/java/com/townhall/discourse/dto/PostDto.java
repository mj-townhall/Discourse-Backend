package com.townhall.discourse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private int id;
    private long timeStampMillis;
    private String content;
    private int userId;
    private String email;
    private String firstName;
    private String LastName;
}
