package unit3.view;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import unit2.task1.cmd.views.HelpView;

@RunWith(MockitoJUnitRunner.class)
public class HelpViewTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @InjectMocks
    private HelpView view;

    @Test
    public void printHelpInfoTest() {
        view.printHelpInfo();
        String log = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertThat("addEmployee arg1 arg2, где arg1 - имя сотрудника,  arg2 - фамилия сотрудника: " +
                       "команда добавления нового сотрудника.\nemployees: команда отображения списка всех сотрудников"
                       + ".\nfireEmployee arg1, где arg1 - id сотрудника: команда увольнения сотрудника.\n" +
                       "addSupply arg1 arg2 arg3, где arg1 - id сотрудника, arg2 - название канцтовара, " +
                       "arg3 - цена канцтовара: команда добавления нового канцелярского товара.\nsupplies arg1, " +
                       "где arg1 - id сотрудника: команда для отображения всех канцелярских товаров у определенного "
                       + "сотрудника\ncalculate arg1, где arg1 - id сотрудника: команда для расчета стоимости всех "
                       + "концелярских "
                       + "товаров у определенного сотрудника.\nexit: команда выхода.")
            .isEqualTo(log.trim());
    }
}
