package com.betulsahin.springbootunittest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.api.function.Executable;

public class SampleUnitTestClass {

    Calculator calculatorTest;

    @BeforeEach
    void setup(){
        System.out.println(" inside before each ");
        this.calculatorTest = new Calculator();
    }

    @AfterEach
    void afterEach(){
        System.out.println(" inside after each ");
    }

    @BeforeAll
    static void beforeAll(){
        System.out.println(" inside before all ");
    }

    @AfterAll
    static void afterAll(){
        System.out.println(" inside after all ");
    }

    @Test
    @DisplayName(value = "This test should return equals when add two number")
    public void should_returnEquals_when_addTwoNumber(){
        // given
       int firstNumber = 10;
       int secondNumber = 20;
       int expected = 30;

       // when
       int actual = calculatorTest.add(firstNumber, secondNumber);

       // then
       Assertions.assertEquals(expected, actual);
    }

    @Test
    @RepeatedTest(10)
    public void should_returnNotEquals_when_addTwoNumber(){
        // given
        int firstNumber = 10;
        int secondNumber = 20;
        int expected = 40;

        // when
        int actual = calculatorTest.add(firstNumber, secondNumber);

        // then
        Assertions.assertNotEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 1, 0, 20})
    public void should_returnZero_when_multiplyNumberWithZero(int givenSource){
        // given
        int firstNumber = givenSource;
        int secondNumber = 0;

        // when
        int actual = calculatorTest.multiply(firstNumber, secondNumber);

        // then
        Assertions.assertTrue(actual == 0);
    }

    @ParameterizedTest(name = "1st={0}, 2nd={1}")
    @CsvSource(value = {"-10, -1", "-10, -20", "-3, -45"})
    public void should_returnTrue_when_multiplyTwoNegativeNumbers(int givenFirstNumber, int givenSecondNumber){
        // given
        int firstNumber = givenFirstNumber;
        int secondNumber = givenSecondNumber;

        // when
        int actual = calculatorTest.multiply(firstNumber, secondNumber);

        // then
        Assertions.assertTrue(actual > 0);
    }

    @Test
    public void should_throwException_when_divideNumberWithZero(){
        // given
        int firstNumber = 10;
        int secondNumber = 0;

        // when
        Executable executable = () -> calculatorTest.divide(firstNumber, secondNumber);

        // then
        Assertions.assertThrows(ArithmeticException.class, executable);
    }

    class Calculator{
       int add(int a, int b){
           return a + b;
       }

       int multiply(int a, int b){
            return a * b;
        }

       int divide(int a, int b){
            return a / b;
        }
    }
}
