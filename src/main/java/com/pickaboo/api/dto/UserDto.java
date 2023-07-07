package com.pickaboo.api.dto;

import com.pickaboo.api.model.RoleType;
import com.pickaboo.api.model.entity.UserEntity;

import java.time.LocalDateTime;

public record UserDto(
        String id,
        String username,
        String password,
        RoleType roleType,
        LocalDateTime registeredAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {

    public static UserDto of(String id, String username, String password) {
        return new UserDto(id, username, password, RoleType.USER, null, null, null);
    }

    public static UserDto of(String id, String username, String password, RoleType roleType, LocalDateTime registeredAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        return new UserDto(id, username, password, RoleType.USER, registeredAt, updatedAt, deletedAt);
    }

    public static UserDto from(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public UserEntity toEntity() {
        return UserEntity.of(
                id,
                username,
                password
        );
    }

}
