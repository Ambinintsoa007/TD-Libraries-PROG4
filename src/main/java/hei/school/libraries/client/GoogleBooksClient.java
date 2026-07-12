package hei.school.libraries.client;

import com.fasterxml.jackson.databind.JsonNode;
import hei.school.libraries.Dto.ExternalBookResponse;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class GoogleBooksClient {

    private final RestClient restClient = RestClient.create();

    @Value("${google.books.api.key:}")
    private String googleBooksApiKey;

    public ExternalBookResponse findByIsbn(String isbn) {
        String cleanIsbn = normalizeIsbn(isbn);

        try {
            JsonNode root =
                    restClient
                            .get()
                            .uri(
                                    uriBuilder -> {
                                        var builder =
                                                uriBuilder
                                                        .scheme("https")
                                                        .host("www.googleapis.com")
                                                        .path("/books/v1/volumes")
                                                        .queryParam("q", "isbn:" + cleanIsbn)
                                                        .queryParam("maxResults", 1);

                                        if (googleBooksApiKey != null && !googleBooksApiKey.isBlank()) {
                                            builder.queryParam("key", googleBooksApiKey);
                                        }

                                        return builder.build();
                                    })
                            .retrieve()
                            .body(JsonNode.class);

            if (root == null || root.path("totalItems").asInt() == 0) {
                throw new NotFoundException("Book not found for ISBN : " + cleanIsbn);
            }

            JsonNode volumeInfo = root.path("items").get(0).path("volumeInfo");

            return new ExternalBookResponse(
                    text(volumeInfo, "title"),
                    authors(volumeInfo),
                    cleanIsbn,
                    text(volumeInfo, "language"),
                    text(volumeInfo, "description"),
                    volumeInfo.path("imageLinks").path("thumbnail").asText(null),
                    text(volumeInfo, "publishedDate"),
                    "GOOGLE_BOOKS");

        } catch (NotFoundException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Google Books API is unavailable");
        }
    }

    private String normalizeIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new BadRequestException("ISBN is required");
        }

        String cleanIsbn = isbn.replace("-", "").replace(" ", "");

        if (!cleanIsbn.matches("\\d{10}|\\d{13}")) {
            throw new BadRequestException("Invalid ISBN format : " + isbn);
        }

        return cleanIsbn;
    }

    private String text(JsonNode node, String field) {
        return node.path(field).asText(null);
    }

    private List<String> authors(JsonNode volumeInfo) {
        List<String> authors = new ArrayList<>();
        JsonNode authorsNode = volumeInfo.path("authors");

        if (authorsNode.isArray()) {
            authorsNode.forEach(author -> authors.add(author.asText()));
        }

        return authors;
    }
}