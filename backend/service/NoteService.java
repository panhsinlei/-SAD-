package backend.service;

import backend.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteService {
    private final List<Note> notes = new ArrayList<>();

    public List<Note> getAllNotes() {
        return notes;
    }

    public void addNote(Note note) {
        notes.add(note);
    }
}
