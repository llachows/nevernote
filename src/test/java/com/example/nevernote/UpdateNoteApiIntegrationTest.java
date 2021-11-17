package com.example.nevernote;

import com.example.nevernote.api.body.NoteBody;
import com.example.nevernote.model.dto.NoteDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateNoteApiIntegrationTest extends BaseIntegrationTest {

  @Sql(
      statements = {
        "INSERT INTO notebook VALUES ('674da7e7-77c5-4b97-b692-eb215b298165');",
        "INSERT INTO note "
            + "VALUES ('30ada802-1a63-47f3-b3a4-c5058b1f5970', "
            + "        'test note 1', "
            + "        'example body 1', "
            + "        'TAG_1;TAG_2', "
            + "        '2021-11-16T18:00:00.000Z', "
            + "        '2021-11-16T18:00:00.000Z', "
            + "        '674da7e7-77c5-4b97-b692-eb215b298165');"
      })
  @Test
  public void test_update_note() {
    // given
    var body =
        NoteBody.builder()
            .title("new title")
            .body("new body")
            .tags(List.of("TAG_NEW"))
            .build();
    var request = new HttpEntity<>(body, new HttpHeaders());
    var noteId = "30ada802-1a63-47f3-b3a4-c5058b1f5970";

    // when
    var response =
        restTemplate.exchange(
            serverBaseUrl() + noteUrlWithId(noteId), HttpMethod.PUT, request, NoteDto.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var dto = response.getBody();
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isNotNull();
    assertThat(dto.getTitle()).isEqualTo("new title");
    assertThat(dto.getBody()).isEqualTo("new body");
    assertThat(dto.getTags()).contains("TAG_NEW");
    assertThat(dto.getCreated()).isEqualTo(Instant.parse("2021-11-16T18:00:00.000Z"));
    assertThat(dto.getLastModified())
        .isNotNull()
        .isNotEqualTo(Instant.parse("2021-11-16T18:00:00.000Z"));
  }
}
