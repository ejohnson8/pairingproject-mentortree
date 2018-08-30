package com.mentortree.mentortreeservice.controller;

import com.mentortree.mentortreeservice.database.EmployeeJdbcRepository;
import com.mentortree.mentortreeservice.model.MentorTree;
import com.mentortree.mentortreeservice.model.response.EmployeeResponse;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MentorTreeController {

    @Autowired
    private EmployeeJdbcRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final EurekaClient discoveryClient;

    public MentorTreeController(EurekaClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping(value = "/mentor/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployeeResponse> getEmployeesByMentorId(@PathVariable("id") String id) {
        List<MentorTree> mentees = repository.getByMentorId(id);

        List<String> employeeIds = getEmployeeIdsFromMentorTrees(mentees);
        String idList = StringUtils.join(employeeIds, ',');
        List<HashMap<String, String>> employees = restTemplate.getForObject(fetchEmployeeServiceUrl()+"/employee"+"/employee/getAll/"+ idList, List.class);

        return mapToResponseObject(employees);

    }

    @RequestMapping(value = "/treelead/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployeeResponse> getEmployeesByTreeLeadId(String id) {
        List<MentorTree> ids = repository.getByTreeLeadId(id);

        List<String> employeeIds = new ArrayList<String>();
        employeeIds.addAll(getEmployeeIdsFromMentorTrees(ids));
        employeeIds.addAll(getMentorIdsFromMentorTrees(ids));
        String idList = StringUtils.join(employeeIds, ',');
        List<HashMap<String, String>> employees = restTemplate.getForObject(fetchEmployeeServiceUrl() +"/employee"+ "/employee/getAll/"+ idList, List.class);

        return mapToResponseObject(employees);

    }

    @PutMapping(value = "/{employeeid}/{mentees}")
    @ResponseBody
    public HttpStatus updateMentor(@PathVariable("employeeid") String employeeid, @PathVariable("mentees") List<String> mentees) {
        if(repository.updateMentor(employeeid, mentees)) return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;

    }

    @DeleteMapping(value = "/{employeeType}/{id}")
    @ResponseBody
    public HttpStatus deleteEmployee(@PathVariable("employeeType") String employeeType, @PathVariable("id") String id) {
        if(repository.deleteEmployee(employeeType, id)) return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }

    private List<EmployeeResponse> mapToResponseObject(List<HashMap<String,String>> employees) {
        List<EmployeeResponse> employeeResponseList = new ArrayList<EmployeeResponse>();
        for(HashMap<String, String> employee : employees) {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.get("employeeID"));
            employeeResponse.setFirstName(employee.get("firstName"));
            employeeResponse.setLastName(employee.get("lastName"));
            employeeResponse.setImageURL(employee.get("imageURL"));
            employeeResponseList.add(employeeResponse);
        }
        return employeeResponseList;

    }

    private List<String> getEmployeeIdsFromMentorTrees(List<MentorTree> mentorTrees) {
        List<String> employees = new ArrayList<String>();
        for(MentorTree tree : mentorTrees) {
            employees.add(tree.getEmployee_id());
        }
        return employees;
    }

    private List<String> getMentorIdsFromMentorTrees(List<MentorTree> mentorTrees) {
        List<String> mentors = new ArrayList<String>();
        for(MentorTree tree : mentorTrees) {
            mentors.add(tree.getMentor_id());
        }
        return mentors;
    }

    private String fetchEmployeeServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("GATEWAY-APPLICATION", false);
        String employeeServiceUrl = instance.getHomePageUrl();
        return employeeServiceUrl;
    }

}
