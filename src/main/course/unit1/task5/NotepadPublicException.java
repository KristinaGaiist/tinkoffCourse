package unit1.task5;

/**
 * {@code NotepadPublicException} thrown to indicate that a method has been passed a name already exist.
 *
 * @author Kristina Kirinyuk
 */
public class NotepadPublicException extends RuntimeException {

    /**
     * Constructs an {@code NotepadPublicException} with the
     * specified detail message.
     *
     * @param name the detail message.
     */
    public NotepadPublicException(String name) {
        super(String.format("Note with name: %s already exist.", name));
    }
}
