package com.jvvladimir.spring.jdbc

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing

@Configuration
@EnableAutoConfiguration
@EnableJdbcAuditing
@ComponentScan(basePackages = ["com.jvvladimir.spring.jdbc"])
class SpringJdbcConfiguration