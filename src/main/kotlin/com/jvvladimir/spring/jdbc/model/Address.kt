package com.jvvladimir.spring.jdbc.model

import org.springframework.data.annotation.Id

data class Address (

    @Id
    var customer: Long? = null,
    var city: String? = null,
    var street: String? = null,
    var house: Int? = null
)