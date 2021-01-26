package unit1.task5;

/**
 * The {@code NoteNameValidator} class represents a validator for note's name.
 * The class {@code Notepad} includes methods to validate a note's name to null and uniqueness.
 *
 * @author Kristina Kirinyuk
 */
class NoteNameValidator {

    /**
     * Validate a note's name to null. If note's name is null, than throw an exception.
     *
     * @param name - note's name
     */
    public void validateNoteNameToNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Note's name can't be null.");
        }
    }

    /**
     * Validate a note's name to uniqueness. If note's name isn't unique, than throw an exception.
     *
     * @param noteName - note's name
     * @param notes    - array of notes
     */
    public void validateNoteNameToExist(String noteName, Note[] notes, int size) {
        if (notes == null || notes.length == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            if (noteName.equals(notes[i].getName())) {
                throw new NotepadPublicException(noteName);
            }
        }
    }
}
