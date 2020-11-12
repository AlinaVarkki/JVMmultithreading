import java.util.Scanner;

public class ThreadGroupFilter {

    public static void main(String[] args) {
        ThreadInfoPrinter threadInfoPrinter = new ThreadInfoPrinter();

        Scanner sc= new Scanner(System.in);
        System.out.print("Enter thread group name to filter by: ");
        String threadGroup = sc.nextLine();
        threadInfoPrinter.filterByThreadGroup(threadGroup);

    }

}
