package com.townhall.discourse.entities;

import jakarta.persistence.*;

public class CommentVoteData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int userId;
    private int commentId;

    @Enumerated(EnumType.STRING)
    private VoteType voteType; // Enum: UPVOTE, DOWNVOTE, NOVOTE
}
