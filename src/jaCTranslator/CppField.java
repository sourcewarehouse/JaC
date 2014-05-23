package jaCTranslator;

public class CppField {
	String ClassModifier;
	String type;
	String Declaration;
	boolean is_array;
	
	public CppField(String classModifier, String type, String declaration,boolean is_array) {
		super();
		ClassModifier = classModifier;
		this.type = type;
		Declaration = declaration;
		this.is_array = is_array;
	}

	@Override
	public String toString() {
		return "CppField [ClassModifier=" + ClassModifier + ", type=" + type
				+ ", Declaration=" + Declaration + ", is_array=" + is_array
				+ "]";
	}
	
	
}
