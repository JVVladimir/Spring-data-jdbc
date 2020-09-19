package com.jvvladimir.spring.jdbc

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing

@Configuration
@EnableAutoConfiguration
@EnableJdbcAuditing
@EnableCaching
@ComponentScan(basePackages = ["com.jvvladimir.spring.jdbc"])
class SpringJdbcConfiguration