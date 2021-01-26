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
     * The field for array length without null elements
     */
    private int size = 0;

    /**
     * The field for array length
     */
    private int capacity;

    /**
     * The field to save all notes
     */
    private Note[] notes;

    public Notepad() {
        this(10);
    }

    public Notepad(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity can't be less than 1.");
        }

        this.capacity = capacity;
        this.notes = new Note[capacity];
    }

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
        noteNameValidator.validateNoteNameToExist(newNote.getName(), notes, size);

        newNote.setId(UUID.randomUUID());
        newNote.setCreatedDate(LocalDateTime.now());

        if (size == capacity) {
            updateCapacity();
        }

        notes[size] = newNote;
        size++;
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
        //newNote.setCreatedDate(notes[index].getCreatedDate());
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
        if (size == 0) {
            System.out.println("В блокноте нет записей");
            return;
        }

        for (int i = 0; i < size; i++) {
            System.out.println(notes[i].toString());
        }
    }

    /**
     * Create new array with length greater than length of old array in twice.
     * Copy all elements of old array to new array.
     */
    private void updateCapacity() {
        capacity = capacity * 2;

        Note[] oldNotes = notes;
        notes = new Note[capacity];

        System.arraycopy(oldNotes, 0, notes, 0, size);
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
        for (int i = 0; i < size; i++) {
            if (notes[i].getId().equals(id)) {
                return i;
            }
        }
        throw new IllegalArgumentException(String.format("Note with id: %s doesn't exist.", id));
    }
}
