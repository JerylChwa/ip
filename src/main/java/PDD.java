import java.util.ArrayList;
import java.util.List;
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

        List<String> tasks = new ArrayList<>();
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
            if (input.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else {
                tasks.add(input);
                System.out.println("added: " + input);
            }
            System.out.println(LINE);
        }
        scanner.close();
    }
}
