package com.washing;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Utility application to find out what the ast visitor is seeing.
 */
public class AstVisitorMessages {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String file = args[0];

        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(new FileIO().read(Paths.get(file)).toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setBindingsRecovery(true);
        parser.setResolveBindings(true);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(ASTVisitor.class);

        MethodHandler handler = (self, thisMethod, proceed, args1) -> {
            if (!Arrays.asList(args1).stream().anyMatch(arg -> arg.toString().contains("Exception"))){
                return true;

            }
            System.out.println("--------------");
            System.out.println(thisMethod);
            System.out.println(Arrays.asList(args1));
            return true;
        };

        ASTVisitor visitor = (ASTVisitor) factory.create(new Class<?>[0], new Object[0], handler);
        cu.accept(visitor);
    }
}
