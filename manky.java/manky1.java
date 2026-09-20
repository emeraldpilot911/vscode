import java.util.Scanner;

public class manky1 {
    public static void main(String[] args) {
        try (Scanner kybd = new Scanner(System.in)) {
            System.out.println("This is a program that predicts your future based on your input.");
            System.out.println("Do you want to continue? (yes/no)");
            if (!kybd.nextLine().equalsIgnoreCase("yes")) {
                System.out.println("Goodbye!");
                return;
            }
            int n = kybd.nextInt();
            System.out.println("You entered: " + n);
        }
    }
}
