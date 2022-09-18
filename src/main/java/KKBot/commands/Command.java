package KKBot.commands;

import KKBot.storage.Storage;
import KKBot.storage.exceptions.StorageException;
import KKBot.tasklist.TaskList;
import KKBot.tasklist.exceptions.InvalidTaskException;
import KKBot.ui.Ui;

/**
 * Command class to encapsulate user inputs.
 *
 * @author AkkFiros
 */

public abstract class Command {
    /**
     * Method to execute an action after receiving a user input.
     * @param tasks the list of tasks stored by KKBot.KKBot
     * @param ui the ui object that governs what response is returned to the user
     * @param storage the storage object to save tasks to hard drive
     * @return KKBot.KKBot's response to the user input
     * @throws InvalidTaskException if user input is wrong
     * @throws StorageException if there is an error reading from/writing to hard drive
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage)
            throws InvalidTaskException, StorageException;
}