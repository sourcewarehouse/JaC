package jaCTranslator;

import java.util.ArrayList;

public class CppMethod {
	ArrayList<String> modifiers;
	String returnType;
	String name;
	ArrayList<String> parameters;
	ArrayList<String> statements;
	
	
	public CppMethod(ArrayList<String> buffer) {
		super();
		this.modifiers = (ArrayList<String>) buffer.clone();
		this.statements = new ArrayList<String>();
		this.parameters = new ArrayList<String>();
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getParameters() {
		return parameters;
	}

	public void setParameter(String parameter) {
		this.parameters.add(parameter);
	}

	public ArrayList<String> getStatements() {
		return statements;
	}

	public void setStatement(String statement) {
		this.statements.add(statement);
	}

	@Override
	public String toString() {
		return "CppMethod [modifiers=" + modifiers + ", returnType="
				+ returnType + ", name=" + name + ", parameters=" + parameters
				+ ", statements=" + statements + "]";
	}
	
	
	
}
