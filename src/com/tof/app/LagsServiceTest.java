package com.tof.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LagsServiceTest {

    @Test
    public void getDateFromIntTest() {
        LagsService lagsService = new LagsService(null, null);
        assertEquals("2020-02-28T23:00:00Z", lagsService.getDateFromInt(2020060).toString());
    }
}