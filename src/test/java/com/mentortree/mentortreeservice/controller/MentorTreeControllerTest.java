package com.mentortree.mentortreeservice.controller;

import com.mentortree.mentortreeservice.database.EmployeeJdbcRepository;
import com.mentortree.mentortreeservice.model.MentorTree;
import com.mentortree.mentortreeservice.model.response.EmployeeResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MentorTreeControllerTest {

    MentorTreeController mentorTreeController;
    private EmployeeJdbcRepository mockRepository;
    private RestTemplate mockRestTemplate;

    private List<Map<String, String>> employees = new ArrayList<Map<String, String>>();

    @Before
    public void setUp() {
        mentorTreeController = new MentorTreeController();

        mockRepository = mock(EmployeeJdbcRepository.class);
        mockRestTemplate = mock(RestTemplate.class);

        ReflectionTestUtils.setField(mentorTreeController, "repository", mockRepository);
        ReflectionTestUtils.setField(mentorTreeController, "restTemplate", mockRestTemplate);

        Map<String, String> employee = new HashMap<String, String>();
        employee.put("firstName", "liz");
        employee.put("lastName", "johnson");
        employee.put("employeeID", "123");
        employee.put("imageURL", "www.image.com");

        employees.add(employee);
    }

    @Test
    public void testGetEmployeesByMentorId() {
        List<MentorTree> mentorTree = new ArrayList<MentorTree>();
        MentorTree entry = new MentorTree();
        entry.setEmployee_id("123");
        entry.setMentor_id("321");
        mentorTree.add(entry);

        when(mockRepository.getByMentorId("321")).thenReturn(mentorTree);
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
        Map<String, String> mentorEmployee = new HashMap<String, String>();
        mentorEmployee.put("firstName", "Thanh");
        mentorEmployee.put("lastName", "Tran");
        mentorEmployee.put("employeeID", "2");
        mentorEmployee.put("imageURL", "www.Thanh-image.com");

        employees.add(mentorEmployee);

        List<MentorTree> mentorTree = new ArrayList<MentorTree>();
        MentorTree entry = new MentorTree();
        entry.setEmployee_id("123");
        entry.setMentor_id("2");
        entry.setTreeLead_id("1");
        mentorTree.add(entry);


        when(mockRepository.getByTreeLeadId("1")).thenReturn(mentorTree);

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

    @Test
    public void testUpdateMentor(){

        List<String> mentees = new ArrayList<String>(Arrays.asList("123"));
        when(mockRepository.updateMentor("4", mentees)).thenReturn(true);

        Assert.assertEquals(HttpStatus.OK,mentorTreeController.updateMentor("4",mentees));
        verify(mockRepository).updateMentor("4", mentees);

    }

    @Test
    public void testDeleteMentee() {
        when(mockRepository.deleteEmployee("employee", "4")).thenReturn(true);

        Assert.assertEquals(HttpStatus.OK,mentorTreeController.deleteEmployee("employee", "4"));
        verify(mockRepository).deleteEmployee("employee", "4");
    }

    @Test
    public void testDeleteMentor() {
        when(mockRepository.deleteEmployee("mentor", "4")).thenReturn(true);

        Assert.assertEquals(HttpStatus.OK,mentorTreeController.deleteEmployee("mentor", "4"));
        verify(mockRepository).deleteEmployee("mentor", "4");
    }

    @Test
    public void testDeleteTreeLead() {
        when(mockRepository.deleteEmployee("treeLead", "4")).thenReturn(true);

        Assert.assertEquals(HttpStatus.OK,mentorTreeController.deleteEmployee("treeLead", "4"));
        verify(mockRepository).deleteEmployee("treeLead", "4");
    }
}
