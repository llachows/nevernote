package com.example.nevernote.service;

import com.example.nevernote.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteNoteService {

  private final NoteRepository noteRepository;

  public void execute(UUID noteId) {
    noteRepository.deleteById(noteId);
  }
}
