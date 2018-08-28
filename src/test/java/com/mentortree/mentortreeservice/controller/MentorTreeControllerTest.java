package com.mentortree.mentortreeservice.controller;

import com.mentortree.mentortreeservice.database.EmployeeJdbcRepository;
import com.mentortree.mentortreeservice.model.Employee;
import com.mentortree.mentortreeservice.model.Mentor;
import com.mentortree.mentortreeservice.model.TreeLead;
import com.mentortree.mentortreeservice.model.employeeservice.EmployeeServiceObject;
import com.mentortree.mentortreeservice.model.response.EmployeeResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MentorTreeControllerTest {

    MentorTreeController mentorTreeController;
    private EmployeeJdbcRepository mockRepository;
    private RestTemplate mockRestTemplate;

    private List<EmployeeServiceObject> employees = new ArrayList<EmployeeServiceObject>();

    @Before
    public void setUp() {
        mentorTreeController = new MentorTreeController();

        mockRepository = mock(EmployeeJdbcRepository.class);
        mockRestTemplate = mock(RestTemplate.class);

        ReflectionTestUtils.setField(mentorTreeController, "repository", mockRepository);
        ReflectionTestUtils.setField(mentorTreeController, "restTemplate", mockRestTemplate);

        EmployeeServiceObject employee = new EmployeeServiceObject();
        employee.setFirstName("liz");
        employee.setLastName("johnson");
        employee.setEmployeeID("123");
        employee.setImageURL("www.image.com");

        employees.add(employee);
    }

    @Test
    public void testGetEmployeesByMentorId() {
        Mentor mentor = new Mentor();
        mentor.setId("321");
        mentor.setMentees(new ArrayList<String>(Arrays.asList("123")));


        when(mockRepository.getById("321")).thenReturn(mentor);
        when(mockRestTemplate.getForObject(anyString(), anyObject())).thenReturn(employees);


        List<EmployeeResponse> employees = mentorTreeController.getEmployeesByMentorId("321");

        Assert.assertNotNull(employees);
        Assert.assertEquals("123", employees.get(0).getId());
/*
        Assert.assertEquals("/employees/123", employees.get(0).getRelativeURL());
*/
        Assert.assertEquals("liz", employees.get(0).getFirstName());
        Assert.assertEquals("johnson", employees.get(0).getLastName());
        Assert.assertEquals("www.image.com", employees.get(0).getImageURL());

    }

    @Test
    public void testGetEmployeesByTreeLeadId(){
        EmployeeServiceObject mentorEmployee = new EmployeeServiceObject();
        mentorEmployee.setFirstName("Thanh");
        mentorEmployee.setLastName("Tran");
        mentorEmployee.setEmployeeID("2");
        mentorEmployee.setImageURL("www.Thanh-image.com");

        employees.add(mentorEmployee);

        TreeLead treeLead = new TreeLead();
        treeLead.setId("1");
        treeLead.setMentors(new ArrayList<String>(Arrays.asList("2")));

        Mentor mentor = new Mentor();
        mentor.setId("2");
        mentor.setMentees(new ArrayList<String>(Arrays.asList("3")));

        when(mockRepository.getById("1")).thenReturn(treeLead);
        when(mockRepository.getById("2")).thenReturn(mentor);
        when(mockRestTemplate.getForObject(anyString(), anyObject())).thenReturn(employees);

        List<EmployeeResponse> employees = mentorTreeController.getEmployeesByTreeLeadId("1");

        Assert.assertNotNull(employees);
        Assert.assertEquals("123", employees.get(0).getId());
/*
        Assert.assertEquals("/employees/123", employees.get(0).getRelativeURL());
*/
        Assert.assertEquals("liz", employees.get(0).getFirstName());
        Assert.assertEquals("johnson", employees.get(0).getLastName());
        Assert.assertEquals("www.image.com", employees.get(0).getImageURL());

        Assert.assertEquals("2", employees.get(1).getId());
/*
        Assert.assertEquals("/employees/123", employees.get(0).getRelativeURL());
*/
        Assert.assertEquals("Thanh", employees.get(1).getFirstName());
        Assert.assertEquals("Tran", employees.get(1).getLastName());
        Assert.assertEquals("www.Thanh-image.com", employees.get(1).getImageURL());


    }
}
