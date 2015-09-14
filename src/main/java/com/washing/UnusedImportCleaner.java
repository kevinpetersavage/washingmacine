package com.washing;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UnusedImportCleaner implements ContentCleaner {
    @Override
    public String clean(String content) throws BadLocationException {
        CompilationUnit cu = createCompilationUnit(content);

        Stream<ImportDeclaration> unusedImports = findUnusedImports(cu);

        ASTRewrite astRewrite = ASTRewrite.create(cu.getAST());

        unusedImports.forEach(i -> astRewrite.remove(i, null));

        Document document = new Document(content);
        astRewrite.rewriteAST(document, null).apply(document);

        return document.get();
    }

    private Stream<ImportDeclaration> findUnusedImports(CompilationUnit cu) {
        Stream<ImportDeclaration> imported = cu.imports().stream().map(i -> ((ImportDeclaration) i));

        List<String> used = new ArrayList<>();
        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(SimpleType node) {
                used.add(node.getName().getFullyQualifiedName());
                return super.visit(node);
            }

            @Override
            public boolean visit(MethodDeclaration node) {
                used.addAll(node.thrownExceptions());
                return super.visit(node);
            }
        });

        return imported.filter(i -> !used.contains(i.getName().getFullyQualifiedName().substring(i.getName().getFullyQualifiedName().lastIndexOf(".") + 1)));
    }

    private CompilationUnit createCompilationUnit(String originalContent) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(originalContent.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setBindingsRecovery(true);
        parser.setResolveBindings(true);

        return (CompilationUnit) parser.createAST(null);
    }

}
