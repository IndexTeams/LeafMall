package cn.kiroe.demo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/17 22:57
 **/
@Slf4j
class DemoApplicationTest {

    void main() {
        log.info("test");
    }

    void test(){
        String s = "s";
        Object o = (Object)s;
        System.out.println(o);
        List<Number> numberIntegerList = new ArrayList<>();
        numberIntegerList.add(1);
        numberIntegerList.add(2);
        numberIntegerList.add(1.1);
        List<? super Number> numberList = numberIntegerList;
        // java: incompatible types:
        // int cannot be converted to capture#1 of ? extends java.lang.Number
        numberList.add(10);  // 编译错误，无法添加元素
        numberList.add(1.1);
        // Number number = numberList.get(0);  // 可以读取元素

        Person<Integer> integerPerson = new Person<>();
        integerPerson.setValue(123);
        Person<? super Integer> person = integerPerson;
        person.setValue(123);
    }

    class Person<T>{
        T value;

        public T getValue() {
            return value;
        }

        public void setValue(final T value) {
            this.value = value;
        }
    }

      public void testVal(){
        val test = "fasdf";

    }
}


