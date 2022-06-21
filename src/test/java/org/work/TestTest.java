package org.work;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.work.moneyToChinese.*;


class TestTest {
    moneyToChinese a=new moneyToChinese();

    @AfterEach
    void successmessage(){
        System.out.println("測試成功!!");
    }


    @org.junit.jupiter.api.Test
    void test1(){
        assertEquals("玖仟陸佰兆元",a.moneyInChinese("9600000000000000"));
    }

    @Test
    void test2(){
        assertEquals("3,250,000",a.valueToString("3250000"));
    }
}