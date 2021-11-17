package com.example.nevernote;

import com.example.nevernote.model.dto.NoteDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RetrieveNoteApiIntegrationTest extends BaseIntegrationTest {

  @Sql(
      statements = {
        "INSERT INTO notebook VALUES ('674da7e7-77c5-4b97-b692-eb215b298165');",
        "INSERT INTO note "
            + "VALUES ('30ada802-1a63-47f3-b3a4-c5058b1f5970', "
            + "        'test note 1', "
            + "        'example body 1', "
            + "        'TAG_1;TAG_2', "
            + "        '2021-11-16 18:00:00.000Z', "
            + "        '2021-11-16 18:00:00.000Z', "
            + "        '674da7e7-77c5-4b97-b692-eb215b298165');"
      })
  @Test
  public void test_retrive_note() {
    // given
    var noteId = "30ada802-1a63-47f3-b3a4-c5058b1f5970";

    // when
    var response =
        restTemplate.getForEntity(serverBaseUrl() + noteUrlWithId(noteId), NoteDto.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var dto = response.getBody();
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isNotNull();
    assertThat(dto.getTitle()).isEqualTo("test note 1");
    assertThat(dto.getBody()).isEqualTo("example body 1");
    assertThat(dto.getTags()).contains("TAG_1", "TAG_2");
    assertThat(dto.getCreated()).isEqualTo(Instant.parse("2021-11-16T18:00:00.000Z"));
    assertThat(dto.getLastModified()).isEqualTo(Instant.parse("2021-11-16T18:00:00.000Z"));
  }
}
