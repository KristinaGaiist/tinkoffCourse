package unit5.task1AndTask2;

public final class ExceptionMessage {

    public static final String ERROR = "Непредвиденная ошибка";
    public static final String FILE_NOT_FOUND = "Входной файл не найден";
    public static final String ERROR_WITH_READ_FILE = "Проблемы при считывании файла";
    public static final String ERROR_WITH_WRITE_FILE = "Проблемы записи в файл";
    public static final String OBJECT_CAN_NOT_BE_SERIALIZE = "Невозможно серилизовать объект";
    public static final String CAST_EXCEPTION = "Невозможно десерилизовать объект в коллекцию фильмов";
    private ExceptionMessage() {
    }
}
