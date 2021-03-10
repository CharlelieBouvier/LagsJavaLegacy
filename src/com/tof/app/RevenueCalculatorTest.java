package com.tof.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RevenueCalculatorTest {

    @Test
    public void givenAnEmptyListOrderReturn0() {
        RevenueCalculator revenueCalculator = new RevenueCalculator();
        Double result = revenueCalculator.revenue(List.of());
        assertEquals(0, result);
    }

    @Test
    public void givenOneOrderReturnThatOrderPrice() {
        RevenueCalculator revenueCalculator = new RevenueCalculator();
        Double result = revenueCalculator.revenue(List.of(new Order("1", 2021001,5,42D)));
        assertEquals(42, result);
    }

    @Test
    public void givenTwoIncompatibleOrdersReturnBestPrice() {
        RevenueCalculator revenueCalculator = new RevenueCalculator();
        Double result = revenueCalculator.revenue(List.of(new Order("1", 2021001,5,42D), new Order("2", 2021002,5,700D)));
        assertEquals(700, result);
    }
    @Test
    public void givenSeveralOrdersReturnBestRevenue() {
        RevenueCalculator revenueCalculator = new RevenueCalculator();
        Double result = revenueCalculator.revenue(List.of(new Order("1", 2021001,5,42.00),
                                                          new Order("2", 2021002,10,700.00),
                                                          new Order("3", 2021006,10,1000.00),
                                                          new Order("4", 2021007,10,2000.00)));
        assertEquals(2042, result);
    }
}