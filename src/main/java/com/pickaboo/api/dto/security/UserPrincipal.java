package com.pickaboo.api.dto.security;

import com.pickaboo.api.dto.UserDto;
import com.pickaboo.api.model.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record UserPrincipal(
        String id,
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static UserPrincipal of(String id, String username, String password) {
        return UserPrincipal.of(id, username, password);
    }

    public static UserPrincipal of(String id, String username, String password, Map<String, Object> oAuth2Attributes) {
        // 지금은 인증만 하고 권한을 다루고 있지 않아서 임의로 세팅한다.
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new UserPrincipal(
                id,
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                oAuth2Attributes
        );
    }

    public static UserPrincipal from(UserDto user) {
        return UserPrincipal.of(
                user.id(),
                user.username(),
                user.password()
        );
    }

    public UserDto toDto() {
        return UserDto.of(
                id,
                username,
                password
        );
    }


    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    @Override public Map<String, Object> getAttributes() { return oAuth2Attributes; }
    @Override public String getName() { return username; }


}
