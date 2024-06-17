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
public class PostData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId") // Specifies the foreign key column name
    private UserData userData;


    @Column(nullable = false)
    private long timeStampMillis;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
