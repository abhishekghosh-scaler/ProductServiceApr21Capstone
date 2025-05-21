package com.scaler.productserviceapr21capstone.calculator;

import org.springframework.stereotype.Controller;

@Controller
public class CalculatorController
{
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService)
    {
        this.calculatorService = calculatorService;
    }

    public int sum(int a, int b)
    {
        System.out.println("Controller: Logic before add");
        int result = calculatorService.add(a, b);
        System.out.println("Controller: Logic after add");

        return result;
    }
}
