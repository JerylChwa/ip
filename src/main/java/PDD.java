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
        List<Boolean> done = new ArrayList<>();
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
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + statusIcon(done.get(i)) + " " + tasks.get(i));
                }
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                done.set(index, true);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + statusIcon(done.get(index)) + " " + tasks.get(index));
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                done.set(index, false);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + statusIcon(done.get(index)) + " " + tasks.get(index));
            } else {
                tasks.add(input);
                done.add(false);
                System.out.println("added: " + input);
            }
            System.out.println(LINE);
        }
        scanner.close();
    }

    private static String statusIcon(boolean isDone) {
        return isDone ? "[X]" : "[ ]";
    }
}
