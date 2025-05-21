package com.scaler.productserviceapr21capstone.calculator;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class CalculatorControllerTest
{
//    CalculatorService calculatorService = Mockito.mock(CalculatorService.class);
//
//    CalculatorController calculatorController = new CalculatorController(calculatorService);

    @MockitoBean
    CalculatorService calculatorService;

    @Autowired
    CalculatorController calculatorController;

    @Test
    public void testSumAcceptsTwoIntegersReturnsSumAsInteger()
    {
        when(calculatorService.add(5, 10)).thenReturn(15);
        when(calculatorService.add(6, 10)).thenReturn(16);

        //Arrange
        int a = 6;
        int b = 10;
        int expectedResult = 16;

        //Act
        int actualResult = calculatorController.sum(a, b);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSumAcceptsAnyTwoIntegersReturnsSumAsInteger()
    {
        when(calculatorService.add(5, 10)).thenReturn(15);
        when(calculatorService.add(anyInt(), anyInt())).thenReturn(100);

        //Arrange
        int a = 10000;
        int b = 200000;
        int expectedResult = 100;

        //Act
        int actualResult = calculatorController.sum(a, b);

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}