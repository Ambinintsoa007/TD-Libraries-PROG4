package hei.school.libraries.mapper;

import hei.school.libraries.Dto.SaleItemResponse;
import hei.school.libraries.entity.SaleItem;

public class SaleItemMapper {

  public static SaleItemResponse toResponse(SaleItem saleItem) {
    return new SaleItemResponse(
        saleItem.getId(),
        saleItem.getBookCopy() != null ? saleItem.getBookCopy().getId() : null,
        saleItem.getUnitPrice());
  }
}
