package hei.school.libraries.dto;

import hei.school.libraries.entity.enums.Format;
import hei.school.libraries.entity.enums.Status;

public record BookCopyRequest(
        String bookId,
        String libraryId,
        Format format,
        Double price,
        String shelfLocation,
        Status status
) {}