package hei.school.libraries.Dto;

import java.util.List;

public record ExternalBookResponse(
    String title,
    List<String> authors,
    String isbn,
    String language,
    String description,
    String coverUrl,
    String publicationDate,
    String source) {}
