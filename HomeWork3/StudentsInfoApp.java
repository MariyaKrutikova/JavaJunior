package ru.geekbrains.lesson3.HomeWork3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.geekbrains.lesson3.task2.ToDo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentsInfoApp {
    public static final String FILE_JSON = "tasks.json";
    public static final String FILE_XML = "tasks.xml";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static void addNewStudent(Scanner scanner, List<StudentExternalize> students) {
        System.out.println("Введите имя студента:");
        String studentName = scanner.nextLine();
        System.out.println("Введите возраст студента:");
        int studentAge = scanner.nextInt();

        students.add(new StudentExternalize(studentName, studentAge));
//        saveStudentToFile(FILE_JSON, students);
//        saveStudentToFile(FILE_XML, students);
//        System.out.println("Студент добавлен");
    }

    public static void saveStudentToFile(String fileName, List<StudentExternalize> students) {
        try {
            if (fileName.endsWith(".json")) {
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.writeValue(new File(fileName), students);
            } else if (fileName.endsWith(".xml")) {
                //String s = xmlMapper.writeValueAsString(tasks);
                xmlMapper.writeValue(new File(fileName), students);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<StudentExternalize> loadStudentsFromFile(String fileName) {
        List<StudentExternalize> students = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()) {
            try {
                if (fileName.endsWith(".json")) {
                    students = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, StudentExternalize.class));
                } else if (fileName.endsWith(".xml")) {
                    students = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, StudentExternalize.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return students;
    }
}
