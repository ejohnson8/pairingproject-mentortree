package com.mentortree.mentortreeservice.database;

import com.mentortree.mentortreeservice.model.MentorTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeJdbcRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedTemplate;

    public List<MentorTree> getByMentorId(String id) {
        return jdbcTemplate.query("select * from mentortree where mentor_id=" + id, BeanPropertyRowMapper.newInstance(MentorTree.class));
    }

    public List<MentorTree> getByTreeLeadId(String id) {
        return jdbcTemplate.query("select * from mentortree where treelead_id=" + id, BeanPropertyRowMapper.newInstance(MentorTree.class));
    }

    public boolean updateMentor(String s, List<String> mentees) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", mentees);
        int i = namedTemplate.update("UPDATE mentortree " + "SET mentor_id=" + s + " WHERE employee_id IN (:ids)" , parameters);
        if(i == mentees.size()) return true;
        return false;
    }

    public boolean deleteEmployee(String employeeType, String id) {
        int i = jdbcTemplate.update("UPDATE mentortree " + "SET " + employeeType + "_id=null WHERE " + employeeType + "_id=" + id);
        if(i >= 1) return true;
        return false;
    }
}
