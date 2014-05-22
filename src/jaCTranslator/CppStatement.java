package jaCTranslator;

abstract class CppStatement {
	public abstract String toCpp();
	
	public abstract void send(String fragment);
}

class AssertStatement extends CppStatement{

	int Expression = -1;
	
	@Override
	public String toString() {
		return "AssertStatement [Expression=" + Expression + "]";
	}

	@Override
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
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
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
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
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
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
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
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
}

class DoStatement extends CppStatement{
	
	String Condition = "";
	int whileEnd = -1;
	
	@Override
	public String toString() {
		return "DoStatement [Condition=" + Condition + ", whileEnd=" + whileEnd
				+ "]";
	}

	@Override
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String fragment) {
		if(Condition.equals("")){
			Condition = fragment;
		}
		else if(whileEnd == -1){
			whileEnd = Integer.parseInt(fragment);
		}		
	}
}

class TryStatement extends CppStatement{

	@Override
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String fragment) {
		// TODO Auto-generated method stub
		
	}
}

class SwitchStatement extends CppStatement{

	@Override
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String fragment) {
		// TODO Auto-generated method stub
		
	}
}

class ReturnStatement extends CppStatement{
	
	int Expression = -1;
	
	@Override
	public String toString() {
		return "ReturnStatement [Expression=" + Expression + "]";
	}

	@Override
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String fragment) {
		if(Expression == -1){
			Expression = Integer.parseInt(fragment);
		}
		
	}
}

class ThrowStatement extends CppStatement{

	@Override
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String fragment) {
		// TODO Auto-generated method stub
		
	}
}

class ExpressionStatement extends CppStatement{

	String expression = "";
	
	@Override
	public String toString() {
		return "ExpressionStatement [expression=" + expression + "]";
	}

	@Override
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String fragment) {
		expression = fragment;
		
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
	public String toCpp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String fragment) {
		if(Type.equals("")){
			Type = fragment;
		}
		else if(Name.equals("")){
			Name = fragment;
		}
		else if(variableInitializer == -1){
			variableInitializer = Integer.parseInt(fragment);
		}
	}
}

