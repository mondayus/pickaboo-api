package com.pickaboo.api.model;

import com.pickaboo.api.dto.UserDto;
import com.pickaboo.api.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Post {
    private Integer id;

    private String title;

    private String body;

    private UserDto user;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;


    public static Post fromEntity(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                UserDto.from(entity.getUser()),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

}
