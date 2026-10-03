package com.bookstudio;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.bookstudio.security.WithStaffUser;

/**
 * Full application context against a real Postgres (Testcontainers) with Flyway
 * migrations and seed data. Each test runs in a transaction that is rolled back,
 * authenticated as an administrator unless it declares otherwise: put
 * {@link WithStaffUser} or {@code @WithAnonymousUser} on a test method, or on the
 * class <em>before</em> {@code @IntegrationTest} (the first security context
 * annotation found wins).
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@Transactional
@WithStaffUser
public @interface IntegrationTest {
}
