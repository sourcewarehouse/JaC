package jaCTranslator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CppWriter {
	
	public void convert(){
		writeMain();
	}
	
	
	public void writeMain(){
		int main = CppProgram.findMain();
		int main2 = CppProgram.findMain2();
		
		try {
			FileWriter out = new FileWriter(new File("main.cpp"));
			CppClass mainClass = CppProgram.classes.get(main);
			out.write("using namespace std;\n\n");
			for(CppField a : mainClass.fields){
				out.write(a.type + " " + a.Declaration + ";\n");
			}
			out.write("\n\n");
			for(int i = 0; i < mainClass.methods.size();i++){
				if(i != main2){
					out.write(mainClass.methods.get(i).toString());
					out.write("\n");
				}
			}
			out.write(mainClass.methods.get(main2).toString());
			out.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

