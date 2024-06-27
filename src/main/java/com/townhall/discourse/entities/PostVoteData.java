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
public class PostVoteData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private int userId;
    private int postId;

    @Enumerated(EnumType.STRING)
    private VoteType voteType; // Enum: UPVOTE, DOWNVOTE, NOVOTE
}
