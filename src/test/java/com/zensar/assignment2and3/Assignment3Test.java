package com.zensar.assignment2and3;

import com.zensar.assignment2and3.models.Employee;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Assignment3Test {

    private static List<Employee> employeeList;

    @BeforeClass
    public static void beforeClass() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "A", 55000, LocalDate.of(2016, 5, 23)));
        employeeList.add(new Employee(2, "B", 50000, LocalDate.of(2015, 9, 7)));
        employeeList.add(new Employee(3, "C", 60000, LocalDate.of(2018, 8, 12)));
        employeeList.add(new Employee(4, "D", 75000, LocalDate.of(2018, 9, 20)));
        employeeList.add(new Employee(5, "E", 42000, LocalDate.of(2019, 6, 16)));
        employeeList.add(new Employee(6, "F", 98000, LocalDate.of(2020, 6, 12)));
    }

    @Test
    public void testLongTenureEmployees() {
        List<Employee> result = Assignment3.longTenureEmployees(employeeList);
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void testTenureWiseEmployeeCount() {
        Map<Integer, Long> result = Assignment3.tenureWiseEmployeeCount(employeeList);
        Assert.assertTrue(result.size() > 0);
        Assert.assertEquals(1, (long) result.get(1));
        Assert.assertEquals(2, (long) result.get(3));
    }
}
