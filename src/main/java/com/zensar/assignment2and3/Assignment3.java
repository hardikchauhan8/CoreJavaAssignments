package com.zensar.assignment2and3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Assignment3 {

    private static List<Employee> empList;

    public static void main(String[] args) {
        initEmps();
        longTenureEmployees(empList).forEach(System.out::println);
        tenureWiseEmployeeCount(empList).forEach((noOfYears, count) -> System.out.println("no_of_years_completed => " + noOfYears + "\t no_of_employee_count => " + count));
    }

    private static void initEmps() {
        empList = new ArrayList<>();
        empList.add(new Employee(1, "A", 55000, LocalDate.of(2016, 5, 23)));
        empList.add(new Employee(2, "B", 50000, LocalDate.of(2015, 9, 7)));
        empList.add(new Employee(3, "C", 60000, LocalDate.of(2018, 8, 12)));
        empList.add(new Employee(4, "D", 75000, LocalDate.of(2018, 9, 20)));
        empList.add(new Employee(5, "E", 42000, LocalDate.of(2019, 6, 16)));
        empList.add(new Employee(6, "F", 98000, LocalDate.of(2020, 6, 12)));
    }

    static List<Employee> longTenureEmployees(List<Employee> employeeList) {
        System.out.println("\nList of employees who has joined 5 years before.");
        return employeeList.stream().filter(employee -> employee.getJoiningDate().getYear() <= LocalDate.now().minusYears(5).getYear()).sorted(Comparator.comparingInt(emp -> emp.getJoiningDate().getYear())).collect(Collectors.toList());
    }

    static Map<Integer, Long> tenureWiseEmployeeCount(List<Employee> employeeList) {
        System.out.println("\n\n\nList of employees => No of years completed -> No of Employees");
        return employeeList.stream().collect(Collectors.groupingBy((emp) -> LocalDate.now().getYear() - emp.getJoiningDate().getYear(), Collectors.counting()));
    }
}
