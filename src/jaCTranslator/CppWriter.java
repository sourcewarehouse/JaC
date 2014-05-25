package jaCTranslator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CppWriter {
	public static int main = -1;
	public static ArrayList<String> files = new ArrayList<String>();
	
	public static void convert(){
		main = CppProgram.findMain();
		writeClasses();
		writeMain();
	}
	
	
	private static void writeClasses() {
		for(int i = 0; i < CppProgram.classes.size();i++){
			if(i != main){
				try {
					FileWriter out = new FileWriter(new File(CppProgram.classes.get(i).name + ".h"));
					String result = "";
					result += "class " + CppProgram.classes.get(i).name + "{\n";
					result += CppProgram.classes.get(i).CppHeader();
					result += "};";
					out.write(result);
					out.close();
					files.add(CppProgram.classes.get(i).name + ".h");
					FileWriter out2 = new FileWriter(new File(CppProgram.classes.get(i).name + ".cpp"));
					result = "";
					result += "#include \"" + CppProgram.classes.get(i).name + ".h\"\n";
					for(int j = 0; j < CppProgram.classes.get(i).constructors.size();j++){
						CppMethod cur = CppProgram.classes.get(i).constructors.get(j);
						result += CppProgram.classes.get(i).name + "::" + cur.getName() + "(";
						for (int k = 0; k < cur.parameters.size(); k++){
							result += cur.parameters.get(k).toCpp();
							if(k + 1 < cur.parameters.size()){
								result += ",";
							}
						}
						result += "){\n";
						result += cur.toCpp();
						result += "}\n";
						
					}
					for(int j = 0; j < CppProgram.classes.get(i).methods.size();j++){
						CppMethod cur = CppProgram.classes.get(i).methods.get(j);
						result += cur.getReturnType() + " " + CppProgram.classes.get(i).name + "::" + cur.getName() + "(";
						for (int k = 0; k < cur.parameters.size(); k++){
							result += cur.parameters.get(k).toCpp();
							if(k + 1 < cur.parameters.size()){
								result += ",";
							}
						}
						result += "){\n";
						result += cur.toCpp();
						result += "}\n";
					}
					out2.write(result);
					out2.close();
				} catch (IOException e) {
					System.out.println("This is not good");
					e.printStackTrace();
				}
			}
		}
	}


	public static void writeMain(){
		int main2 = CppProgram.findMain2();
		
		try {
			FileWriter out = new FileWriter(new File("main.cpp"));
			String result = "";
			CppClass mainClass = CppProgram.classes.get(main);
			out.write("#include <iostream>\n");
			for(int i = 0; i < files.size();i++){
				out.write("#include \"" + files.get(i) + "\"\n");	
			}
			out.write("using namespace std;\n\n");
			for(CppField a : mainClass.fields){
				result += a.type + " " + a.Declaration + ";";
			}
			out.write("\n\n");
			for(int i = 0; i < mainClass.methods.size();i++){
				if(i != main2){
					result += mainClass.methods.get(i).getReturnType() + " " + mainClass.methods.get(i).getName() + "(";
					for (int j = 0; j < mainClass.methods.get(i).parameters.size(); j++){
						result += mainClass.methods.get(i).parameters.get(j).toCpp();
						if(j + 1 < mainClass.methods.get(i).parameters.size()){
							result += ",";
						}
					}
					result += "){\n";
					result += mainClass.methods.get(i).toCpp();
				}
			}
			result += "int main(int argc, char *argv[]){\n";
			result += mainClass.methods.get(main2).toCpp();
			result += "}";
			out.write(result);
			out.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

