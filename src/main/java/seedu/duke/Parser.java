package seedu.duke;

import seedu.duke.Exceptions.DukeException;

/**
 * Represents Parser object.
 */
public class Parser {

    /**
     * Enum for Commands for DukeBot.
     */
    public enum Commands {
        BYE, LIST, FIND, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT }

    /**
     * The Parser parses the commands based on the user input.
     *
     * @param taskList the TaskList where the tasks are stored.
     * @param storage  manages saving and loading the files from taskList.txt.
     * @param ui       manages the user interface and the output text from Duke.
     * @param s        the user input.
     *
     * @return String Duke's response.
     *
     * @throws ArrayIndexOutOfBoundsException If there is an empty input.
     * @throws DukeException       If there is an unknown command.
     */

    public String parse(TaskList taskList, Storage storage, Ui ui, String s) throws DukeException {
        String response = "";
        try {
            // Split the user input into an array with the command and the description
            String[] description = s.split(" ", 2);
            // Set command
            String command = description[0];
            Commands currCommand = Commands.valueOf(command.toUpperCase());

        switch (currCommand) {
            // Command for bye
            case BYE:
                response = ui.displayExit();
                break;
                // Command for list
            case LIST: {
                response = ui.displayTaskList(taskList);
                break;
            }
            // Command for find
            case FIND: {
                String keyword = description[1];
                response = ui.displayFindList(taskList.findTasks(keyword), keyword);
                break;
            }
            // Command to mark as done
            case MARK: {
                String input = description[1];
                response = TaskList.markTask(taskList, input);
                storage.write(taskList);
                break;
            }
            // Command to unmark
            case UNMARK: {
                String input = description[1];
                response = TaskList.unmarkTask(taskList, input);
                storage.write(taskList);
                break;
            }
            // Command to remove task
            case DELETE: {
                String input = description[1];
                response = TaskList.removeTask(taskList, input);
                storage.write(taskList);
                break;
            }
            // Create Duke.Todo task
            case TODO: {
                try {
                    String input = description[1];
                    response = Todo.runTodo(taskList, input);
                    storage.write(taskList);
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "Hey! The description of a todo cannot be empty!";
                }
                break;
            }
            // Create deadline task
            case DEADLINE: {
                try {
                    String input = description[1];
                    response = Deadline.runDeadline(taskList, input);
                    storage.write(taskList);
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "Hey! The description of a deadline cannot be empty!";
                }
                break;
            }
            // Create event task
            case EVENT: {
                try {
                    String input = description[1];
                    response = Event.runEvent(taskList, input);
                    storage.write(taskList);
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "Hey! The description of an event cannot be empty!";
                }
                break;
            }
            }
        } catch (IllegalArgumentException e) {
            return "I don't know what that means :(";
        }
        return response;
    }
}
