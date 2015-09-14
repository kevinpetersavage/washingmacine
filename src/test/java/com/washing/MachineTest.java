package com.washing;

import org.eclipse.jface.text.BadLocationException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class MachineTest {
    @Test
    public void shouldCleanAllFilesInDir() throws IOException {
        String directory = "directory";
        DirectoryScanner scanner = mock(DirectoryScanner.class);
        FileCleaner cleaner = mock(FileCleaner.class);

        List<Path> expectedPaths = Stream.of("file1","file2").map(Paths::get).collect(Collectors.toList());
        when(scanner.scan(directory)).thenReturn(expectedPaths.stream());

        new Machine(scanner, cleaner).clean(directory);

        expectedPaths.forEach((file) -> {
            try {
                verify(cleaner).clean(file);
            } catch (IOException | BadLocationException e) {
                new RuntimeException();
            }
        });
    }
}
