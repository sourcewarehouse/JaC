package jaCTranslator;

import java.util.ArrayList;

public class CppProgram {
	static int main;
	static ArrayList<CppClass> classes = new ArrayList<CppClass>();
	static int pointer = -1;
	
	public static void findMain() {
		for(int i = 0; i < classes.size();i++){
			for(CppMethod b : classes.get(i).methods){
				if(b.getName().equalsIgnoreCase("main")){
					main = i;
				}
			}
		}
	}
	
}
