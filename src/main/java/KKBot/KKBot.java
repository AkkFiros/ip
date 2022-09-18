package KKBot;

import KKBot.commands.Command;
import KKBot.exceptions.KKBotException;
import KKBot.parser.Parser;
import KKBot.storage.Storage;
import KKBot.storage.exceptions.StorageException;
import KKBot.tasklist.TaskList;
import KKBot.ui.Ui;

/**
 * KKBot is a chatbot that helps keep track of your to-do list!
 * It currently supports the following keywords:
 *     1) bye: exits the chatbot
 *     2) list: lists out all tasks stored
 *     3) mark x: marks the x-th item in your list as complete
 *     4) unmark x: marks the x-th item in your list as incomplete
 *     5) todo ijk: adds in a todo task ijk to the list
 *     6) deadline rst /by date-time: adds in a deadline task due by date-time
 *     7) event xyz /at date-time: adds in an event task occurring at date-time
 * If no keywords are used, KKBot returns an error message.
 *
 * @author AkkFiros
 */
public class KKBot {
    // create a class level array to store all user-input tasks
    private static List<Task> tasks = new ArrayList<>();
    final static String PATH = "./data";
    final static String FILENAME = "./data/kkbot.txt";
    private static String divider = "____________________________________________________________\n";

    /**
     * getTasks method to retrieve the list of tasks (for writing to file)
     * @return the list of tasks as a string
     */
    private static String getTasks() {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            result += tasks.get(i).toString() + "\n";
        }
        return result;
    }

    /**
     * writeFile method to write tasks to file
     * @param list the list of tasks to be saved to the hard disk
     */
    private static void writeFile(String list) {
        try {
            FileWriter fw = new FileWriter(FILENAME);
            fw.write(list);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * createFile method to create a new file to hard disk for saving
     */
    private static void createFile() {
        File directory = new File(PATH);
        if(!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(FILENAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void list() {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String completionIcon = task.getStatusIcon();
            String description = task.getDescription();
            String index = String.valueOf(i + 1);
            //if last task in list, add a divider after
            if (i == tasks.size() - 1) {
                System.out.println(index + ". " + task.toString() + "\n" + divider);
            } else {
                System.out.println(index + ". " + task.toString() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        // Welcome message and header
        String logo = " __   __   __   __  _____\n"
                    + "|  | /  / |  | /  /|  __  \\\n"
                    + "|  |/  /  |  |/  / | |__|  |\n"
                    + "|     /   |     /  |      /\n"
                    + "|     \\   |     \\  |  __  \\\n"
                    + "|  |\\  \\  |  |\\  \\ | |__|  |\n"
                    + "|__| \\__\\ |__| \\__\\|______/\n";
        String welcomeMessage = "Hello! I'm KKBot! \n"
                + "What can I do for you?\n";
        System.out.println(divider + logo + welcomeMessage + divider);



        // Add scanner in for user input (create scanner object)
        Scanner userText = new Scanner(System.in);

        // Chatbot function:
        while (true) {
            String input = userText.nextLine();
            System.out.println(divider);
            // if user input is bye, chatbot closes
            if (input.equals("bye") || input.equals("Bye") || input.equals("BYE")) {
                System.out.println("KKBot signing off. Goodbye!\n" + divider);
                break;
            // if user input is list, chatbot returns a list of stored tasks
            } else if (input.equals("list") || input.equals("List") || input.equals("LIST")) {
                // internal check if there are any tasks to do
                // if no stored tasks, inform accordingly
                if (tasks.isEmpty()) {
                    System.out.println("Good job! There's no tasks at hand!\n" + divider);
                    // if there are stored tasks, list them out
                    // include the index number and status icon of the task
                } else {
                    System.out.println("Here's your list of tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        String completionIcon = task.getStatusIcon();
                        String description = task.getDescription();
                        String index = String.valueOf(i + 1);
                        //if last task in list, add a divider after
                        if (i == tasks.size() - 1) {
                            System.out.println(index + ". " + task.toString() + "\n" + divider);
                        } else {
                            System.out.println(index + ". " + task.toString() + "\n");
                        }
                    }
                }
            // if user input is mark, chatbot retrieves the specified task and marks it as complete
            } else if (input.startsWith("mark") || input.startsWith("Mark") || input.startsWith("MARK")) {
                String inputNumber = input.substring(5);
                try {
                    int taskNumber = Integer.parseInt(inputNumber);
                    Task task = tasks.get(taskNumber - 1);
                    if (task != null) {
                        if (!task.getCompletion()) {
                            task.setComplete();
                            writeFile(getTasks());
                            System.out.println(divider);
                        } else {
                            System.out.println("Silly you, that task is already complete!\n" + divider);
                        }
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Woopsies you left out the task number. Please specify one!\n" + divider);
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("Hmm... that task doesn't exist. Please input another task number!\n" + divider);
                }
            // if user input is unmark, chatbot retrieves the specified task and marks it as incomplete
            } else if (input.startsWith("unmark") || input.startsWith("Unmark") || input.startsWith("UNMARK")) {
                String inputNumber = input.substring(7);
                try {
                    int taskNumber = Integer.parseInt(inputNumber);
                    Task task = tasks.get(taskNumber - 1);
                    if (task != null) {
                        if (task.getCompletion()) {
                            task.setIncomplete();
                            writeFile(getTasks());
                            System.out.println(divider);
                        } else {
                            System.out.println("Erm... that task was never complete...\n" + divider);
                        }
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Woopsies you left out the task number. Please specify one!\n" + divider);
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("Hmm... that task doesn't exist. Please input another task number!\n" + divider);
                }
            // if user input is todo, chatbot creates and adds in a new todo task to the list
            } else if (input.startsWith("todo") || input.startsWith("Todo") || input.startsWith("TODO")) {
                try {
                    String description = input.substring(5);
                    ToDo newToDo = new ToDo(description);
                    tasks.add(newToDo);
                    writeFile(getTasks());
                    System.out.println("I've added something to do to your list:\n" + "    " + newToDo.toString()
                            + "\n" + "Now you have " + tasks.size() + " tasks in the list!\n" + divider);
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("You forgot to specify a task! Try again!\n" + divider);
                }
            // if user input is deadline, chatbot creates and adds in a new deadline task to the list
            // it also sources out the deadline from the user input and jots it down in the list as well
            } else if (input.startsWith("deadline") || input.startsWith("Deadline") || input.startsWith("DEADLINE")) {
                try {
                    int separator = input.indexOf(" /by ");
                    String description = input.substring(9, separator).trim();
                    String by = input.substring(separator + 5);
                    Deadline newDeadline = new Deadline(description, by);
                    tasks.add(newDeadline);
                    writeFile(getTasks());
                    System.out.println("You've got a new deadline added to your list:\n" + "    "
                            + newDeadline.toString() + "\n" + "Now you have " + tasks.size()
                            + " tasks in the list!\n" + divider);
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("You forgot to specify a task/deadline! Try again!\n" + divider);
                }
            // if user input is event, chatbot creates and adds in a new event to the list
            // it also sources out the event time from the user input and jots it down in the list as well
            } else if (input.startsWith("event") || input.startsWith("Event") || input.startsWith("EVENT")) {
                try {
                    int separator = input.indexOf(" /at ");
                    String description = input.substring(6, separator).trim();
                    String at = input.substring(separator + 5);
                    Event newEvent = new Event(description, at);
                    tasks.add(newEvent);
                    writeFile(getTasks());
                    System.out.println("A new event is lined up and added to your list:\n"
                            + "    " + newEvent.toString() + "\n" + "Now you have "
                            + tasks.size() + " tasks in the list!\n" + divider);
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("You forgot to specify a task/deadline! Try again!\n" + divider);
                }
            } else if (input.startsWith("delete") || input.startsWith("Delete") || input.startsWith("DELETE")) {
                String inputNumber = input.substring(7);
                try {
                    int taskNumber = Integer.parseInt(inputNumber);
                    Task toRemove = tasks.get(taskNumber - 1);
                    tasks.remove(toRemove);
                    writeFile(getTasks());
                    System.out.println("This task has been deleted:\n" + "    " + toRemove.toString() + "\n"
                            + "Now you have " + tasks.size() + " tasks in the list!\n" + divider);
                } catch (NumberFormatException nfe) {
                    System.out.println("Woopsies you left out the task number. Please specify one!\n" + divider);
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("Hmm... that task doesn't exist. Please input another task number!\n" + divider);
                }
            // if user input has no keywords, then chatbot returns an error message
            } else {
                System.out.println("KKBot doesn't understood... please try again!" + "\n" + divider);
            }
        }
    }
}
