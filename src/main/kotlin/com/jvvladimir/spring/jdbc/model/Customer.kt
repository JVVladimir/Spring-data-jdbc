package com.jvvladimir.spring.jdbc.model

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.Id

data class Customer (

    @Id
    var id: Long? = null,
    var name: String? = null,
    var gender: Gender? = null,
    @CreatedBy
    var createdBy: String? = null,
    var addresses: MutableMap<Long, Address> = mutableMapOf()
)