package ru.geekbrains.lesson3.HomeWork3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class StudentExternalize implements Externalizable {

    private String name;
    private int age;
    private transient double GPA;

    public StudentExternalize(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public StudentExternalize() {
    }


    /*Запись данных студента*/
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(age);
        out.writeObject(GPA);
    }

    /*Считывание данных о студенте*/
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = (int) in.readObject();
        GPA = (Double) in.readObject();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "StudentExternalize{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", GPA=" + GPA +
                '}';
    }
}
