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

    @Column(nullable = false)
    private int postId;

    @Column(nullable = false)
    private long createdAt;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int votes=0;

//    @Override
//    public String toString() {
//        return "CommentData{" +
//                "id=" + id +
//                ", userId=" + (userData != null ? userData.getId() : null) +
//                ", postId=" + (postData != null ? postData.getId() : null) +
//                ", content='" + content + '\'' +
//                ", createdAt=" + createdAt +
//                ", votes=" + votes +
//                '}';
//    }

}
