package com.pickaboo.api.controller;

import com.pickaboo.api.dto.UserDto;
import com.pickaboo.api.dto.request.UserJoinRequest;
import com.pickaboo.api.dto.request.UserLoginRequest;
import com.pickaboo.api.dto.response.AlarmResponse;
import com.pickaboo.api.dto.response.Response;
import com.pickaboo.api.dto.response.UserJoinResponse;
import com.pickaboo.api.dto.response.UserLoginResponse;
import com.pickaboo.api.common.exception.ErrorCode;
import com.pickaboo.api.common.exception.PickabooApiApplicationException;
import com.pickaboo.api.service.AlarmService;
import com.pickaboo.api.service.UserService;
import com.pickaboo.api.common.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final AlarmService alarmService;


    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        UserDto user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), UserDto.class).orElseThrow(
                () -> new PickabooApiApplicationException(ErrorCode.INTERNAL_SERVER_ERROR,
                        "Casting to User class failed"));
        return Response.success(userService.alarmList(user.id(), pageable).map(AlarmResponse::fromAlarm));
    }

    @GetMapping("/alarm/subscribe")
    public SseEmitter subscribe(Authentication authentication) {
        UserDto user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), UserDto.class).orElseThrow(
                () -> new PickabooApiApplicationException(ErrorCode.INTERNAL_SERVER_ERROR,
                        "Casting to User class failed"));
        return alarmService.connectAlarm(user.id());

    }
}
