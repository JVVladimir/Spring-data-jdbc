package com.jvvladimir.spring.jdbc.services

import com.jvvladimir.spring.jdbc.model.Customer
import com.jvvladimir.spring.jdbc.repository.CustomerRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class CustomerServiceImpl(
    val customerRepository: CustomerRepository
): CustomerService {

    @Cacheable("customers")
    override fun findById(id: Long): Customer? = customerRepository.findById(id).orElse(null)

    @CachePut("customers")
    override fun save(customer: Customer) = customerRepository.save(customer)

    @CacheEvict("customers")
    override fun delete(customer: Customer) =
        customerRepository.delete(customer)
}