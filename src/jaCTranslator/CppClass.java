package jaCTranslator;

import java.util.ArrayList;

public class CppClass {
	String name;
	ArrayList<CppField> fields;
	ArrayList<CppMethod> methods;
	private int methodP = -1;
	
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
	
	public void newField(String type, String declaration, boolean is_array){
		fields.add(new CppField(modifierBuffer, type, declaration,is_array));
		modifierBuffer = "";
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
		result += "Fields:";
		for(CppField a : this.fields){
			result += a.toString() + "\n";
		}
		return result;
	}

	public String CppHeader() {
		String result = "";
		result += "public:\n";
		for(int i = 0; i < fields.size();i++){
			if(fields.get(i).ClassModifier.equals("public")){
				result += fields.get(i).toCpp();
			}
		}
		for(int i = 0; i < methods.size();i++){
			if(methods.get(i).getModifier().equals("public")){
				result += methods.get(i).getReturnType() + " ";
				result += methods.get(i).getName() + "(";
				for(int j = 0; j < methods.get(i).parameters.size();j++){
					result += methods.get(i).parameters.get(j).toCpp();
					if(j + 1 < methods.get(i).parameters.size()){
						result += ",";
					}
				}
				result += ");\n";
			}
		}
		result += "private:\n";
		for(int i = 0; i < fields.size();i++){
			if(fields.get(i).ClassModifier.equals("private")){
				result += fields.get(i).toCpp();
			}
		}
		for(int i = 0; i < methods.size();i++){
			if(methods.get(i).getModifier().equals("private")){
				result += methods.get(i).getReturnType() + " ";
				result += methods.get(i).getName() + "(";
				for(int j = 0; j < methods.get(i).parameters.size();j++){
					result += methods.get(i).parameters.get(j).toCpp();
					if(j + 1 < methods.get(i).parameters.size()){
						result += ",";
					}
					result += ");\n";
				}		
			}
		}
		result += "protected:\n";
		for(int i = 0; i < fields.size();i++){
			if(fields.get(i).ClassModifier.equals("protected")){
				result += fields.get(i).toCpp();
			}
		}
		for(int i = 0; i < methods.size();i++){
			if(methods.get(i).getModifier().equals("protected")){
				result += methods.get(i).getReturnType() + " ";
				result += methods.get(i).getName() + "(";
				for(int j = 0; j < methods.get(i).parameters.size();j++){
					result += methods.get(i).parameters.get(j).toCpp();
					if(j + 1 < methods.get(i).parameters.size()){
						result += ",";
					}
					result += ");\n";
				}		
			}
		}
		return result;
	}

	
	
	
}
