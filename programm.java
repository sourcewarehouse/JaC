public class programm{

	public static void main(String[] args){
		   int i = 0;
		   int a = 0;
		   while(i < 4){
			   a = multiply(i , 2);
			   i++;
		   }  
		   System.out.println(a);
		}
	
	public int multiply (int a, int b){
		return a*b;
	}
}