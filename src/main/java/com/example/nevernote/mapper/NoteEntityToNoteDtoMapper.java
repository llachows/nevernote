package com.example.nevernote.mapper;

import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.model.entity.NoteEntity;
import org.springframework.stereotype.Component;

@Component
public class NoteEntityToNoteDtoMapper {

  public NoteDto map(NoteEntity entity) {
    return NoteDto.builder()
        .id(entity.getId().toString())
        .title(entity.getTitle())
        .body(entity.getBody())
        .tags(entity.getTags())
        .created(entity.getCreatedDate())
        .lastModified(entity.getModifiedDate())
        .build();
  }
}
