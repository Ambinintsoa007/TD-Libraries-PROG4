package hei.school.libraries.Dto;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.Customer;
import hei.school.libraries.entity.Sale;
import java.util.List;

public record LibraryResponse(
    String id,
    String name,
    String address,
    String phone,
    List<BookCopy> bookCopies,
    List<Customer> customers,
    List<Sale> sales) {}
