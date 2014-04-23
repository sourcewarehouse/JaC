package jaCTranslator;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javaReader.JavaLexer;
import javaReader.JavaParser;
import javaReader.JavaParser.ClassDeclarationContext;
import javaReader.JavaParser.ClassOrInterfaceModifierContext;
import javaReader.JavaParser.CompilationUnitContext;
import javaReader.JavaParser.ExpressionContext;
import javaReader.JavaParser.FormalParameterContext;
import javaReader.JavaParser.MethodDeclarationContext;
import javaReader.JavaParser.StatementContext;
import javaReader.JavaParser.TypeDeclarationContext;


public class JaC {
	
    public static void main(String[] args) throws IOException {
        String inputFile = "programm.java";
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit(); // parse
        //System.out.println(tree.toStringTree(parser));
        convert(tree);
        for(CppClass a : CppProgram.classes){
        	System.out.println(a);
        }
    }

	private static void convert(ParseTree tree) {
//		System.out.println(tree.getClass());
		if(tree instanceof CompilationUnitContext){
			for(int i = 0; i < tree.getChildCount()-1;i++){
				convert(tree.getChild(i));
			}
		}
		else if(tree instanceof TypeDeclarationContext){
			convert(tree.getChild(1));
		}
		else if(tree instanceof ClassDeclarationContext){
			CppProgram.classes.add(new CppClass(tree.getChild(1).getText()));
			CppProgram.pointer++;
			convert(tree.getChild(2));
		}
		else if(tree instanceof ClassOrInterfaceModifierContext){
			CppProgram.classes.get(CppProgram.pointer).modifierBuffer.add(tree.getChild(0).getText());
		}
		else if(tree instanceof MethodDeclarationContext){
			CppProgram.classes.get(CppProgram.pointer).newMethod();
			CppProgram.classes.get(CppProgram.pointer).addToMethod("returnType",tree.getChild(0).getText());
			CppProgram.classes.get(CppProgram.pointer).addToMethod("name",tree.getChild(1).getText());
			convert(tree.getChild(2));
			convert(tree.getChild(3));
		}
		else if(tree instanceof FormalParameterContext){
			CppProgram.classes.get(CppProgram.pointer).addToMethod("parameter",tree.getChild(0).getText() + " " + tree.getChild(1).getText());
		}
		else if(tree instanceof StatementContext){
			if(tree.getChildCount() > 2){
				CppProgram.classes.get(CppProgram.pointer).statementOpen = true;
				CppProgram.classes.get(CppProgram.pointer).statementBuffer.add(tree.getChild(0).getText());
				CppProgram.classes.get(CppProgram.pointer).statementBuffer.add("(");
				convert(tree.getChild(1));
				CppProgram.classes.get(CppProgram.pointer).statementBuffer.add("){");
				convert(tree.getChild(2));
				CppProgram.classes.get(CppProgram.pointer).statementBuffer.add("}");
				CppProgram.classes.get(CppProgram.pointer).newStatement();
				CppProgram.classes.get(CppProgram.pointer).statementOpen = false;
			}
			else if (tree.getChildCount() > 1){
				convert(tree.getChild(0));
			}
			else{
				if(CppProgram.classes.get(CppProgram.pointer).statementOpen){
					convert(tree.getChild(0));
					CppProgram.classes.get(CppProgram.pointer).statementBuffer.add(";");
				}
				else{
					convert(tree.getChild(0));
					CppProgram.classes.get(CppProgram.pointer).statementBuffer.add(";");
					CppProgram.classes.get(CppProgram.pointer).newStatement();
				}
			}
		}
		else if(tree instanceof ExpressionContext){
			CppProgram.classes.get(CppProgram.pointer).statementBuffer.add(tree.getText());
		}
		else{
			for(int i = 0; i < tree.getChildCount();i++){
				convert(tree.getChild(i));
			}
		}
	}
    
    
}

