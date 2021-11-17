package com.example.nevernote.service;

import com.example.nevernote.api.body.NotebookBody;
import com.example.nevernote.mapper.NotebookEntityToNotebookDtoMapper;
import com.example.nevernote.model.dto.NotebookDto;
import com.example.nevernote.model.entity.NoteEntity;
import com.example.nevernote.model.entity.NotebookEntity;
import com.example.nevernote.repository.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateNotebookService {

  private final NotebookRepository notebookRepository;
  private final NotebookEntityToNotebookDtoMapper notebookEntityToNotebookDtoMapper;

  public NotebookDto execute(NotebookBody notebookBody) {
    var entity = save(notebookBody);
    return notebookEntityToNotebookDtoMapper.map(entity);
  }

  @Transactional
  public NotebookEntity save(NotebookBody notebookBody) {
    var notebookEntity = new NotebookEntity();
    Optional.ofNullable(notebookBody.getNotes())
        .ifPresent(
            notes ->
                notes.stream()
                    .map(
                        noteBody ->
                            NoteEntity.builder()
                                .title(noteBody.getTitle())
                                .body(noteBody.getBody())
                                .tags(noteBody.getTags())
                                .build())
                    .forEach(notebookEntity::add));
    return notebookRepository.save(notebookEntity);
  }
}
