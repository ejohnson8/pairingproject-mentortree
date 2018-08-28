package com.mentortree.mentortreeservice.controller;

import com.mentortree.mentortreeservice.database.EmployeeJdbcRepository;
import com.mentortree.mentortreeservice.model.Employee;
import com.mentortree.mentortreeservice.model.Mentor;
import com.mentortree.mentortreeservice.model.TreeLead;
import com.mentortree.mentortreeservice.model.employeeservice.EmployeeServiceObject;
import com.mentortree.mentortreeservice.model.response.EmployeeResponse;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MentorTreeController {

    @Autowired
    private EmployeeJdbcRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/mentor/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployeeResponse> getEmployeesByMentorId(@PathVariable("id") String id) {
        Mentor employee =  (Mentor)repository.getById(id);
        List<String> mentees = employee.getMentees();

        String idList = StringUtils.join(mentees, ',');
        List<EmployeeServiceObject> employees = restTemplate.getForObject("localhost:8080/employee/"+ idList, List.class);

        return mapToResponseObject(employees);

    }

    public List<EmployeeResponse> getEmployeesByTreeLeadId(String id) {
        TreeLead treeLead = (TreeLead)repository.getById(id);
        List<String> mentorIds = treeLead.getMentors();

        List<String> ids = new ArrayList<>();
        for(String mentorId : mentorIds){
            ids.add(mentorId);
            Mentor mentor = (Mentor) repository.getById(mentorId);
            List<String> mentees = mentor.getMentees();
            ids.addAll(mentees);
        }
        String idList = StringUtils.join(ids, ',');
        List<EmployeeServiceObject> employees = restTemplate.getForObject("localhost:8080/employee/"+ idList, List.class);

        return mapToResponseObject(employees);

    }

    private List<EmployeeResponse> mapToResponseObject(List<EmployeeServiceObject> employees) {
        List<EmployeeResponse> employeeResponseList = new ArrayList<EmployeeResponse>();
        for(EmployeeServiceObject employee : employees) {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.getEmployeeID());
            employeeResponse.setFirstName(employee.getFirstName());
            employeeResponse.setLastName(employee.getLastName());
            employeeResponse.setImageURL(employee.getImageURL());
            employeeResponseList.add(employeeResponse);
        }
        return employeeResponseList;

    }


}
