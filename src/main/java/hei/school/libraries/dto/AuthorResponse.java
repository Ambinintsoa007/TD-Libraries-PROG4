package hei.school.libraries.dto;

import java.time.LocalDate;

public record AuthorResponse(
    String id,
    String firstName,
    String lastName,
    LocalDate birthDate,
    String nationality,
    String biography) {}
