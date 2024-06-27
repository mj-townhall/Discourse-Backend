package com.townhall.discourse.entities;

public enum VoteType {
    UPVOTE("upvote",1),
    DOWNVOTE("downvote",-1),
    NOVOTE("novote",0);

    private final String type;
    private final int value;

    VoteType(String type, int value) {
        this.type = type;
        this.value=value;
    }

    public String getType() {
        return type;
    }

    public int getValue(){
        return this.value;
    }

    // Optional: Helper method to convert string to enum
    public static VoteType fromString(String text) {
        for (VoteType b : VoteType.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

    public static VoteType fromValue(int value) {
        if(value==1) return UPVOTE;
        else if(value==-1) return DOWNVOTE;
        else if(value==0) return NOVOTE;
        throw new IllegalArgumentException("No constant with number " + value + " found");
    }
}

