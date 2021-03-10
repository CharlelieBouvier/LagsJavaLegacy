package com.tof.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;

public class DateHelper {
    public DateHelper() {
    }

    static Instant getEndDate(Order order) {
        Instant startDate = getDateFromInt(order.getStartDate());
        return startDate.atZone(ZoneId.systemDefault()).plusDays(order.getDuration()).toInstant();
    }

    static protected Instant getDateFromInt(int d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyDDD");
        try {
            return formatter.parse(String.valueOf(d)).toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}