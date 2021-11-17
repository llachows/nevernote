package com.example.nevernote.service;

import com.example.nevernote.api.body.NoteBody;
import com.example.nevernote.mapper.NoteEntityToNoteDtoMapper;
import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.model.entity.NoteEntity;
import com.example.nevernote.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateNoteService {

  private final NoteRepository noteRepository;
  private final NoteEntityToNoteDtoMapper noteEntityToNoteDtoMapper;

  public NoteDto execute(UUID noteId, NoteBody noteBody) {
    var entity = findAndUpdate(noteId, noteBody);
    return noteEntityToNoteDtoMapper.map(entity);
  }

  @Transactional
  public NoteEntity findAndUpdate(UUID noteId, NoteBody noteBody) {
    var entity =
        noteRepository
            .findById(noteId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Notebook not found: %s", noteId)));

    entity.setTitle(noteBody.getTitle());
    entity.setBody(noteBody.getBody());
    entity.setTags(noteBody.getTags());

    return noteRepository.save(entity);
  }
}
