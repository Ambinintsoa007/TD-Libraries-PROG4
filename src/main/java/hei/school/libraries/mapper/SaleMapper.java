package hei.school.libraries.mapper;

import hei.school.libraries.Dto.SaleResponse;
import hei.school.libraries.entity.Sale;

public class SaleMapper {

  public static SaleResponse toResponse(Sale sale) {
    return new SaleResponse(
        sale.getId(),
        sale.getCustomer() != null ? sale.getCustomer().getId() : null,
        sale.getSaleDate(),
        sale.getTotalAmount(),
        sale.getStatus(),
        sale.getSaleItems().stream().map(SaleItemMapper::toResponse).toList());
  }
}
