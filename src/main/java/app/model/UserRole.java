package app.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {

    USER,BOT,ADMIN

//    USER(new HashSet<>(Arrays.asList(
//
//    ))),
//    ADMIN(new HashSet<>(Arrays.asList(
//
//    )));
//
//    private final Set<UserPermission> permissions;
//
//    UserRole(Set<UserPermission> permissions) {
//        this.permissions = permissions;
//    }
//
//    public Set<UserPermission> getPermissions() {
//        return permissions;
//    }
//
//    public Set<GrantedAuthority> getGrantedAuthorities() {
//        Set<GrantedAuthority> grantedAuthorities = getPermissions().stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toSet());
//
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//
//        return grantedAuthorities;
//    }
}
