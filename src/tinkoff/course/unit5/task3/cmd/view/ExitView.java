package unit5.task3.cmd.view;

import unit5.task3.application.ExitService;

public class ExitView {

    private final ExitService exitService;

    public ExitView(ExitService exitService) {
        this.exitService = exitService;
    }

    public void exit() {
        exitService.exit();
        System.exit(0);
    }
}
