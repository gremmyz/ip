package seedu.duke;

import seedu.duke.Exceptions.DukeException;

/**
 * Represents Parser object.
 */
public class Parser {

    public enum Commands {
        BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT }

    public boolean isBye;

    /**
     * The Parser parses the commands based on the user input.
     *
     * @param taskList the TaskList where the tasks are stored
     * @param storage  manages saving and loading the files from taskList.txt
     * @param ui       manages the user interface and the output text from Duke
     * @param s        the user input
     */

    public void parser(TaskList taskList, Storage storage, Ui ui, String s) {
        try {
            String[] description = s.split(" ", 2);
            String command = description[0];
            Commands currCommand = Commands.valueOf(command.toUpperCase());

            switch (currCommand) {
                // Command for bye
                case BYE:
                    ui.displayExit();
                    this.isBye = true;
                    break;
                // Command for list
                case LIST: {
                    ui.displayTaskList(taskList);
                    break;
                }
                // Command to mark as done
                case MARK: {
                    String input = description[1];
                    TaskList.markTask(taskList, input);
                    storage.write(taskList);
                    break;
                }
                // Command to unmark
                case UNMARK: {
                    String input = description[1];
                    TaskList.unmarkTask(taskList, input);
                    storage.write(taskList);
                    break;
                }
                // Command to remove task
                case DELETE: {
                    String input = description[1];
                    TaskList.removeTask(taskList, input);
                    storage.write(taskList);
                    break;
                }
                // Create Duke.Todo task
                case TODO: {
                    try {
                        String input = description[1];
                        Todo.runTodo(taskList, input);
                        storage.write(taskList);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Hey! The description of a todo cannot be empty!");
                    }
                    break;
                }
                // Create deadline task
                case DEADLINE: {
                    try {
                        String input = description[1];
                        Deadline.runDeadline(taskList, input);
                        storage.write(taskList);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Hey! The description of a deadline cannot be empty!");
                    }
                    break;
                }
                // Create event task
                case EVENT: {
                    try {
                        String input = description[1];
                        Event.runEvent(taskList, input);
                        storage.write(taskList);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Hey! The description of an event cannot be empty!");
                    }
                }
                default: {
                    throw new DukeException("");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("I don't know what that means :<");
        } catch (DukeException e) {
            throw new RuntimeException(e);
        }
    }
}
