package com.bookstudio.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * Runs the test as an authenticated staff member, with the same kind of
 * authentication a real bearer token produces (a JwtAuthenticationToken whose
 * authorities are the permission codes).
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@WithSecurityContext(factory = WithStaffUserSecurityContextFactory.class)
public @interface WithStaffUser {

    long id() default 1;

    String username() default "admin";

    /**
     * Defaults to every permission, i.e. an administrator.
     */
    String[] permissions() default {
            "ADMIN_FULL", "USER_CREATE", "USER_EDIT", "USER_DELETE", "BOOK_CREATE", "BOOK_EDIT", "BOOK_DELETE",
            "LOAN_CREATE", "LOAN_EDIT", "LOAN_RETURN", "FINE_CREATE", "FINE_EDIT", "FINE_PAYMENT", "REPORT_VIEW",
            "CATALOG_MANAGE", "RESERVATION_MANAGE", "READER_MANAGE" };
}
