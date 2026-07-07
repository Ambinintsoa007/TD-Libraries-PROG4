package hei.school.libraries.service;


import hei.school.libraries.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookStockService {
    private static final long SEUIL_STOCK_MINIMUM = 3;
    private final BookRepository bookRepository;
    private final BookCopyRepository ;

}
