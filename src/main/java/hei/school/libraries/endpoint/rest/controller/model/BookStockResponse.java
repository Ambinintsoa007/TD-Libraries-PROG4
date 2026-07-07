package hei.school.libraries.endpoint.rest.controller.model;

public record BookStockResponse(
        String bookId,
        String title,
        long availableCopies,
        boolean stockInsuffisant
) {}