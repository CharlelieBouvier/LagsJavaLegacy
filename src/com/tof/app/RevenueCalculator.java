package com.tof.app;

import java.util.ArrayList;
import java.util.List;

public class RevenueCalculator {

    public Double revenue(List<Order> orders) {
        if (orders.size() == 0)
            return 0.0;
        Order firstOrder = orders.get(0);
        List<Order> compatibleOrders = new ArrayList<>();
        for (Order o : orders) {
            if (!DateHelper.getDateFromInt(o.getStartDate()).isBefore(DateHelper.getEndDate(firstOrder))) {
                compatibleOrders.add(o);
            }
        }

        List<Order> followingOrders = new ArrayList<>();
        for (int i = 1; i < orders.size(); i++) {
            followingOrders.add(orders.get(i));
        }
        double revenueWithFirstOrder = firstOrder.getPrice() + revenue(compatibleOrders);
        double revenueWithFollowingOrders = revenue(followingOrders);

        return Math.max(revenueWithFirstOrder, revenueWithFollowingOrders);
    }
}
