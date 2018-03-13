package com.logicbig.example;

import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.text.ParseException;
import java.util.Locale;
import java.util.StringJoiner;

/**
 * This example formats Employee object in following format:
 * Employee#name, Employee#dept, Employee#phoneNumber
 */
public class CustomFormatterExample {

    public static void main (String[] args) {
        DefaultFormattingConversionService service =
                            new DefaultFormattingConversionService();
        service.addFormatter(new EmployeeFormatter());

        Employee employee = new Employee("Joe", "IT", "123-456-7890");
        String string = service.convert(employee, String.class);
        System.out.println(string);

        Employee e = service.convert(string, Employee.class);
        System.out.println(e);
    }

    private static class EmployeeFormatter implements Formatter<Employee> {

        @Override
        public Employee parse (String text,
                            Locale locale) throws ParseException {


            String[] split = text.split(",");
            if (split.length != 3) {
                throw new ParseException("The Employee string format " +
                                    "should be in this format: Mike, Account, 111-111-1111",
                                    split.length);
            }
            Employee employee = new Employee(split[0].trim(),
                                split[1].trim(), split[2].trim());
            return employee;
        }

        @Override
        public String print (Employee employee, Locale locale) {
            return new StringJoiner(", ")
                                .add(employee.getName())
                                .add(employee.getDept())
                                .add(employee.getPhoneNumber())
                                .toString();

        }
    }

    private static class Employee {
        private String name;
        private String dept;
        private String phoneNumber;

        public Employee (String name, String dept, String phoneNumber) {
            this.name = name;
            this.dept = dept;
            this.phoneNumber = phoneNumber;
        }

        public String getName () {
            return name;
        }

        public String getDept () {
            return dept;
        }


        public String getPhoneNumber () {
            return phoneNumber;
        }
    }
}