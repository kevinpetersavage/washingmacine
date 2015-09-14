package com.washing;

import org.eclipse.jface.text.BadLocationException;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, BadLocationException {
        String directory = args[0];

        Machine machine = new Machine(
                new DirectoryScanner(),
                new FileCleaner(
                        new UnusedImportCleaner(),
                        new FileIO()
                )
        );

        machine.clean(directory);
    }
}
