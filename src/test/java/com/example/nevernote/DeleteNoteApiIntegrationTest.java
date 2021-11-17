package com.example.nevernote;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteNoteApiIntegrationTest extends BaseIntegrationTest {

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
  public void test_delete_note() {
    // given
    var request = new HttpEntity<>(new HttpHeaders());
    var noteId = "30ada802-1a63-47f3-b3a4-c5058b1f5970";

    // when
    var response =
        restTemplate.exchange(
            serverBaseUrl() + noteUrlWithId(noteId), HttpMethod.DELETE, request, Void.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(findNote(noteId)).isEmpty();
  }
}
