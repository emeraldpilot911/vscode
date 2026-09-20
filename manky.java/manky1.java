import java.util.Scanner;

public class manky1 {
    public static void main(String[] args) {
        try (Scanner kybd = new Scanner(System.in)) {
            System.out.println("Enter a number:");
            int n = kybd.nextInt();
            System.out.println("You entered: " + n);
        }
    }
}
