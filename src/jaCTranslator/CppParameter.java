package jaCTranslator;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class CppParameter {
	private String Type;
	private String name;
	private boolean array;
	
	public CppParameter(String Type, String name, boolean isArray){
		this.Type = Type;
		this.name = name;
		array = isArray;
	}

	@Override
	public String toString() {
		return "CppParameter [Type=" + Type + ", name=" + name + ", array="
				+ array + "]";
	}

	public String toCpp() {
		String result = "";
		if(array){
			result += Type + " " + name + "[]";
		}
		else{
			result += Type + " " + name;
		}
		return result;
	}
	
}
