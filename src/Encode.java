
public class Encode {

	/*    public static void main(String[] args) {
	    	Integer a = encrypt("7856");
	    	Integer b = Decode.decrypt("1234");
	    	System.out.println(a);
	    	System.out.println(b);
	}*/
	    public String encrypt(String pin) {
			
			
			String digits = pin;
			String c1 =  digits.substring(0,1);
			String c2 =  digits.substring(1,2);
			String c3 =  digits.substring(2,3);
			String c4 =  digits.substring(3,4);
			//converting them into integers for arithmetic operation
			Integer d1,d2,d3,d4;
			d1 = Integer.valueOf(c1);
			d2 = Integer.valueOf(c2);
			d3 = Integer.valueOf(c3);
			d4 = Integer.valueOf(c4);
			//performing operations so that we can convert it into a secret pin
			d1 = (d1+6)%10;
			d2 = (d2+6)%10;
			d3 = (d3+6)%10;
			d4 = (d4+6)%10;
			//swapping values...places of numbers
			
			// Value of first and 3rd is assigned to temporary
	        Integer temporary = d1;
	        Integer temporary2 = d2;
	        
	        // Value of second is assigned to first and fourth to 2nd
	        d1 = d3;
	        d2 = d4;
	        // Value of temporary (which contains the initial value of first) is assigned to second
	        d3 = temporary;
	        d4 = temporary2;
	        //converting into string so that we can get a sigle encrypted pin again by concatenation different strings(numbers)
	        String s1 = String.valueOf(d1);
	        String s2 = String.valueOf(d2);
	        String s3 = String.valueOf(d3);
	        String s4 = String.valueOf(d4);
	        String encrypted_pin = s1+s2+s3+s4;
			//converting into integer
			//Integer encrypted_pin = Integer.parseInt(converToInt);
			//System.out.println("7856");
			//System.out.printf("encrypted pin To be:%d\n",encrypted_pin);
			return  encrypted_pin;
		}
}