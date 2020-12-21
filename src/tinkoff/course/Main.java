import unit1.Task1;
import unit1.Task2;
import unit1.Task3;
import unit1.Task4;
import unit1.task5.Note;
import unit1.task5.Notepad;

public class Main {

    public static void main(String... args) {
        Task1 task1 = new Task1();
        task1.printArray(task1.arrayOfEvenNumbersCase1());
        task1.printArray(task1.arrayOfEvenNumbersCase2());
        task1.printArray(task1.arrayOfEvenNumbersCase3());

        Task2 task2 = new Task2();
        task2.printFunctionResult(-4.5, 2.4, 0.5);

        Task3 task3 = new Task3();
        int[][] array = task3.fullArrayByRandomNumbers();
        task3.printArray(array);
        task3.printArray(task3.fullArrayByMaxAndMinNumbers(array));

        Task4 task4 = new Task4();
        task4.printEquationResult(4, 9, 170);

        Notepad notepad = new Notepad();

        Note note1 = new Note();
        note1.setName("note1");
        //  note1.setName(null);
        note1.setContent("12345^&*");
        notepad.addNote(note1);

        Note note2 = new Note();
        note2.setName("note2");
        //  note2.setName("note1");
        note2.setContent("vnbnbn\njhjh");
        notepad.addNote(note2);

        Note note3 = new Note();
        note3.setName("note3");
        note3.setContent("dfjghasdfhgahszdjfvajkhsdvcakjzsdvckajzsdhvjkjSDHVkzsjdhvJSKDHVzkjshvjkhdvbz");
        notepad.addNote(note3);

        Note editNote = new Note();
        editNote.setId(note1.getId());
        editNote.setName("main NOTE");
      //  editNote.setId(UUID.randomUUID());
        notepad.editNote(editNote);

        notepad.showAllNotes();
    }
}
