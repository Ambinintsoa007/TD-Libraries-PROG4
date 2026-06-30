package hei.school.libraries.dto;

import java.time.LocalDate;

public record BookResponse(
    String id,
    String title,
    String isbn,
    String language,
    String description,
    String coverUrl,
    LocalDate publicationDate) {}
