package com.example.nevernote.mapper;

import com.example.nevernote.model.entity.NoteEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NoteEntityToNoteDtoMapperTest {

  private NoteEntityToNoteDtoMapper mapper = new NoteEntityToNoteDtoMapper();

  @Test
  public void test_map() {
    // given
    var entity = new NoteEntity();
    entity.setId(UUID.fromString("8eee55b2-2a31-4a6e-8a51-e81cc17d9194"));
    entity.setTitle("title");
    entity.setBody("body");
    entity.setTags(List.of("tag1", "tag2"));
    Instant created = Instant.now();
    entity.setCreatedDate(created);
    Instant modified = Instant.now().plus(5, ChronoUnit.MINUTES);
    entity.setModifiedDate(modified);

    // when
    var dto = mapper.map(entity);

    // then
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo("8eee55b2-2a31-4a6e-8a51-e81cc17d9194");
    assertThat(dto.getTitle()).isEqualTo("title");
    assertThat(dto.getBody()).isEqualTo("body");
    assertThat(dto.getTags()).contains("tag1", "tag2");
    assertThat(dto.getCreated()).isEqualTo(created);
    assertThat(dto.getLastModified()).isEqualTo(modified);
  }
}
