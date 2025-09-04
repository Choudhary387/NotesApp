package com.Notes.Notes;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*") // allow frontend access from Vercel later
public class NoteController {

    private final NoteRepository repository;

    public NoteController(NoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return repository.save(note);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestBody Note note) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(note.getTitle());
            existing.setContent(note.getContent());
            existing.setPublic(note.isPublic());
            return repository.save(existing);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/{id}/share")
    public Note shareNote(@PathVariable Long id) {
        return repository.findById(id).map(note -> {
            String shareId = UUID.randomUUID().toString();
            note.setShareId(shareId);
            return repository.save(note);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    @GetMapping("/share/{shareId}")
    public Note getSharedNote(@PathVariable String shareId) {
        return repository.findByShareId(shareId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shared note not found"));
    }



}
