package com.bookstudio.shared.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Enables asynchronous event listeners (@ApplicationModuleListener). With
 * virtual threads enabled, Boot's task executor runs them on virtual threads.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
}
