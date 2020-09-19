package com.jvvladimir.spring.jdbc.repository

import com.jvvladimir.spring.jdbc.SpringJdbcConfiguration
import com.jvvladimir.spring.jdbc.model.Address
import com.jvvladimir.spring.jdbc.model.Customer
import com.jvvladimir.spring.jdbc.model.Gender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Example
import org.springframework.test.context.ContextConfiguration

/**
 * @see DataJdbcTest поднимает in-memory БД, если таковая предоставлена (указывается в build.gradle.kts)
 * Каждый тест выполняется в рамках одной транзакции и откатывается после выполнения
 * DataJdbcTest нельзя использовать вместе с
 * @see SpringBootTest
 * При запуске теста DataJdbcTest сканирует classpath на наличие schema.sql для инициализации таблиц/схем тестовой БД
 *
 * Непонятно, почему нельзя использовать внешние ключи (как будто SQL нам больше не нужен!!)
 * Отношение многие-ко-многим делается целиком руками
 *
 *
 * */

@DataJdbcTest
@ContextConfiguration(classes = [SpringJdbcConfiguration::class])
class CustomerRepositoryTest {

    @Autowired
    private lateinit var repository: CustomerRepository

    @Value("\${owner.name}")
    private lateinit var ownerName: String

    @Test
    fun `test custom repository`() {
        val customer = Customer().also {
            it.name = "Vova"
            it.gender = Gender.MALE
        }
        val savedCustomer = repository.save(customer)

        assertThat(savedCustomer).isNotNull
        assertThat(savedCustomer.name).isEqualTo(customer.name)
    }

    @Test
    fun `test clone entity`() {
        val customer = Customer().also {
            it.name = "Vova"
            it.gender = Gender.MALE
        }
        val savedCustomer = repository.save(customer)

        assertThat(savedCustomer).isNotNull

        savedCustomer.id = null
        val secondCustomer = repository.save(savedCustomer)

        assertThat(secondCustomer).isNotNull
        assertThat(repository.count()).isEqualTo(2)
    }

    @Test
    fun `test aggregate entity`() {
        val address = Address().also {
            it.city = "Moscow"
            it.house = 27
        }
        val customer = Customer().also {
            it.name = "Vova"
            it.gender = Gender.MALE
            it.addresses[1] = address
        }
        val savedCustomer = repository.save(customer)

        assertThat(savedCustomer).isNotNull
        assertThat(savedCustomer.addresses[1]).isNotNull

        repository.delete(customer)

        assertThat(repository.findById(customer.id!!).isPresent).isFalse
    }

    @Test
    fun `test audition entity`() {
        val customer = Customer().also {
            it.name = "Vova"
            it.gender = Gender.MALE
        }
        val savedCustomer = repository.save(customer)

        assertThat(savedCustomer).isNotNull
        assertThat(savedCustomer.createdBy).isEqualTo(ownerName)
    }

    @Test
    fun `test count of customers query`() {
        val numberOfCustomers = 12
        repeat(numberOfCustomers) {
            repository.save(Customer().also {
                it.name = "Vova"
            })
        }

        assertThat(repository.countCustomers()).isEqualTo(numberOfCustomers)
    }

    @Test
    fun `test delete customer query`() {
        val customer = repository.save(Customer().also {
            it.name = "Vova"
        })

        assertThat(repository.findById(customer.id!!).isEmpty).isFalse

        repository.deleteCustomer(customer.id!!)
        assertThat(repository.findById(customer.id!!).isEmpty).isTrue
    }

    @Test
    fun `test cache`() {
        val customer = repository.save(Customer().also {
            it.name = "Vova"
        })
        repository.findById(customer.id!!)
        repository.findById(customer.id!!)
        repository.findById(customer.id!!)
    }
}