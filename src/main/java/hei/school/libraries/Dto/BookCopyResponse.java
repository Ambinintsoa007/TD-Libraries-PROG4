package hei.school.libraries.Dto;

import hei.school.libraries.entity.enums.Format;
import hei.school.libraries.entity.enums.Status;

public record BookCopyResponse(
    String id,
    String bookId,
    String libraryId,
    Format format,
    Double price,
    String shelfLocation,
    Status status) {}
