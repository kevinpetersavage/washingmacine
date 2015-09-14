package com.washing;

import org.eclipse.jface.text.BadLocationException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

public class FileCleanerTest {
    @Test
    public void shouldReadFileCleanItThenSaveIt() throws BadLocationException, IOException {
        Path path = Paths.get("file");
        String content = "dirty";

        ContentCleaner contentCleaner = mock(ContentCleaner.class);
        FileIO fileIO = mock(FileIO.class);
        String cleaned = "cleaned";

        when(fileIO.read(path)).thenReturn(content);
        when(contentCleaner.clean(content)).thenReturn(cleaned);

        new FileCleaner(contentCleaner, fileIO).clean(path);

        verify(fileIO).write(path, cleaned);
    }
}