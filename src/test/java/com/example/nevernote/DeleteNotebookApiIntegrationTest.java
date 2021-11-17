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
class DeleteNotebookApiIntegrationTest extends BaseIntegrationTest {

  @Sql(
      statements =
          "INSERT INTO notebook VALUES ('674da7e7-77c5-4b97-b692-eb215b298165');")
  @Test
  public void test_delete_notebook() {
    // given
    var request = new HttpEntity<>(new HttpHeaders());
    var notebookId = "674da7e7-77c5-4b97-b692-eb215b298165";

    // when
    var response =
        restTemplate.exchange(
            serverBaseUrl() + notebookUrlWithId(notebookId),
            HttpMethod.DELETE,
            request,
            Void.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(findNotebook(notebookId)).isEmpty();
  }
}
