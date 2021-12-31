package com.zensar.assignment2and3;

import com.zensar.assignment2and3.customannotation.DefaultName;
import com.zensar.assignment2and3.customexception.AnnotationNotPersistedException;
import com.zensar.assignment2and3.models.Employee;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class Assignment2 {

    public static void main(String[] args) throws AnnotationNotPersistedException {
        Employee serializeEmp = new Employee(5, "Hardik", 22000, LocalDate.now());

        try {
            FileOutputStream fout = new FileOutputStream("serialized_object.txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(serializeEmp);
            out.flush();
            out.close();
            System.out.println("serialization success");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("serialized_object.txt"));
            Employee deserializeEmp = (Employee) in.readObject();
            System.out.println(deserializeEmp);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Employee emp = new Employee();
        try {
            Method method = emp.getClass().getMethod("getName");

            DefaultName defaultName = method.getAnnotation(DefaultName.class);
            System.out.println("default name value => " + defaultName.name());
        } catch (NoSuchMethodException | SecurityException | NullPointerException e) {
            e.printStackTrace();
            throw new AnnotationNotPersistedException("Annotation is not persisted.");
        }
    }
}
