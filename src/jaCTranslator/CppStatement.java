package jaCTranslator;

abstract class CppStatement {
	public abstract String toCpp(CppMethod cppMethod);
	
	public abstract void send(String fragment);
}

class AssertStatement extends CppStatement{

	int Expression = -1;
	
	@Override
	public String toString() {
		return "AssertStatement [Expression=" + Expression + "]";
	}

	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		cppMethod.standalone = false;
		result += "assert" + cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod) + ";\n";
		cppMethod.standalone = true;
		return result;
	}

	@Override
	public void send(String fragment) {
		Expression = Integer.parseInt(fragment);		
	}

}

class IfStatement extends CppStatement{

	int Condition = -1;
	int ifEnd = -1;
	int elseEnd = -1;
	
	@Override
	public String toString() {
		return "IfStatement [Condition=" + Condition + ", ifEnd=" + ifEnd
				+ ", elseEnd=" + elseEnd + "]";
	}


	@Override
	public void send(String fragment) {
		if(Condition == -1){
			Condition = Integer.parseInt(fragment);
		}
		else if(ifEnd == -1){
			ifEnd = Integer.parseInt(fragment);
		}
		else if(elseEnd == -1){
			elseEnd = Integer.parseInt(fragment);
		}
	}


	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		if(elseEnd == -1){
			cppMethod.standalone = false;
			result += "if(" + cppMethod.statements.get(Condition).toCpp(cppMethod) + "){\n";
			cppMethod.standalone = true;
			while(cppMethod.writeP <= ifEnd){
				result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
			}
			result += "}\n";
		}
		else{
			cppMethod.standalone = false;
			result += "if(" + cppMethod.statements.get(Condition).toCpp(cppMethod) + "){\n";
			cppMethod.standalone = true;
			while(cppMethod.writeP <= ifEnd){
				result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
			}
			result += "}\n";
			result += "else{\n";
			while(cppMethod.writeP <= elseEnd){
				result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
			}
			result += "}\n";
		}
		return result;
	}
}

class ForStatement extends CppStatement{
	
	int forInit = -1;
	int expression = -1;
	int forUpdate = -1;
	int forEnd = -1;
	
	
	@Override
	public String toString() {
		return "ForStatement [forInit=" + forInit + ", expression="
				+ expression + ", forUpdate=" + forUpdate + ", forEnd="
				+ forEnd + "]";
	}



	@Override
	public void send(String fragment) {
		if(forInit == -1){
			forInit = Integer.parseInt(fragment);
		}
		else if(expression == -1){
			expression = Integer.parseInt(fragment);
		}
		else if(forUpdate == -1){
			forUpdate = Integer.parseInt(fragment);
		} 
		else if(forEnd == -1){
			forEnd = Integer.parseInt(fragment);
		}
		
	}



	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		cppMethod.standalone = false;
		result += "for("+ cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod) + ";";
		result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod) + ";";
		result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod) + "){\n";
		cppMethod.standalone = true;
		while(cppMethod.writeP <= forEnd){
			result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
		}
		result += "}\n";
		return result;
	}
}

class WhileStatement extends CppStatement{
	
	int Condition = -1;
	int whileEnd = -1;
	
	@Override
	public String toString() {
		return "WhileStatement [Condition=" + Condition + ", whileEnd="
				+ whileEnd + "]";
	}


	@Override
	public void send(String fragment) {
		if(Condition == -1){
			Condition = Integer.parseInt(fragment);
		}
		else if(whileEnd == -1){
			whileEnd = Integer.parseInt(fragment);
		}
	}


	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		cppMethod.standalone = false;
		result += "while(" + cppMethod.statements.get(Condition).toCpp(cppMethod) + "){\n";
		cppMethod.standalone = true;
		while(cppMethod.writeP <= whileEnd){
			result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
		}
		result += "}\n";
		return result;
	}
}

class DoStatement extends CppStatement{
	
	int Condition = -1;
	int whileEnd = -1;
	
	@Override
	public String toString() {
		return "DoStatement [Condition=" + Condition + ", whileEnd=" + whileEnd
				+ "]";
	}


	@Override
	public void send(String fragment) {
		if(Condition == -1){
			Condition = Integer.parseInt(fragment);
		}
		else if(whileEnd == -1){
			whileEnd = Integer.parseInt(fragment);
		}		
	}


	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		String temp = "";
		result += "do{\n";
		cppMethod.standalone = false;
		temp = cppMethod.statements.get(Condition).toCpp(cppMethod);
		cppMethod.standalone = true;
		while(cppMethod.writeP <= whileEnd){
			result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
		}
		result += "}while(" + temp + ");";
		return result;
	}
}

class TryStatement extends CppStatement{


	@Override
	public void send(String fragment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toCpp(CppMethod cppMethod) {
		// TODO Auto-generated method stub
		return null;
	}
}

class SwitchStatement extends CppStatement{


	@Override
	public void send(String fragment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toCpp(CppMethod cppMethod) {
		// TODO Auto-generated method stub
		return null;
	}
}

class ReturnStatement extends CppStatement{
	
	int Expression = -1;
	
	@Override
	public String toString() {
		return "ReturnStatement [Expression=" + Expression + "]";
	}


	@Override
	public void send(String fragment) {
		if(Expression == -1){
			Expression = Integer.parseInt(fragment);
		}
		
	}


	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		if(Expression == -1){
			result += "return;\n";
		}
		else{
			cppMethod.standalone = false;
			result += "return " + cppMethod.statements.get(Expression).toCpp(cppMethod) + ";\n";
			cppMethod.standalone = true;
		}
		return result;
	}
}

class ThrowStatement extends CppStatement{
	
	int Expression = -1;
	

	@Override
	public void send(String fragment) {
		if(Expression == -1){
			Expression = Integer.parseInt(fragment);
		}
		
	}

	@Override
	public String toString() {
		return "ThrowStatement [Expression=" + Expression + "]";
	}

	@Override
	public String toCpp(CppMethod cppMethod) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}

class ExpressionStatement extends CppStatement{

	String expression = "";
	boolean standAlone = false;
	
	@Override
	public String toString() {
		return "ExpressionStatement [expression=" + expression + "]";
	}


	@Override
	public void send(String fragment) {
		expression = fragment;
	}


	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		analyze(expression);
		if(cppMethod.standalone){
			result += expression + ";\n";
		}
		else{
			result += expression;
		}
		
		return result;
	}


	private void analyze(String text) {
		if(text.contains("System.out.println")){
			String[] pieces = text.split("System.out.println");
			String temp = "";
			int counter = 1;
			for(int i = 1; i < pieces[1].length(); i++){
				if(pieces[1].charAt(i) == ')'){
					counter--;
					if(counter == 0){
						break;
					}
				}
				if(pieces[1].charAt(i) == '+'){
					temp += " << ";
					continue;
				}
				temp += pieces[1].charAt(i);
			}
			expression = "cout << " + temp + " << endl";
		}
		
	}
}

class LocalVariableDeclarationStatement extends CppStatement{

	String Type = "";
	String Name = "";
	int variableInitializer = -1;
	
	
	
	@Override
	public String toString() {
		return "LocalVariableDeclarationStatement [Type=" + Type + ", Name="
				+ Name + ", variableInitializer=" + variableInitializer + "]";
	}


	@Override
	public void send(String fragment) {
		if(Type.equals("")){
			if(fragment.equals("boolean")){
				Type = "bool";
			}
			else{
				Type = fragment;
			}
		}
		else if(Name.equals("")){
			Name = fragment;
		}
		else if(variableInitializer == -1){
			variableInitializer = Integer.parseInt(fragment);
		}
	}


	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		if(variableInitializer == -1){
			result += Type + " " + Name + ";\n";
		}
		else{
			if(cppMethod.standalone == false){
				String temp = cppMethod.statements.get(variableInitializer).toCpp(cppMethod);
				if(temp.contains(Type) && temp.contains("new")){
					result += Type + " " + Name + "(";
					String[] pieces = temp.split(Type);
					if(pieces.length == 2){
						for(int i = 1; i < pieces[1].length();i++){
							if(pieces[1].charAt(i) != ';'){
								result += pieces[1].charAt(i);
							}
						}
					}
					else{
						result += ">>> UNEXPECTED SYMBOLS <<<";
					}
					result += ";\n";
				}
				else{
					result += Type + " " + Name + " = " + temp;
				}
			}
			else{
				cppMethod.standalone = false;
				String temp = cppMethod.statements.get(variableInitializer).toCpp(cppMethod);
				if(temp.contains(Type) && temp.contains("new")){
					result += Type + " " + Name + "(";
					String[] pieces = temp.split(Type);
					if(pieces.length == 2){
						for(int i = 1; i < pieces[1].length();i++){
							if(pieces[1].charAt(i) != ';'){
								result += pieces[1].charAt(i);
							}
						}
					}
					else{
						result += ">>> UNEXPECTED SYMBOLS <<<";
					}
					result += ";\n";
				}
				else{
					result += Type + " " + Name + " = " + temp + ";\n";
				}
				cppMethod.standalone = true;
			}
		}
		return result;
	}
}

