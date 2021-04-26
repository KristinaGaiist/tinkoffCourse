package unit2.task1.cmd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import unit2.task1.application.CalculateSuppliesCostService;
import unit2.task1.application.EmployeeService;
import unit2.task1.application.SupplyService;
import unit2.task1.cmd.views.CalculateSuppliesCostView;
import unit2.task1.cmd.views.EmployeeView;
import unit2.task1.cmd.views.ExitView;
import unit2.task1.cmd.views.HelpView;
import unit2.task1.cmd.views.SupplyView;
import unit2.task1.persistence.IDataStorage;
import unit2.task1.persistence.InMemoryDataStorage;

public class Main {

    public static void main(String... args) {
        IDataStorage dataStorage = new InMemoryDataStorage();
        ShellCommandExecutor commandExecutor =
            new ShellCommandExecutor(new CalculateSuppliesCostView(new CalculateSuppliesCostService(dataStorage)),
                                     new EmployeeView(new EmployeeService(dataStorage)), new ExitView(),
                                     new HelpView(), new SupplyView(new SupplyService(dataStorage)));
        System.out.println(Messages.INFO_COMMAND);

        try (InputStream inputStream = System.in;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (true) {
                System.out.println(Messages.INSERT_COMMAND);
                String command = bufferedReader.readLine();
                String[] arguments = command.split(" ");

                commandExecutor.execute(arguments);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Произошла непредвиденная ошибка");
        }
    }
}
