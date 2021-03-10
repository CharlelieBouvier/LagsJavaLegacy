package com.tof.app;

import java.io.IOException;
import java.io.InputStream;

public class InputReader {

    private final InputStream inputStream;

    public InputReader(InputStream inputStream) {

        this.inputStream = inputStream;
    }

    public int read() throws IOException {
        return this.inputStream.read();
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }
}
