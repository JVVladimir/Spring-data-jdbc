package com.jvvladimir.spring.jdbc.audition

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreatedByAuditorAware : AuditorAware<String> {

    @Value("\${owner.name}")
    private lateinit var ownerName: String

    override fun getCurrentAuditor(): Optional<String> {
        return Optional.of(ownerName)
    }
}