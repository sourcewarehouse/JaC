package jaCTranslator;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javaReader.JavaLexer;
import javaReader.JavaParser;
import javaReader.JavaParser.BlockContext;
import javaReader.JavaParser.ClassBodyDeclarationContext;
import javaReader.JavaParser.ClassDeclarationContext;
import javaReader.JavaParser.FormalParameterContext;
import javaReader.JavaParser.LocalVariableDeclarationContext;
import javaReader.JavaParser.MethodDeclarationContext;
import javaReader.JavaParser.StatementContext;
import javaReader.JavaParser.StatementExpressionContext;


public class JaC {
	
    public static void main(String[] args) throws IOException {
    	//standard parsing of text with ANTLR generated parser 
        String inputFile = "programm.java";
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        //------------------------------
        
        convert(tree);
        for(CppClass c : CppProgram.classes){
        	System.out.println(c);
        }
    }

	private static void convert(ParseTree tree) {
		if(tree instanceof ClassDeclarationContext){
			CppProgram.newClass(new CppClass(tree.getChild(1).getText()));
			convert(tree.getChild(2));
		}
		else if(tree instanceof ClassBodyDeclarationContext){
			if(tree.getChild(0).getText().equals("static")){
				CppProgram.curClass().addModifier("public");
			}
			else{
				CppProgram.curClass().addModifier(tree.getChild(0).getText());
			}
			convert(tree.getChild(tree.getChildCount() - 1));
		}
		else if(tree instanceof MethodDeclarationContext){
			CppProgram.curClass().newMethod();
			CppProgram.curClass().addToMethod("returnType", tree.getChild(0).getText());
			CppProgram.curClass().addToMethod("name", tree.getChild(1).getText());
			if(tree.getChild(2).getChildCount() > 2){
				convert(tree.getChild(2));
			}
			convert(tree.getChild(3));
		}
		else if(tree instanceof FormalParameterContext){
			CppProgram.curClass().addToMethod("parameter", tree.getChild(0).getText() + " " + tree.getChild(1).getText());
		}
		else if(tree instanceof LocalVariableDeclarationContext){
			if(tree.getChildCount() > 2){
				CppProgram.curClass().getMethod().setStatement(tree.getChild(1).getText() + " " + tree.getChild(2).getText());
			}
			else{
				CppProgram.curClass().getMethod().setStatement(tree.getChild(0).getText() + " " + tree.getChild(1).getText());
			}
		}
		else if(tree instanceof StatementContext){
			ParseStatement(tree);
		}
		else{
			for(int i = 0; i < tree.getChildCount();i++){
				convert(tree.getChild(i));
			}
		}
	}

	private static void ParseStatement(ParseTree tree) {
		if(tree.getChild(0) instanceof BlockContext){
			convert(tree.getChild(0));
		}
		else{
			switch(tree.getChild(0).getText()){
				case "assert":
					CppProgram.curClass().addToMethod("statement", "assert(" + tree.getChild(1).getText() + ")");
					break;
				case "if":
					if(tree.getChildCount() > 3){
						CppProgram.curClass().addToMethod("statement", "if" + tree.getChild(1).getText() + "{");
						convert(tree.getChild(2));
						CppProgram.curClass().addToMethod("statement", "}");
					}
					else{
						CppProgram.curClass().addToMethod("statement", "if" + tree.getChild(1).getText() + "{");
						convert(tree.getChild(2));
						CppProgram.curClass().addToMethod("statement", "}");
						CppProgram.curClass().addToMethod("statement", "else{");
						convert(tree.getChild(2));
						CppProgram.curClass().addToMethod("statement", "}");
					}
					break;
				case "for":
					CppProgram.curClass().addToMethod("statement", "for" + tree.getChild(1).getText() + "{");
					convert(tree.getChild(2));
					CppProgram.curClass().addToMethod("statement", "}");
					break;
				case "while":
					CppProgram.curClass().addToMethod("statement", "while" + tree.getChild(1).getText() + "{");
					convert(tree.getChild(2));
					CppProgram.curClass().addToMethod("statement", "}");
					break;
				case "do":
					CppProgram.curClass().addToMethod("statement", "do{" );
					convert(tree.getChild(2));
					CppProgram.curClass().addToMethod("statement", "}while" + tree.getChild(3));
					break;
				case "try":
					CppProgram.curClass().addToMethod("statement", "try{" );
					convert(tree.getChild(1));
					CppProgram.curClass().addToMethod("statement", "}");
					String temp = "";
					for(int i = 0; i < tree.getChild(2).getChildCount()-1;i++){
						temp += tree.getChild(2).getChild(i).getText();
					}
					CppProgram.curClass().addToMethod("statement", temp + "{");
					convert(tree.getChild(2).getChild(tree.getChild(2).getChildCount()-1));
					CppProgram.curClass().addToMethod("statement","}");
					break;
				case "switch":
					//TODO
					break;
				case "return":
					if(tree.getChildCount() > 2){
						CppProgram.curClass().addToMethod("statement", "return " + tree.getChild(1).getText());	
					}
					else{
						CppProgram.curClass().addToMethod("statement", "return");
					}
					break;
				case "throw":
					CppProgram.curClass().addToMethod("statement", "throw " + tree.getChild(1));
					break;
				default:
					CppProgram.curClass().addToMethod("statement", tree.getChild(0).getText());
					break;
			}
		}
		
	}   
}

