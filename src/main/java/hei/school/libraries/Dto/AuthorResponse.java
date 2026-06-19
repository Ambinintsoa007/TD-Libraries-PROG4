package hei.school.libraries.Dto;

import java.time.LocalDate;

public record AuthorResponse(
    String id,
    String firstName,
    String lastName,
    LocalDate birthDate,
    String nationality,
    String biography) {}
