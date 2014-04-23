package jaCTranslator;

public class CppField {
	String ClassModifier;
	String FieldDeclaration;
	
	public CppField(String classModifier, String fieldDeclaration) {
		super();
		ClassModifier = classModifier;
		FieldDeclaration = fieldDeclaration;
	}
	
	public CppField(String fieldDeclaration) {
		super();
		ClassModifier = "public";
		FieldDeclaration = fieldDeclaration;
	}
}
