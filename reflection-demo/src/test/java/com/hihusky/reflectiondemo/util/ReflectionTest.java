package com.hihusky.reflectiondemo.util;

import com.hihusky.reflectiondemo.entity.Base;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ReflectionTest {
    @Test
    public void FieldTest() {
        Base base = new Base();
        base.setValue(18);
        Field[] fields = base.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                // set field
                field.set(base, 11);
            } catch (IllegalAccessException e) {
                System.out.println();
                e.printStackTrace();
            }
        }
        System.out.println(base);
    }
}
