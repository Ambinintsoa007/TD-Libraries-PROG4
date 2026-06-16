package hei.school.libraries.Dto;

import java.time.LocalDate;

public record BookResponse(
    String id,
    String title,
    String language,
    String description,
    String coverUrl,
    LocalDate publicationDate) {}
