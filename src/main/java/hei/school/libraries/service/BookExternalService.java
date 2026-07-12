package hei.school.libraries.service;

import hei.school.libraries.Dto.ExternalBookResponse;
import hei.school.libraries.client.GoogleBooksClient;
import hei.school.libraries.client.OpenLibraryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookExternalService {

    private final GoogleBooksClient googleBooksClient;
    private final OpenLibraryClient openLibraryClient;

    public ExternalBookResponse findByIsbn(String isbn) {
        try {
            return googleBooksClient.findByIsbn(isbn);
        } catch (Exception e) {
            return openLibraryClient.findByIsbn(isbn);
        }
    }
}