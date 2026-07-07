package hei.school.libraries.service;


import hei.school.libraries.endpoint.rest.controller.model.BookStockResponse;
import hei.school.libraries.entity.Book;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.repository.BookCopyRepository;
import hei.school.libraries.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookStockService {
    private static final long SEUIL_STOCK_MINIMUM = 3;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    public BookStockResponse getStock(String bookId) {
        Book book =
                bookRepository
                        .findById(bookId)
                        .orElseThrow(() -> new RuntimeException("Book not found : " + bookId));

        long available = bookCopyRepository.countByBook_IdAndStatus(bookId, Status.AVAILABLE);
        boolean insuffisant = available <= SEUIL_STOCK_MINIMUM;

        return new BookStockResponse(book.getId(), book.getTitle(), available, insuffisant);
    }
}
