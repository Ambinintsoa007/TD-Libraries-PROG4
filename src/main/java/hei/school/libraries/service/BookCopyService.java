package hei.school.libraries.service;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.repository.BookCopyRepository;
import hei.school.libraries.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class BookCopyService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    public List<BookCopy> getCopies(String bookId, Status status) {
        bookRepository
                .findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found : " + bookId));

        if (status != null) {
            return bookCopyRepository.findByBook_IdAndStatus(bookId, status);
        }
        return bookCopyRepository.findByBook_Id(bookId);
    }
}
