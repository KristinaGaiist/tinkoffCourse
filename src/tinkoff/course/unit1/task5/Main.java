package unit1.task5;

public class Main {

    public static void main(String... args) {
        Notepad notepad = new Notepad();

        Note note1 = new Note();
        note1.setName("note1");
        note1.setContent("12345^&*");
        notepad.addNote(note1);

        Note note2 = new Note();
        note2.setName("note2");
        note2.setContent("vnbnbn\njhjh");
        notepad.addNote(note2);

        Note note3 = new Note();
        note3.setName("note3");
        note3.setContent("dfjghasdfhgahszdjfvajkhsdvcakjzsdvckajzsdhvjkjSDHVkzsjdhvJSKDHVzkjshvjkhdvbz");
        notepad.addNote(note3);

        Note editNote = new Note();
        editNote.setId(note1.getId());
        editNote.setName("main NOTE");
        notepad.editNote(editNote);

        notepad.showAllNotes();
    }
}
