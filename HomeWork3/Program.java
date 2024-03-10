package ru.geekbrains.lesson3.HomeWork3;

/* 1.   Разработайте класс Student с полями  String name, int age, transient double GPA (средний бал).
        Обеспечьте поддержку сериализации для данного класса.
        Создайте объект класса Student и инициализируйте его данными.
        Сериализуйте объект в программу из файла.
        Выведите все поля объекта, включая GPA, и обсудите, почему значение GPA не было сохранено/восстановленно.

    2.  Выполнить задачу 1 используя другие типы сериализации (в xml и json документы). */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ru.geekbrains.lesson3.task2.ToDoListApp.FILE_JSON;
import static ru.geekbrains.lesson3.task2.ToDoListApp.loadTasksFromFile;

public class Program {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //----------------------Сериализация----------------------
        Student student1 = new Student("Ivanov", 19, 4.35);
        try (FileOutputStream fileOutputStream = new FileOutputStream("FileOfStudents.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(student1);
                objectOutputStream.close();
            }
        try (FileInputStream fileInputStream = new FileInputStream("FileOfStudents.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            student1 = (Student) objectInputStream.readObject();
            System.out.println(student1);
            System.out.println("GPA 0.0, так как для данного поля установлено transient");
        }

        //--------------------Экстернализация------------------------
        List<StudentExternalize> students = new ArrayList<>();
        students.add(new StudentExternalize("Иванов", 18));
        students.add(new StudentExternalize("Петров", 21));
        for (StudentExternalize student: students) {
            System.out.println(student.toString());
        }
        System.out.println("_______________________________________________");
        Scanner scanner = new Scanner(System.in);
        StudentsInfoApp.addNewStudent(scanner,students);
        for (StudentExternalize student: students) {
            System.out.println(student.toString());
        }
        StudentsInfoApp.saveStudentToFile("FILE_JSON.xml", students);
        StudentsInfoApp.saveStudentToFile("FILE_JSON.json", students);

        List<StudentExternalize> studentsToConsol = new ArrayList<>();

        studentsToConsol = StudentsInfoApp.loadStudentsFromFile("FILE_JSON.json");
        for (StudentExternalize student: studentsToConsol) {
            System.out.println(student.toString());
        }
        System.out.println("___________________________________________");
        studentsToConsol = StudentsInfoApp.loadStudentsFromFile("FILE_JSON.xml");
        for (StudentExternalize student: studentsToConsol) {
            System.out.println(student.toString());
        }
    }
}
