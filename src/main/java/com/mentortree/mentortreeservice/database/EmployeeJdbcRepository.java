package com.mentortree.mentortreeservice.database;

import com.mentortree.mentortreeservice.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeJdbcRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Employee getById(String id) {
        return jdbcTemplate.queryForObject("select * from employee where employeeID=?", new Object[] {
                id
        }, new BeanPropertyRowMapper<Employee>(Employee.class));
    }
}
