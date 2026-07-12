package hei.school.libraries.client;

import com.fasterxml.jackson.databind.JsonNode;
import hei.school.libraries.Dto.ExternalBookResponse;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenLibraryClient {

  private final RestClient restClient = RestClient.create();

  public ExternalBookResponse findByIsbn(String isbn) {
    String cleanIsbn = normalizeIsbn(isbn);

    JsonNode root =
        restClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder
                        .scheme("https")
                        .host("openlibrary.org")
                        .path("/search.json")
                        .queryParam("isbn", cleanIsbn)
                        .queryParam("limit", 1)
                        .build())
            .retrieve()
            .body(JsonNode.class);

    if (root == null || root.path("numFound").asInt() == 0) {
      throw new NotFoundException("Book not found for ISBN : " + cleanIsbn);
    }

    JsonNode doc = root.path("docs").get(0);

    return new ExternalBookResponse(
        doc.path("title").asText(null),
        authors(doc),
        cleanIsbn,
        doc.path("language").isArray() && doc.path("language").size() > 0
            ? doc.path("language").get(0).asText()
            : null,
        null,
        doc.has("cover_i")
            ? "https://covers.openlibrary.org/b/id/" + doc.path("cover_i").asText() + "-L.jpg"
            : null,
        doc.has("first_publish_year") ? doc.path("first_publish_year").asText() : null,
        "OPEN_LIBRARY_SEARCH");
  }

  private List<String> authors(JsonNode doc) {
    List<String> authors = new ArrayList<>();

    JsonNode authorsNode = doc.path("author_name");
    if (authorsNode.isArray()) {
      authorsNode.forEach(author -> authors.add(author.asText()));
    }

    return authors;
  }

  private String normalizeIsbn(String isbn) {
    if (isbn == null || isbn.isBlank()) {
      throw new BadRequestException("ISBN is required");
    }

    String cleanIsbn = isbn.replace("-", "").replace(" ", "");

    if (!isValidIsbn(cleanIsbn)) {
      throw new BadRequestException("Invalid ISBN format : " + isbn);
    }

    return cleanIsbn;
  }

  private boolean isValidIsbn(String isbn) {
    return isValidIsbn10(isbn) || isValidIsbn13(isbn);
  }

  private boolean isValidIsbn13(String isbn) {
    if (!isbn.matches("\\d{13}")) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 12; i++) {
      int digit = Character.getNumericValue(isbn.charAt(i));
      sum += (i % 2 == 0) ? digit : digit * 3;
    }

    int checkDigit = (10 - (sum % 10)) % 10;
    return checkDigit == Character.getNumericValue(isbn.charAt(12));
  }

  private boolean isValidIsbn10(String isbn) {
    if (!isbn.matches("\\d{9}[\\dXx]")) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 10; i++) {
      char c = isbn.charAt(i);
      int digit = c == 'X' || c == 'x' ? 10 : Character.getNumericValue(c);
      sum += digit * (10 - i);
    }

    return sum % 11 == 0;
  }
}
