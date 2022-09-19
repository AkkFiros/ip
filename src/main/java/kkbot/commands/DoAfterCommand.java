package kkbot.commands;

import kkbot.storage.Storage;
import kkbot.storage.exceptions.StorageException;
import kkbot.tasks.DoAfter;
import kkbot.tasklist.TaskList;
import kkbot.ui.Ui;

/**
 * Class representing the command for KKBot when user inputs a doafter task
 *
 * @author AkkFiros
 */

public class DoAfterCommand extends Command {
    public static final String KEYWORD = "doafter";
    public static final String DATE_INPUT = " /after ";
    private String description;
    private String date;

    /**
     * Constructor for a DoAfterCommand
     * @param description the description of the doafter task
     * @param date the start date of the task
     */
    public DoAfterCommand(String description, String date) {
        super();
        this.description = description;
        this.date = date;
    }

    /**
     * Returns a set of actions done by KKBot when user inputs "after".
     * @param tasks the list of tasks stored by KKBot
     * @param ui the ui object that governs what response is returned to the user
     * @param storage the storage object to save tasks to hard drive
     * @return the related messages after a doafter task is added to KKBot
     * @throws StorageException if there is an error reading from/writing to hard drive
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws StorageException {
        DoAfter task = new DoAfter(description, date);
        tasks.addTask(task);
        storage.save(tasks);
        return ui.showTaskAddition(task)
                + ui.showNumberOfTasks(tasks.getNumberOfTasks());
    }
}