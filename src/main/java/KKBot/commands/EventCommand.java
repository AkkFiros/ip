package KKBot.commands;

import KKBot.storage.Storage;
import KKBot.storage.exceptions.StorageException;
import KKBot.tasks.Event;
import KKBot.tasklist.TaskList;
import KKBot.ui.Ui;

/**
 * Class representing the command for KKBot.KKBot when user inputs an event task
 *
 * @author AkkFiros
 */

public class EventCommand extends Command {
    public static final String KEYWORD = "event";
    public static final String DATE_INPUT = " /at ";
    private String description;
    private String date;

    /**
     * Constructor for a EventCommand
     * @param description the description of the event task
     * @param date the date of the task
     */
    public EventCommand(String description, String date) {
        super();
        this.description = description;
        this.date = date;
    }

    /**
     * Returns a set of actions done by KKBot.KKBot when user inputs "event".
     * @param tasks the list of tasks stored by KKBot.KKBot
     * @param ui the ui object that governs what response is returned to the user
     * @param storage the storage object to save tasks to hard drive
     * @return the related messages after an event task is added to KKBot.KKBot
     * @throws StorageException if there is an error reading from/writing to hard drive
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws StorageException {
        Event task = new Event(description, date);
        tasks.addTask(task);
        storage.save(tasks);
        return ui.showTaskAddition(task)
                + ui.showNumberOfTasks(tasks.getNumberOfTasks());
    }
}
