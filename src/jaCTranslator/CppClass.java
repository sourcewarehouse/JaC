package jaCTranslator;

import java.util.ArrayList;

public class CppClass {
	String name;
	ArrayList<CppField> fields;
	ArrayList<CppMethod> methods;
	private int methodP = -1;
	//private int fieldP = -1;
	
	String modifierBuffer = "";
	ArrayList<String> statementBuffer = new ArrayList<String>();
	
	public CppClass(String name) {
		super();
		this.name = name;
		this.methods = new ArrayList<CppMethod>();
		this.fields = new ArrayList<CppField>();
	}
	
	public CppMethod getMethod(){
		return methods.get(methodP);
	}
	
	public void addModifier(String modifier){
		this.modifierBuffer = modifier;
	}

	public void newMethod() {
		methods.add(new CppMethod(modifierBuffer));
		modifierBuffer = "";
		methodP++;
	}
	
	public void addParameterToMethod(String Type, String name, boolean isArray){
		methods.get(methodP).setParameter(Type, name, isArray);
	}
	
	public void addReturnTypeToMethod(String Type){
		methods.get(methodP).setReturnType(Type);
	}
	
	public void addNameToMethod(String Name){
		methods.get(methodP).setName(Name);
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
