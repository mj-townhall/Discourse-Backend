package com.townhall.discourse.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class Status {

    public int id;
    public String message;
}
