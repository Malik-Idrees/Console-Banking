
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankCore ourNewBank;
        Scanner sc = new Scanner(System.in);
        boolean endTask = false;
        String inputString;
        //This file at the top display total accounts in our system Then a username and password separated by space
        String path = ("C:\\\\Users\\\\user\\\\Desktop\\\\list.txt");
        ourNewBank = new BankCore(path);
        ourNewBank.startUp();
        //System.exit(1);
    }
}