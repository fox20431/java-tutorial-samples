package com.hihusky.reflectiondemo.util;

import com.hihusky.reflectiondemo.entity.Person;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ReflectionTest {
    @Test
    public void FieldTest() {
        Person person = new Person();
        person.setName("ark");
        person.setAge(18);
        Field[] fields = person.getClass().getDeclaredFields();
        for (Field field : fields) {
            String o = null;
            try {
                field.setAccessible(true);
                o = (String)field.get(person);
            } catch (IllegalAccessException e) {
                System.out.println();
                e.printStackTrace();
            }
            System.out.println(o);
        }
    }
}
