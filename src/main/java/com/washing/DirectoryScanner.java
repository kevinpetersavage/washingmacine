package com.washing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DirectoryScanner {
    public Stream<Path> scan(String directory) throws IOException {
        return Files.walk(Paths.get(directory));
    }
}
