/**
 * Entry point for the PDD chatbot. Wires together the {@link Storage},
 * {@link TaskList} and {@link Ui} collaborators and runs the main
 * read-command / parse / execute loop.
 */
public class PDD {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public PDD(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (PDDException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new PDD("./data/pdd.txt").run();
    }
}
