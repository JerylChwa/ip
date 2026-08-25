import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Makes sense of raw user input: splitting a command line into its
 * command word and arguments, and turning the arguments for each
 * command type into validated data (a parsed date, a new Task, etc),
 * throwing {@link PDDException} with a user-facing message when the
 * input doesn't fit what that command expects.
 */
public class Parser {
    /** The kind of command a user input line names. */
    public enum CommandType { LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, ON }

    /** Returns the command word (before the first space), e.g. "deadline" from "deadline return book /by ...". */
    public static String getCommandWord(String fullCommand) {
        int spaceIndex = fullCommand.indexOf(' ');
        return spaceIndex == -1 ? fullCommand : fullCommand.substring(0, spaceIndex);
    }

    /** Returns the trimmed text after the command word, or "" if there is none. */
    public static String getCommandArgs(String fullCommand) {
        int spaceIndex = fullCommand.indexOf(' ');
        return spaceIndex == -1 ? "" : fullCommand.substring(spaceIndex + 1).trim();
    }

    /** Resolves a command word to a {@link CommandType}, throwing if it isn't recognized. */
    public static CommandType parseCommandType(String commandWord) throws PDDException {
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new PDDException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /** Parses the arguments of a {@code todo} command into a {@link Todo}. */
    public static Todo parseTodo(String commandArgs) throws PDDException {
        if (commandArgs.isEmpty()) {
            throw new PDDException("OOPS!!! The description of a todo cannot be empty.");
        }
        return new Todo(commandArgs);
    }

    /** Parses the arguments of a {@code deadline} command into a {@link Deadline}. */
    public static Deadline parseDeadline(String commandArgs) throws PDDException {
        if (commandArgs.isEmpty()) {
            throw new PDDException("OOPS!!! The description of a deadline cannot be empty.");
        }
        String[] parts = commandArgs.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new PDDException("OOPS!!! A deadline needs a description and a '/by' "
                    + "date, e.g. deadline return book /by 2019-10-15");
        }
        LocalDate by = parseDate(parts[1].trim());
        return new Deadline(parts[0].trim(), by);
    }

    /** Parses the arguments of an {@code event} command into an {@link Event}. */
    public static Event parseEvent(String commandArgs) throws PDDException {
        if (commandArgs.isEmpty()) {
            throw new PDDException("OOPS!!! The description of an event cannot be empty.");
        }
        String[] parts = commandArgs.split(" /from ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new PDDException("OOPS!!! An event needs a description, a '/from' date and a "
                    + "'/to' time, e.g. event meeting /from 2019-10-15 /to 4pm");
        }
        String[] fromTo = parts[1].split(" /to ", 2);
        if (fromTo.length < 2 || fromTo[0].trim().isEmpty() || fromTo[1].trim().isEmpty()) {
            throw new PDDException("OOPS!!! An event needs a description, a '/from' date and a "
                    + "'/to' time, e.g. event meeting /from 2019-10-15 /to 4pm");
        }
        LocalDate from = parseDate(fromTo[0].trim());
        return new Event(parts[0].trim(), from, fromTo[1].trim());
    }

    /** Parses the arguments of an {@code on} command into the date to filter tasks by. */
    public static LocalDate parseOnDate(String commandArgs) throws PDDException {
        if (commandArgs.isEmpty()) {
            throw new PDDException("OOPS!!! Please provide a date, e.g. on 2019-10-15");
        }
        return parseDate(commandArgs);
    }

    private static LocalDate parseDate(String text) throws PDDException {
        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            throw new PDDException("OOPS!!! Please enter the date in yyyy-MM-dd format, e.g. 2019-10-15.");
        }
    }
}
