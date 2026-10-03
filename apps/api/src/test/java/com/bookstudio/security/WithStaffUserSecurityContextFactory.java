package com.bookstudio.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

class WithStaffUserSecurityContextFactory implements WithSecurityContextFactory<WithStaffUser> {

    @Override
    public SecurityContext createSecurityContext(WithStaffUser user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(StaffAuth.token(user.id(), user.username(), user.permissions()));
        return context;
    }
}
