package com.pickaboo.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {
    private String name;
    private String password;
}
