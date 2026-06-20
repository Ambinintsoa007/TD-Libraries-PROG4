package hei.school.libraries.service;

import hei.school.libraries.Dto.BookResponse;
import hei.school.libraries.entity.Author;
import hei.school.libraries.entity.Book;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.Customer;
import hei.school.libraries.entity.Sale;
import hei.school.libraries.entity.SaleItem;
import hei.school.libraries.entity.enums.SaleStatus;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.exception.ForbiddenException;
import hei.school.libraries.exception.InsufficientStockException;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.mapper.BookMapper;
import hei.school.libraries.repository.AuthorRepository;
import hei.school.libraries.repository.BookCopyRepository;
import hei.school.libraries.repository.BookRepository;
import hei.school.libraries.repository.CustomerRepository;
import hei.school.libraries.repository.SaleItemRepository;
import hei.school.libraries.repository.SaleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final BookCopyRepository bookCopyRepository;
  private final SaleRepository saleRepository;
  private final SaleItemRepository saleItemRepository;
  private final CustomerRepository customerRepository;
  private final BookMapper bookMapper;

  public List<BookResponse> getAllBooks() {
    return bookRepository.findAll().stream()
        .map(
            book ->
                new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getLanguage(),
                    book.getDescription(),
                    book.getCoverUrl(),
                    book.getPublicationDate()))
        .toList();
  }

  public Book getBookById(String id) {
    try {
      java.util.UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid book id format : " + id);
    }
    return bookRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Book not found : " + id));
  }

  public Book createBook(Book book) {
    return bookRepository.save(book);
  }

  public Book patchBook(String id, Book book) {
    Book found = getBookById(id);
    bookMapper.patch(found, book);
    return bookRepository.save(found);
  }

  public void deleteBook(String id) {
    Book found = getBookById(id);
    if (!found.getBookCopies().isEmpty()) {
      throw new ForbiddenException("Cannot delete book with existing copies");
    }
    bookRepository.delete(found);
  }

  @Transactional
  public Book addAuthorToBook(String bookId, String authorId) {
    Book book = getBookById(bookId);
    Author author =
        authorRepository
            .findById(authorId)
            .orElseThrow(() -> new NotFoundException("Author not found : " + authorId));
    if (!book.getAuthors().contains(author)) {
      book.getAuthors().add(author);
    }
    return bookRepository.save(book);
  }

  public List<BookResponse> getLowStock(int threshold) {
    return bookRepository.findAll().stream()
        .filter(book -> getAvailableStock(book.getId()) < threshold)
        .map(
            book ->
                new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getLanguage(),
                    book.getDescription(),
                    book.getCoverUrl(),
                    book.getPublicationDate()))
        .toList();
  }

  public long getAvailableStock(String bookId) {
    return bookCopyRepository.countByBook_IdAndStatus(bookId, Status.AVAILABLE);
  }

  @Transactional
  public Sale sell(String bookId, String customerId, int quantity) {
    Book book = getBookById(bookId);
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new NotFoundException("Customer not found : " + customerId));

    List<BookCopy> availableCopies =
        bookCopyRepository.findByBook_IdAndStatus(bookId, Status.AVAILABLE);

    if (availableCopies.size() < quantity) {
      throw new InsufficientStockException(
          "Stock insuffisant pour le livre "
              + bookId
              + " : demandé "
              + quantity
              + ", disponible "
              + availableCopies.size());
    }

    Sale sale = new Sale();
    sale.setCustomer(customer);
    sale.setSaleDate(LocalDate.now());
    sale.setStatus(SaleStatus.PENDING);

    double total = 0.0;
    for (int i = 0; i < quantity; i++) {
      BookCopy copy = availableCopies.get(i);
      copy.setStatus(Status.OUT_OF_STOCK);
      bookCopyRepository.save(copy);

      SaleItem item = new SaleItem();
      item.setSale(sale);
      item.setBookCopy(copy);
      item.setUnitPrice(copy.getPrice());
      saleItemRepository.save(item);

      total += copy.getPrice() != null ? copy.getPrice() : 0.0;
    }

    sale.setTotalAmount(total);
    return saleRepository.save(sale);
  }
}
