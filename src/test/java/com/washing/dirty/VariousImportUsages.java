package com.washing.dirty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VariousImportUsages {
    public List<Set<Map<String,Integer>>> things() throws UnsupportedOperationException, IOException {
        Files.walk(Paths.get(""), 1);
        return new ArrayList<>();
    }
}
