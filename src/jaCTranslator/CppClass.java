package jaCTranslator;

import java.util.ArrayList;

public class CppClass {
	String name;
	ArrayList<CppField> fields;
	ArrayList<CppMethod> methods;
	int methodP = -1;
	int fieldP = -1;
	
	ArrayList<String> modifierBuffer = new ArrayList<String>();
	ArrayList<String> statementBuffer = new ArrayList<String>();
	
	public CppClass(String name) {
		super();
		this.name = name;
		this.methods = new ArrayList<CppMethod>();
		this.fields = new ArrayList<CppField>();
	}

	public void newMethod() {
		methods.add(new CppMethod(modifierBuffer));
		modifierBuffer.clear();
		methodP++;
	}
	
	public void newStatement(){
		String statement = "";
		for(String s : statementBuffer){
			statement += s; 
		}
		methods.get(methodP).setStatement(statement);
		statementBuffer.clear();
	}
	
	public void addToMethod(String field, String value){
		switch(field){
		case "returnType":
			methods.get(methodP).setReturnType(value);
			break;
		case "name":
			methods.get(methodP).setName(value);
			break;
		case "parameter":
			methods.get(methodP).setParameter(value);
			break;
		case "statement":
			methods.get(methodP).setStatement(value);
			break;
		}
	}

	@Override
	public String toString() {
		String result = "";
		result += "name:" +  this.name + "\n";
		for(CppMethod a : this.methods){
			result += a.toString() + "\n";
		}
		return result;
	}

	
	
	
}
