import java.util.Scanner;

public class ArmstrongNum {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a number : ");
        int num = scan.nextInt();

        int tmp = num, armNum = 0;

        while (tmp > 0) {
            int digit = tmp % 10;
            armNum = armNum + (digit * digit * digit);
            tmp = tmp / 10;
        }

        if (num == armNum) {
            System.out.println("Entered number is Armstrong number.");
        } else {
            System.out.println("Entered number is Not an Armstrong number");
        }

        scan.close();
    }
}
