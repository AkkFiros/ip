/**
 * Command class to encapsulate user inputs.
 *
 * @author AkkFiros
 */

public abstract class Command {
    /**
     * Method to execute an action after receiving a user input.
     * @param tasks the list of tasks stored by KKBot
     * @param display the display object that governs what response is returned to the user
     * @param storage the storage object to save tasks to hard drive
     * @return KKBot's response to the user input
     * @throws InvalidTaskException if user input is wrong
     * @throws StorageException if there is an error reading from/writing to hard drive
     */
    public abstract String execute(TaskList tasks, Display display, Storage storage)
            throws InvalidTaskException, StorageException;
}
