package hei.school.libraries.dto;

import java.util.List;

public record LibraryResponse(
    String id,
    String name,
    String address,
    String phone,
    List<String> bookCopyIds,
    List<String> customerIds,
    List<String> saleIds) {}
