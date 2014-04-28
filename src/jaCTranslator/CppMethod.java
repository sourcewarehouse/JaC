package jaCTranslator;

import java.util.ArrayList;

public class CppMethod {
	private String modifier;
	private String returnType;
	private String name;
	ArrayList<String> parameters;
	ArrayList<String> statements;
	
	
	public CppMethod(String buffer) {
		super();
		this.modifier = buffer;
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

	public String getModifier() {
		return modifier;
	}

	@Override
	public String toString() {
		return "CppMethod [modifiers=" + modifier + ", returnType="
				+ returnType + ", name=" + name + ", parameters=" + parameters
				+ ", statements=" + statements + "]";
	}
	
	
	
}
