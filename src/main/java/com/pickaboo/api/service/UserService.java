package com.pickaboo.api.service;

import com.pickaboo.api.common.exception.ErrorCode;
import com.pickaboo.api.common.exception.PickabooApiApplicationException;
import com.pickaboo.api.dto.UserDto;
import com.pickaboo.api.model.Alarm;
import com.pickaboo.api.model.entity.UserEntity;
import com.pickaboo.api.repository.AlarmEntityRepository;
import com.pickaboo.api.repository.UserCacheRepository;
import com.pickaboo.api.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final UserCacheRepository userCacheRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;


    public UserDto loadUserByUserName(String userName) {
        return userCacheRepository.getUser(userName).orElseGet(() ->
                userEntityRepository.findByUserName(userName).map(UserDto::from).orElseThrow(() ->
                        new PickabooApiApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)))
        );
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> searchUser(String username) {
        return userEntityRepository.findByUserName(username)
                .map(UserDto::from);
    }


    @Transactional
    public UserDto join(String id, String userName, String password) {
        // 회원가입하려는 userName으로 회원가입된 user가 있는지
        userEntityRepository.findById(id).ifPresent(it -> {
            throw new PickabooApiApplicationException(ErrorCode.DUPLICATED_USER_ID, String.format("%s is duplicated", id));
        });

        // 회원가입 진행 = user를 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(id, userName, password));
        return UserDto.from(userEntity);
    }

    public Page<Alarm> alarmList(String userId, Pageable pageable) {
        return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);

    }
}
