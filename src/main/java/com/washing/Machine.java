package com.washing;

import org.eclipse.jface.text.BadLocationException;

import java.io.IOException;

public class Machine {
    private final DirectoryScanner scanner;
    private final FileCleaner cleaner;

    public Machine(DirectoryScanner scanner, FileCleaner cleaner) {
        this.scanner = scanner;
        this.cleaner = cleaner;
    }

    public void clean(String directory) throws IOException {
        scanner.scan(directory).forEach((file) -> {
            try {
                cleaner.clean(file);
            } catch (IOException | BadLocationException e) {
                new RuntimeException(e);
            }
        });
    }
}
