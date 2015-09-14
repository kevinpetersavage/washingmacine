package com.washing;

import org.eclipse.jface.text.BadLocationException;

public interface ContentCleaner {
    public String clean(String content) throws BadLocationException;
}
