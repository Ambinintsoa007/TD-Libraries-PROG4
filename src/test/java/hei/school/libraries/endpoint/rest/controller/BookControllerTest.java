package hei.school.libraries.endpoint.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hei.school.libraries.Dto.BookResponse;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.service.BookService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import hei.school.libraries.service.BookExternalService;
import hei.school.libraries.service.BookImportService;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private BookService bookService;

  @MockBean
  private BookExternalService bookExternalService;

  @MockBean
  private BookImportService bookImportService;

  @Test
  void getBooks_withExistingUUID_shouldReturn200() throws Exception {

    BookResponse book =
        new BookResponse(
            "9c858901-8a57-4791-81fe-4c455b099bc9",
            "Le Petit Prince",
            "978-2-07-040850-4",
            "FR",
            "desc",
            "cover.jpg",
            LocalDate.now());
    when(bookService.getAllBooks()).thenReturn(List.of(book));

    mockMvc
        .perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value("9c858901-8a57-4791-81fe-4c455b099bc9"))
        .andExpect(jsonPath("$[0].title").value("Le Petit Prince"))
        .andExpect(jsonPath("$[0].isbn").value("978-2-07-040850-4"));
  }

  @Test
  void getBookById_with_invalidUUID_shouldThrow400() throws Exception {
    String invalidId = "not-a-uuid";
    when(bookService.getBookById(invalidId))
        .thenThrow(new BadRequestException("Invalid book id format : " + invalidId));

    mockMvc.perform(get("/books/{id}", invalidId)).andExpect(status().isBadRequest());
  }

  @Test
  void getBookById_with_nonExistingBook_shouldThrow404() throws Exception {

    String unknownId = "9c858901-8a57-4791-81fe-4c455b099bc9";
    when(bookService.getBookById(unknownId))
        .thenThrow(new NotFoundException("Book not found : " + unknownId));

    mockMvc.perform(get("/books/{id}", unknownId)).andExpect(status().isNotFound());
  }
}
