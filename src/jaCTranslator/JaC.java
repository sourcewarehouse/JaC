package jaCTranslator;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import gui.Window;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javaReader.JavaLexer;
import javaReader.JavaParser;
import javaReader.JavaParser.BlockContext;
import javaReader.JavaParser.ClassBodyDeclarationContext;
import javaReader.JavaParser.ClassDeclarationContext;
import javaReader.JavaParser.ConstructorDeclarationContext;
import javaReader.JavaParser.ExpressionContext;
import javaReader.JavaParser.FieldDeclarationContext;
import javaReader.JavaParser.FormalParameterContext;
import javaReader.JavaParser.LocalVariableDeclarationContext;
import javaReader.JavaParser.MethodDeclarationContext;
import javaReader.JavaParser.StatementContext;
import javaReader.JavaParser.StatementExpressionContext;
import javaReader.JavaParser.VariableDeclaratorContext;


public class JaC {
	
    public static void main(String[] args) throws IOException {
    	Window frame = new Window();
		frame.setVisible(true);
        
    }
    
    public static void trun(String inputFile) throws IOException {
    	InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        //------------------------------
        
        convert(tree);
        CppWriter.convert();
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
			if(tree.getChild(0).getText().equals("static") || tree.getChildCount() == 1){
				CppProgram.curClass().addModifier("public");
			}
			else{
				CppProgram.curClass().addModifier(tree.getChild(0).getText());
			}
			convert(tree.getChild(tree.getChildCount() - 1));
		}
		else if(tree instanceof ConstructorDeclarationContext){
			CppProgram.curClass().constructor = true;
			CppProgram.curClass().newConstructor();
			CppProgram.curClass().addNameToMethod(tree.getChild(0).getText());
			if(tree.getChild(1).getChildCount() > 2){
				convert(tree.getChild(1));
			}
			convert(tree.getChild(2));
			CppProgram.curClass().constructor = false;
		}
		else if(tree instanceof MethodDeclarationContext){
			CppProgram.curClass().newMethod();
			CppProgram.curClass().addReturnTypeToMethod(tree.getChild(0).getText());
			CppProgram.curClass().addNameToMethod(tree.getChild(1).getText());
			if(tree.getChild(2).getChildCount() > 2){
				convert(tree.getChild(2));
			}
			convert(tree.getChild(3));
		}
		else if(tree instanceof FormalParameterContext){
			if(tree.getChild(0).getChildCount() > 1){
				CppProgram.curClass().addParameterToMethod(tree.getChild(0).getChild(0).getText(), tree.getChild(1).getText(), true);
			}
			else{
				CppProgram.curClass().addParameterToMethod(tree.getChild(0).getText(), tree.getChild(1).getText(), false);
			}
		}
		else if(tree instanceof LocalVariableDeclarationContext){
			CppProgram.curClass().getMethod().statements.add(new LocalVariableDeclarationStatement());
			CppProgram.curClass().getMethod().MovePointer();
			CppProgram.curClass().getMethod().appendStatement(tree.getChild(0).getText());
			convert(tree.getChild(1));
		}
		else if(tree instanceof StatementContext){
			ParseStatement(tree);
		}
		else if(tree instanceof VariableDeclaratorContext){
			if(tree.getChildCount() > 1){
				CppProgram.curClass().getMethod().appendStatement(tree.getChild(0).getText());
				CppProgram.curClass().getMethod().newLayer();
				convert(tree.getChild(2));
				CppProgram.curClass().getMethod().endStatement();
			}
			else{
				CppProgram.curClass().getMethod().appendStatement(tree.getChild(0).getText());
			}
		}
		else if(tree instanceof ExpressionContext){
			CppProgram.curClass().getMethod().statements.add(new ExpressionStatement());
			CppProgram.curClass().getMethod().MovePointer();
			CppProgram.curClass().getMethod().appendStatement(tree.getText());
		}
		else if(tree instanceof FieldDeclarationContext){
			boolean is_array = false;
			String type;
			if(tree.getChild(0).getChildCount() > 1){
				is_array = true;
				type = tree.getChild(0).getChild(0).getText();
			}
			else{
				type = tree.getChild(0).getText();
			}
			String declaration = tree.getChild(1).getText();
			CppProgram.curClass().newField(type, declaration, is_array);
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
		else if(tree.getChild(0) instanceof StatementExpressionContext){
			convert(tree.getChild(0));
		}

		else{
			switch(tree.getChild(0).getText()){
				case "assert":
					CppProgram.curClass().getMethod().statements.add(new AssertStatement());
					CppProgram.curClass().getMethod().MovePointer();
					CppProgram.curClass().getMethod().newLayer();
					convert(tree.getChild(1));
					CppProgram.curClass().getMethod().endStatement();
					break;
				case "if":
					if(tree.getChildCount() > 3){
						CppProgram.curClass().getMethod().statements.add(new IfStatement());
						CppProgram.curClass().getMethod().MovePointer();
						CppProgram.curClass().getMethod().newLayer();
						convert(tree.getChild(1).getChild(1));
						CppProgram.curClass().getMethod().statements.get(CppProgram.curClass().getMethod().StatementP.get(
								CppProgram.curClass().getMethod().StatementP.size()-1)).send(String.valueOf(CppProgram.curClass().getMethod().StatementNr));
						convert(tree.getChild(2));
						CppProgram.curClass().getMethod().statements.get(CppProgram.curClass().getMethod().StatementP.get(
								CppProgram.curClass().getMethod().StatementP.size()-1)).send(String.valueOf(CppProgram.curClass().getMethod().StatementNr));
						convert(tree.getChild(4));
						CppProgram.curClass().getMethod().endStatement();
					}
					else{
						CppProgram.curClass().getMethod().statements.add(new IfStatement());
						CppProgram.curClass().getMethod().MovePointer();
						CppProgram.curClass().getMethod().newLayer();
						convert(tree.getChild(1).getChild(1));
						CppProgram.curClass().getMethod().statements.get(CppProgram.curClass().getMethod().StatementP.get(
								CppProgram.curClass().getMethod().StatementP.size()-1)).send(String.valueOf(CppProgram.curClass().getMethod().StatementNr));
						convert(tree.getChild(2));
						CppProgram.curClass().getMethod().endStatement();
					}
					break;
				case "for":
					CppProgram.curClass().getMethod().statements.add(new ForStatement());
					CppProgram.curClass().getMethod().MovePointer();
					CppProgram.curClass().getMethod().newLayer();
					convert(tree.getChild(2).getChild(0));
					CppProgram.curClass().getMethod().statements.get(CppProgram.curClass().getMethod().StatementP.get(
							CppProgram.curClass().getMethod().StatementP.size()-1)).send(String.valueOf(CppProgram.curClass().getMethod().StatementNr));
					convert(tree.getChild(2).getChild(2));
					CppProgram.curClass().getMethod().statements.get(CppProgram.curClass().getMethod().StatementP.get(
							CppProgram.curClass().getMethod().StatementP.size()-1)).send(String.valueOf(CppProgram.curClass().getMethod().StatementNr));
					convert(tree.getChild(2).getChild(4));
					CppProgram.curClass().getMethod().statements.get(CppProgram.curClass().getMethod().StatementP.get(
							CppProgram.curClass().getMethod().StatementP.size()-1)).send(String.valueOf(CppProgram.curClass().getMethod().StatementNr));
					convert(tree.getChild(4));
					CppProgram.curClass().getMethod().endStatement();
					break;
					

				case "while":
					CppProgram.curClass().getMethod().statements.add(new WhileStatement());
					CppProgram.curClass().getMethod().MovePointer();
					CppProgram.curClass().getMethod().newLayer();
					convert(tree.getChild(1).getChild(1));
					CppProgram.curClass().getMethod().statements.get(CppProgram.curClass().getMethod().StatementP.get(
							CppProgram.curClass().getMethod().StatementP.size()-1)).send(String.valueOf(CppProgram.curClass().getMethod().StatementNr));
					convert(tree.getChild(2));
					CppProgram.curClass().getMethod().endStatement();
					break;
					
					 
				case "do":
					CppProgram.curClass().getMethod().statements.add(new DoStatement());
					CppProgram.curClass().getMethod().MovePointer();
					CppProgram.curClass().getMethod().newLayer();
					convert(tree.getChild(3).getChild(1));
					CppProgram.curClass().getMethod().statements.get(CppProgram.curClass().getMethod().StatementP.get(
							CppProgram.curClass().getMethod().StatementP.size()-1)).send(String.valueOf(CppProgram.curClass().getMethod().StatementNr));
					convert(tree.getChild(1));
					CppProgram.curClass().getMethod().endStatement();
					break;
					
				case "try":
					CppProgram.curClass().getMethod().statements.add(new TryStatement());
					CppProgram.curClass().getMethod().MovePointer();

					
					
					break;
				case "switch":
					CppProgram.curClass().getMethod().statements.add(new SwitchStatement());
					CppProgram.curClass().getMethod().MovePointer();

					
					break;
				case "return":
					if(tree.getChildCount() > 2){
						CppProgram.curClass().getMethod().statements.add(new ReturnStatement());
						CppProgram.curClass().getMethod().MovePointer();
						CppProgram.curClass().getMethod().newLayer();
						convert(tree.getChild(1));					
						CppProgram.curClass().getMethod().endStatement();
					}
					else{
						CppProgram.curClass().getMethod().statements.add(new ReturnStatement());
						CppProgram.curClass().getMethod().MovePointer();
					}
					break;
				case "throw":
					CppProgram.curClass().getMethod().statements.add(new ThrowStatement());
					CppProgram.curClass().getMethod().MovePointer();
					CppProgram.curClass().getMethod().newLayer();
					convert(tree.getChild(1));
					CppProgram.curClass().getMethod().endStatement();
					break;
			}
		}
		
	}   
}

