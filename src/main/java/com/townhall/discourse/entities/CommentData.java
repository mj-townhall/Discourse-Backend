package com.townhall.discourse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CommentData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserData userData;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private PostData postData;

    @Column(nullable = false)
    private long timeStampMillis;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

}
