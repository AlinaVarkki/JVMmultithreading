import java.util.Scanner;

public class ThreadNameSearch {

    public static void main(String[] args) {
        ThreadInfoPrinter threadInfoPrinter = new ThreadInfoPrinter();

        Scanner sc= new Scanner(System.in);
        System.out.print("Enter thread name to find: ");
        String thread = sc.nextLine();
        threadInfoPrinter.searchThreadByName(thread);
    }
}