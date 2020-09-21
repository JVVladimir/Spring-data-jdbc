package com.jvvladimir.spring.jdbc.services

import com.jvvladimir.spring.jdbc.model.Customer

interface CustomerService {

    fun findById(id: Long): Customer?
    fun save(customer: Customer): Customer
    fun delete(customer: Customer)
}