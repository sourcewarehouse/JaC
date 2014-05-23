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
	
	public static int findMain(){
		for(int j = 0; j < classes.size();j++){
			for(int i = 0; i < classes.get(j).methods.size();i++){
				if(classes.get(j).methods.get(i).getName() == "main"){
					return j;
				}
			}
		}
		return -1;
	}
	
	public static int findMain2(){
		for(int j = 0; j < classes.size();j++){
			for(int i = 0; i < classes.get(j).methods.size();i++){
				if(classes.get(j).methods.get(i).getName() == "main"){
					return i;
				}
			}
		}
		return -1;
	}
}
