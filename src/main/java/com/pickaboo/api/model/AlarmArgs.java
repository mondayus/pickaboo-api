package com.pickaboo.api.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {
    // 알람을 발생시킨 사람
    private String fromUserId;
    private Integer targetId;
}
