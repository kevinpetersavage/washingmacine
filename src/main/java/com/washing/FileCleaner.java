package com.washing;

import org.eclipse.jface.text.BadLocationException;

import java.io.IOException;
import java.nio.file.Path;

public class FileCleaner {
    private final ContentCleaner contentCleaner;
    private final FileIO fileIO;

    public FileCleaner(ContentCleaner contentCleaner, FileIO fileIO) {
        this.contentCleaner = contentCleaner;
        this.fileIO = fileIO;
    }

    public void clean(Path file) throws IOException, BadLocationException {
        fileIO.write(file,
                contentCleaner.clean(
                        fileIO.read(file)
                )
        );
    }
}
