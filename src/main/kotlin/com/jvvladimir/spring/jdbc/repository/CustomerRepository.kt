package com.jvvladimir.spring.jdbc.repository

import com.jvvladimir.spring.jdbc.model.Customer
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository: CrudRepository<Customer, Long> {

    @Query("select count(*) from customer")
    fun countCustomers(): Int

    @Query("delete from customer where id = :id")
    @Modifying
    fun deleteCustomer(@Param("id") id: Long)
}