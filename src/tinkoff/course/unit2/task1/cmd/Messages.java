package unit2.task1.cmd;

public final class Messages {

	private Messages() {
	}

	public static final String INSERT_COMMAND = "Bведите команду: ";
	public static final String INFO_COMMAND = "Bведите команду help для подробной информации.";
	public static final String COMMAND_NOT_FOUND = "Команда %s не найдена";
	public static final String ILLEGAL_ARGUMENTS = "Неверные аргументы.";
	public static final String EMPLOYEE_NOT_FOUND = "Сотрудник с id = %d не найден.";
	public static final String COST_INFO = "Стоимост канцтоваров у сотрудника %d равна %.2f";
	public static final String ERROR_STRING_FOR_PARSE_TO_INT = "Аргумент %s не может быть конвентирован в число.";
	public static final String ERROR_STRING_FOR_PARSE_TO_DOUBLE =
			"Аргумент %s не может быть конвентирован в число с плавающей точкой.";
}
