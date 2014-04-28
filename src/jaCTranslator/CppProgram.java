package jaCTranslator;

import java.util.ArrayList;

public class CppProgram {
	static int main;
	static ArrayList<CppClass> classes = new ArrayList<CppClass>();
	static int pointer = -1;
	
	
	public static CppClass curClass(){
		return classes.get(pointer);
	}
	
	public static void newClass(CppClass cls){
		classes.add(cls);
		pointer += 1;
	}
	
}
