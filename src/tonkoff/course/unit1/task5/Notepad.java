package unit1.task5;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code Notepad} class represents a array of notes.
 * The class {@code Notepad} includes fields array of notes and validator for note's name.
 * It also includes methods to manage a notes: add new note, change the already existing notes
 * and show data of all notes.
 *
 * @author Kristina Kirinyuk
 */
public class Notepad {

    /**
     * The field for call method to validate note's name
     */
    private final NoteNameValidator noteNameValidator = new NoteNameValidator();

    /**
     * The field to save all notes
     */
    private Note[] notes = new Note[]{};

    /**
     * Adding a new note to the end of the array of all notes.
     * Check the name of the new note for null and uniqueness. Copy array of notes to new array
     * with a length greater than 1 adding new note to ending of array. Set unique ID to new note and date of creation.
     * And add new note to array of notes.
     *
     * @param newNote - new note
     */
    public void addNote(Note newNote) {
        noteNameValidator.validateNoteNameToNull(newNote.getName());
        noteNameValidator.validateNoteNameToExist(newNote.getName(), notes);

        Note[] newNotes = copyNotesToNewArray();

        newNote.setId(UUID.randomUUID());
        newNote.setCreatedDate(LocalDateTime.now());
        newNotes[newNotes.length - 1] = newNote;

        notes = newNotes;
    }

    /**
     * Modified note in array of notes.
     * Check the name of the new note for null and uniqueness. Get index of note in the array.
     * Set date of modified and replace old note.
     *
     * @param newNote - old note with new changes
     */
    public void editNote(Note newNote) {
        noteNameValidator.validateNoteNameToNull(newNote.getName());
        int index = getNoteIndex(newNote.getId());

        newNote.setModifiedDate(LocalDateTime.now());
        notes[index] = newNote;
    }

    /**
     * Show all saves notes.
     * If array of notes are empty, then show the corresponding message.
     * * Example of note:
     * * <blockquote><pre>
     *      * Name of note. Created date: 2020-12-21T14:57:20.889453200
     *      * This is a test not
     *      * </pre></blockquote>
     */
    public void showAllNotes() {
        if (notes.length == 0) {
            System.out.println("В блокноте нет записей");
            return;
        }

        for (Note note : notes) {
            String modifiedDateString = note.getModifiedDate() == null ? ""
                    : "; modified date: " + note.getModifiedDate();
            System.out.println(note.getName() + ". Created date: " + note.getCreatedDate() + modifiedDateString);
            System.out.println(note.getContent());
            System.out.println();
        }
    }

    /**
     * Create new array with length greater than length of old array to 1. Copy all elements of old array to new array.
     *
     * @return new array with all elements from old array with 1 empty space at the ending of new array
     */
    private Note[] copyNotesToNewArray() {
        Note[] newNotes = new Note[notes.length + 1];
        System.arraycopy(notes, 0, newNotes, 0, notes.length);

        return newNotes;
    }

    /**
     * Return index of note in array of notes.
     * Check note ID to null. Throw exception if note with same id not found in array.
     *
     * @param id - ID of note
     * @return index in array
     */
    private int getNoteIndex(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Note id can't be null");
        }
        for (int i = 0; i < notes.length; i++) {
            if (notes[i].getId().equals(id)) {
                return i;
            }
        }
        throw new IllegalArgumentException(String.format("Note with id: %s doesn't exist.", id));
    }
}
