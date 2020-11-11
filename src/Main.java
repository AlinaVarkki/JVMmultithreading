public class Main {
    public static void main(String[] args) {
        ThreadInfoPrinter threadInfoPrinter = new ThreadInfoPrinter();
        System.out.println("Hierarchy Format:\n");
        threadInfoPrinter.printInfo(0);
        System.out.println();
        System.out.println("Listed Format:\n");
        threadInfoPrinter.printInfo(1);
    }
}
