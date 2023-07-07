package com.pickaboo.api.dto.response;

import com.pickaboo.api.dto.UserDto;
import com.pickaboo.api.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String userName;
    private RoleType role;

    public static UserResponse fromUser(UserDto user) {
        return new UserResponse(
                user.id(),
                user.username(),
                user.roleType()
        );
    }

}
