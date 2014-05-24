package jaCTranslator;

import java.util.ArrayList;

public class CppMethod {
	public ArrayList<Integer> StatementP = new ArrayList<Integer>();
	public int StatementNr = -1;
	private String modifier;
	private String returnType;
	private String name;
	ArrayList<CppParameter> parameters;
	ArrayList<CppStatement> statements;
	public int writeP = 0;
	public boolean standalone = true;
	
	
	public CppMethod(String buffer) {
		super();
		this.modifier = buffer;
		this.statements = new ArrayList<CppStatement>();
		this.parameters = new ArrayList<CppParameter>();
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

	public void setParameter(String Type, String name, boolean isArray) {
		this.parameters.add(new CppParameter(Type,name,isArray));
	}

	public ArrayList<CppStatement> getStatements() {
		return statements;
	}

	public void appendStatement(String fragment) {
//		System.out.println("append: " + StatementNr);
		this.statements.get(StatementNr).send(fragment);
	}
	
	public void MovePointer() {
//		System.out.println("move: " + StatementNr);
//		System.out.println("move: " + StatementP.toString());
		StatementNr += 1;
//		System.out.println("move2: " + StatementNr);
//		System.out.println("move2: " + StatementP.toString());
	}
	
	public void newLayer() {
//		System.out.println("new: " + StatementP.toString());
		StatementP.add(StatementNr);
//		System.out.println("new2: " + StatementP.toString());
	}
	
	public void endStatement(){
//		System.out.println("end: " + StatementNr);
//		System.out.println("end: " + StatementP.toString());
		statements.get(StatementP.get(StatementP.size()-1)).send(String.valueOf(StatementNr));
		StatementP.remove(StatementP.size()-1);
//		System.out.println("end2: " + StatementNr);
//		System.out.println("end2: " + StatementP.toString());
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

	public String toCpp() {
		String result = "";
		while(writeP < this.statements.size()){
			result += this.statements.get(writeP).toCpp(this);
		}
		return result;
	}
		
}
