import java.util.Scanner;

public class PDD {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        String banner = " ____  ____  ____  \n"
                + "|  _ \\|  _ \\|  _ \\ \n"
                + "| |_) | | | | | | |\n"
                + "|  __/| |_| | |_| |\n"
                + "|_|   |____/|____/ \n";

        System.out.println(LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm PDD.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }
            System.out.println(LINE);
            System.out.println(input);
            System.out.println(LINE);
        }
        scanner.close();
    }
}
