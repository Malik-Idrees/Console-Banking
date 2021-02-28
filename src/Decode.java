
public class Decode {
	public String decrypt(String pin) {
		
		//assigning each single integer to a variable 
		String digits = pin;
		String c1 =  digits.substring(0,1);
		String c2 =  digits.substring(1,2);
		String c3 =  digits.substring(2,3);
		String c4 =  digits.substring(3,4);
		
		//swapping the values of variables...shifting the integers place
		// Value of first and 3rd is assigned to temporary
        String temporary = c1;
        String temporary2 = c2;
        // Value of 3rd is assigned to first and fourth to 2nd
        c1 = c3;
        c2 = c4;
        // Value of temporary (which contains the initial value of first) is assigned to second
        c3 = temporary;
        c4 = temporary2;
      //conversion into integers for applying arithmetic operations
        Integer d1,d2,d3,d4;
        d1 = Integer.valueOf(c1);
		d2 = Integer.valueOf(c2);
		d3 = Integer.valueOf(c3);
		d4 = Integer.valueOf(c4);
        //applying arithmetic operations
		d1 = (d1+4)%10;
		d2 = (d2+4)%10;
		d3 = (d3+4)%10;
		d4 = (d4+4)%10;
        //converting into string so that we can get a single encrypted pin again by concatenation different strings(numbers)
        String s1 = String.valueOf(d1);
        String s2 = String.valueOf(d2);
        String s3 = String.valueOf(d3);
        String s4 = String.valueOf(d4);
        String decrypted_pin = s1+s2+s3+s4;
		//converting into integer
		//Integer decrypted_pin = Integer.parseInt(decrypted_pin);
		//System.out.println(pin);
		//System.out.printf("decrypted pin To be%d\n:",decrypted_pin);
		return decrypted_pin;
	}
}
