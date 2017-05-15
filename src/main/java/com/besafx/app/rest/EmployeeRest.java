package com.besafx.app.rest;

import com.besafx.app.config.CustomException;
import com.besafx.app.entity.Employee;
import com.besafx.app.entity.Person;
import com.besafx.app.entity.Views;
import com.besafx.app.service.EmployeeService;
import com.besafx.app.service.PersonService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/employee/")
public class EmployeeRest {

    @Autowired
    private PersonService personService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_EMPLOYEE_CREATE')")
    public Employee create(@RequestBody Employee employee, Principal principal) {
        if (employeeService.findByBranchAndPerson(employee.getBranch(), employee.getPerson()) != null) {
            throw new CustomException("هذا الموظف موجود بالفعل");
        }
        Employee topEmployee = employeeService.findTopByOrderByCodeDesc();
        if (topEmployee == null) {
            employee.setCode(1);
        } else {
            employee.setCode(topEmployee.getCode() + 1);
        }
        employee = employeeService.save(employee);
        notificationService.notifyOne(Notification
                .builder()
                .title("العمليات على الموظفين")
                .message("تم اضافة موظف جديد بنجاح")
                .type("success")
                .icon("fa-plus-circle")
                .build(), principal.getName());
        return employee;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_EMPLOYEE_UPDATE')")
    @JsonView(Views.Summery.class)
    public Employee update(@RequestBody Employee employee, Principal principal) {
        Employee object = employeeService.findOne(employee.getId());
        if (object != null) {
            Optional.ofNullable(employeeService.findByBranchAndPerson(employee.getBranch(), employee.getPerson())).ifPresent(value -> {
                if (object.getId() != value.getId()) {
                    throw new CustomException("هذا الموظف موجود بالفعل");
                }
            });
            employee = employeeService.save(employee);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على الموظفين")
                    .message("تم تعديل بيانات الموظف بنجاح")
                    .type("success")
                    .icon("fa-edit")
                    .build(), principal.getName());
            return employee;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_EMPLOYEE_DELETE')")
    public void delete(@PathVariable Long id) {
        Employee object = employeeService.findOne(id);
        if (object != null) {
            throw new CustomException("لا يمكنك حذف هذا الموظف");
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Employee> findAll() {
        return Lists.newArrayList(employeeService.findAll());
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Employee findOne(@PathVariable Long id) {
        return employeeService.findOne(id);
    }

    @RequestMapping(value = "count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long count() {
        return employeeService.count();
    }

    @RequestMapping(value = "fetchTableData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Employee> fetchTableData(Principal principal) {
        Person person = personService.findByEmail(principal.getName());
        if(person.getTechnicalSupport()){
            return findAll();
        }
        List<Employee> list = new ArrayList<>();
        person.getCompanies().stream().forEach(company -> company.getBranches().stream().forEach(branch -> list.addAll(branch.getEmployees())));
        person.getBranches().stream().forEach(branch -> list.addAll(branch.getEmployees()));
        list.addAll(person.getEmployees());
        return list.stream().distinct().collect(Collectors.toList());
    }

    @RequestMapping(value = "fetchTableDataSummery", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(Views.Summery.class)
    public List<Employee> fetchTableDataSummery(Principal principal) {
        return fetchTableData(principal);
    }

}
