package hei.school.libraries.Dto;

import hei.school.libraries.entity.enums.SaleStatus;
import java.time.LocalDate;
import java.util.List;

public record SaleResponse(
    String id,
    String customerId,
    LocalDate saleDate,
    Double totalAmount,
    SaleStatus status,
    List<SaleItemResponse> saleItems) {}
