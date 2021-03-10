package com.tof.app;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LagsTest {

    @Test
    public void goldenMaster() throws URISyntaxException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        InputStream inputStream = new ByteArrayInputStream("L\r\nC\r\nQ\r\n".getBytes());
        Lags.printStream = printStream;
        Lags.inputStream = inputStream;
        Lags.main(null);
        String expected = Files.readString(Path.of(this.getClass().getClassLoader().getResource("ExpectedOutput.txt").toURI())).replace("\r", "").replace("\n", "");
        assertEquals(expected, baos.toString().replace("\r", "").replace("\n", ""));
    }

    @Test
    public void pathToOrderTest() throws URISyntaxException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        InputStream inputStream = new ByteArrayInputStream("C\r\nQ\r\n".getBytes());
        Lags.printStream = printStream;
        Lags.inputStream = inputStream;
        Lags.main(new String[]{"123456789.CSV"});
        String expected = "123456789";
        assertTrue(baos.toString().contains(expected));
    }
}