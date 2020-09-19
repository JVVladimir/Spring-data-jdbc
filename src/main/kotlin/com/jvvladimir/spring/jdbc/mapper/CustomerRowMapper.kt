package com.jvvladimir.spring.jdbc.mapper

import com.jvvladimir.spring.jdbc.model.Customer
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class CustomerRowMapper : RowMapper<Customer> {

    override fun mapRow(rs: ResultSet, rowNum: Int) =
        Customer().apply {
            id = rs.getLong("ID")
            name = rs.getString("NAME")
        }
}