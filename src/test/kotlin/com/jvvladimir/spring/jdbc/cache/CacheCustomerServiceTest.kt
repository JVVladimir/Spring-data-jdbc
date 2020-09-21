package com.jvvladimir.spring.jdbc.cache

import com.jvvladimir.spring.jdbc.SpringJdbcConfiguration
import com.jvvladimir.spring.jdbc.model.Customer
import com.jvvladimir.spring.jdbc.repository.CustomerRepository
import com.jvvladimir.spring.jdbc.services.CustomerService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.cache.CacheType
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.cache.CacheManager
import org.springframework.test.context.ContextConfiguration

/**
 * Как SpringBootTest поднимает БД (какую?)?
 *
 * DataJdbcTest - по-умолчанию поднимает бины, связанные только с JDBC и DB, кэширования нет.
 * Нужно руками указывать, какой CacheManager поднять или использовать вместо DataJdbcTest
 * SpringBootTest, который поднимет весь контекст, в том числе и CacheManager.
 * */

@DataJdbcTest
@AutoConfigureCache(cacheProvider = CacheType.SIMPLE)
@ContextConfiguration(classes = [SpringJdbcConfiguration::class])
class CacheCustomerServiceTest {

    companion object {
        private const val CACHE_NAME = "customers"
    }

    @Autowired
    private lateinit var customerService: CustomerService
    @Autowired
    private lateinit var repository: CustomerRepository
    @Autowired
    private lateinit var cacheManager: CacheManager

    private val customer: Customer = Customer().also {
        it.name = "Vova"
    }

    @Test
    fun `test finding operation in cache`() {
        val customer = repository.save(customer)

        repeat(3) {
            customerService.findById(customer.id!!)
        }

        assertThat(cacheManager.getCache(CACHE_NAME)!![customer.id!!]).isNotNull
    }

    @Test
    fun `test saving operation in cache`() {
        val customer = customerService.save(customer)
        assertThat(cacheManager.getCache(CACHE_NAME)!![customer.id!!]).isNotNull

        // customerService.findById(customer.id!!)

        // assertThat(cacheManager.getCache(CACHE_NAME)!![customer.id!!]).isNotNull
    }

    @Test
    fun `test deleting operation in cache`() {
        val customer = customerService.save(customer)
        assertThat(cacheManager.getCache(CACHE_NAME)!![customer.id!!]).isNotNull

        repeat(3) {
            customerService.findById(customer.id!!)
        }

        assertThat(cacheManager.getCache(CACHE_NAME)!![customer.id!!]).isNotNull
    }
}