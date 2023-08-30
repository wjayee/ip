package Utils;

import Exceptions.DukeException;
import Exceptions.InvalidInputException;
import Exceptions.MissingDateException;
import Exceptions.MissingTitleException;

/**
 * Makes sense of the user command
 */
public class Parser {

    /**
     * Returns the command that is called by the user.
     * @param input The user input.
     * @return The type of command called.
     */
    public static Commands determineCommand(String input) {
        for (Commands command: Commands.values()) {
            if (input.contains(command.name().toLowerCase())) {
                return command;
            }
        }
        return Commands.UNKNOWN;
    }

    /**
     * Returns the title of the task.
     * @param input The user input.
     * @param command Type of command given by the user.
     * @return The title of the task.
     * @throws DukeException Exceptions.InvalidInputException thrown if input cannot be recognised. Exceptions.MissingTitleException thrown
     * if user did not give a title for their task.
     */
    public static String obtainTitle(String input, Commands command) throws DukeException{
        try {
            if (command.equals(Commands.TODO)) {
                return input.split("todo ")[1];

            } else if (command.equals(Commands.DEADLINE)) {
                return input.split("deadline ")[1].split(" /by ")[0];

            } else if (command.equals(Commands.EVENT)) {
                return input.split("event ")[1].split(" /from ")[0];

            } else {
                throw new InvalidInputException("Invalid input");
            }

        } catch (ArrayIndexOutOfBoundsException oob) {
            throw new MissingTitleException("Missing Title");
        } catch (Exception e) {
            throw new InvalidInputException("Invalid input");
        }
    }

    /**
     * Returns the date specified by the user for their task.
     * @param input The user input.
     * @param command Type of command given by the user.
     * @return By date of deadlines or From and To date of events.
     * @throws DukeException Exceptions.InvalidInputException thrown if input cannot be recognised. Exceptions.MissingDateException thrown
     * if user did not give a by date for their deadline or either a from or to date for their event.
     */
    public static String obtainDate(String input, Commands command) throws DukeException{
        try {
            if (command.equals(Commands.DEADLINE)) {
                return input.split(" /by ")[1];

            } else if (command.equals(Commands.EVENT)) {
                String from = input.split(" /from ")[1].split(" /to ")[0];
                String to = input.split(" /from ")[1].split(" /to ")[1];
                return from + "/to" + to;

            } else {
                throw new InvalidInputException("Invalid Input");
            }
        } catch (ArrayIndexOutOfBoundsException oob) {
            throw new MissingDateException("Missing Date");
        } catch (Exception e) {
            throw new InvalidInputException("Invalid input");
        }
    }
}
