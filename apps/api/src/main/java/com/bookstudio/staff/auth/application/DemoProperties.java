package com.bookstudio.staff.auth.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * One-click login for the public demo. Disabled unless explicitly enabled.
 *
 * @param enabled whether {@code POST /auth/demo} is available
 * @param username staff account the demo login signs in as
 */
@ConfigurationProperties("app.demo")
public record DemoProperties(
    @DefaultValue("false") boolean enabled,
    @DefaultValue("demo") String username
) {
}
