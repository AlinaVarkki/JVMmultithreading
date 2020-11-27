import java.util.Scanner;


public class ConsoleMain {
    public static void main(String[] args) {



        ThreadInfoPrinter threadInfoPrinter = new ThreadInfoPrinter();

        while(1==1){
                System.out.println("Enter 0 to see current thread groups and threads");
                System.out.println("Enter 1 to see current thread groups and threads infinitely refreshing");
                System.out.println("Enter 2 to search by thread name");
                System.out.println("Enter 3 to filter by thread group name");
                System.out.println("Enter 4 to start a new thread");

                Scanner sc= new Scanner(System.in);
                String chosenOption = sc.nextLine();

                switch (chosenOption){
                    case "0":
                        threadInfoPrinter.printInfo(0);
                        break;
                    case "1":
                        threadInfoPrinter.printInfoUpdating();
                        break;
                    case "2":
                        System.out.print("Enter thread name to find: ");
                        String thread = sc.nextLine();
                        threadInfoPrinter.searchThreadByName(thread);
                        break;
                    case "3":
                        System.out.print("Enter thread group name to filter by: ");
                        String threadGroup = sc.nextLine();
                        threadInfoPrinter.filterByThreadGroup(threadGroup);
                        break;
                    case "4":
                        threadInfoPrinter.startNewThread("TestThread");
                        break;
                    default:
                        System.out.println("Please enter an option 0-2");
                }

        }

    }



}