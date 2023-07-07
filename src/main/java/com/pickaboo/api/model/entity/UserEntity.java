package com.pickaboo.api.model.entity;

import com.pickaboo.api.model.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@SQLDelete(sql = "UPDATE \"user\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class UserEntity {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; //kakao - email

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.USER;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = LocalDateTime.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = LocalDateTime.from(Instant.now());
    }

    public static UserEntity of(String id, String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }
}
