package unit5.task3.cmd.view;

import unit5.task3.application.CommitService;

public class ExitView {

    private final CommitService commitService;

    public ExitView(CommitService commitService) {
        this.commitService = commitService;
    }

    public void exit() {
        commitService.saveChanges();
        System.exit(0);
    }
}
