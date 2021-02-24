package unit3.view;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import unit2.task1.cmd.views.ExitView;

@RunWith(MockitoJUnitRunner.class)
public class ExitViewTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @InjectMocks
    private ExitView view;

    @Test
    public void exitTest() {
        exit.expectSystemExitWithStatus(0);
        view.exit();
    }
}
