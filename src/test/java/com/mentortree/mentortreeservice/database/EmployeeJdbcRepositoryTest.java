package com.mentortree.mentortreeservice.database;

import com.mentortree.mentortreeservice.model.MentorTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeJdbcRepositoryTest {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedTemplate;
    EmployeeJdbcRepository employeeJdbcRepository = new EmployeeJdbcRepository();
    List<MentorTree> mentorTrees = new ArrayList<MentorTree>();

    @Before
    public void setup(){
        jdbcTemplate = mock(JdbcTemplate.class);
        namedTemplate = mock(NamedParameterJdbcTemplate.class);
        ReflectionTestUtils.setField(employeeJdbcRepository,"jdbcTemplate",jdbcTemplate);
        ReflectionTestUtils.setField(employeeJdbcRepository,"namedTemplate",namedTemplate);

        MentorTree mentorTree = new MentorTree();
        mentorTree.setEmployee_id("1");
        mentorTree.setMentor_id("2");
        mentorTree.setTreeLead_id("3");


        mentorTrees.add(mentorTree);

    }

    @Test
    public void testGetByMentorId(){

        when(jdbcTemplate.query(anyString(),(BeanPropertyRowMapper)(anyObject()))).thenReturn(mentorTrees);

        List<MentorTree> returnedMentorTrees = employeeJdbcRepository.getByMentorId("2");

        Assert.assertEquals("1",returnedMentorTrees.get(0).getEmployee_id());
        Assert.assertEquals("2",returnedMentorTrees.get(0).getMentor_id());
        Assert.assertEquals("3",returnedMentorTrees.get(0).getTreeLead_id());

    }

    @Test
    public void testGetByTreeLeadId(){

        when(jdbcTemplate.query(anyString(),(BeanPropertyRowMapper)(anyObject()))).thenReturn(mentorTrees);

        List<MentorTree> returnedMentorTrees = employeeJdbcRepository.getByTreeLeadId("3");

        Assert.assertEquals("1",returnedMentorTrees.get(0).getEmployee_id());
        Assert.assertEquals("2",returnedMentorTrees.get(0).getMentor_id());
        Assert.assertEquals("3",returnedMentorTrees.get(0).getTreeLead_id());

    }

    @Test
    public void testUpdateMentor(){

        when(namedTemplate.update(anyString(),(MapSqlParameterSource)(anyObject()))).thenReturn(1);

        boolean result = employeeJdbcRepository.updateMentor("1", new ArrayList<String>(Arrays.asList("1")));

        Assert.assertTrue(result);

    }

    @Test
    public void testDeleteEmployee(){
        when(jdbcTemplate.update(anyString())).thenReturn(1);
        boolean result = employeeJdbcRepository.deleteEmployee("employee","1");
        Assert.assertTrue(result);
    }

}
