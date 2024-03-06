/*Используя Reflection API, напишите программу, которая выводит на экран все методы класса String.*/

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Task1 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> stringClass = String.class;

        /*Получение всех конструкторов класса String*/
        Constructor<?>[]constructors = stringClass.getConstructors();
        for (Constructor<?>constructor : constructors) {
            System.out.println(constructor.toString());
        }

        System.out.println("---------------------------------------------------------------");
        /*Получение всех методов класса String*/
        Method[] methodss = stringClass.getMethods();
        for (Method method : methodss) {
            System.out.println(method.toString());
        }
    }
}
