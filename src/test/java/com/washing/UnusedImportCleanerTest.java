package com.washing;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.Paths.get;
import static org.fest.assertions.Assertions.assertThat;

public class UnusedImportCleanerTest {
    @Test
    public void shouldRemoveImport() throws IOException, JavaModelException, BadLocationException {
        String path = "src/test/java/com/washing/dirty/UnusedImport.java";
        String originalContent = new String(Files.readAllBytes(get(path)));

        String newContent = new UnusedImportCleaner().clean(originalContent);

        String[] lines = originalContent.split("\n");
        StringBuffer buffer = new StringBuffer();
        for (String line : lines) {
            if (!line.contains("Double")) {
                buffer.append(line).append('\n');
            }
        }

        assertThat(buffer.toString())
                .isEqualTo(newContent);
    }

    @Test
    public void shouldLeaveVariousImports() throws IOException, JavaModelException, BadLocationException {
        String path = "src/test/java/com/washing/dirty/VariousImportUsages.java";
        String originalContent = new String(Files.readAllBytes(get(path)));
        String newContent = new UnusedImportCleaner().clean(originalContent);

        assertThat(originalContent).isEqualTo(newContent);
    }

}
