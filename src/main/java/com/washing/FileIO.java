package com.washing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIO {
    public String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    public void write(Path path, String cleaned) throws IOException {
        Files.write(path, cleaned.getBytes());
    }
}
