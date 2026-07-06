package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.Dto.SaleItemRequest;
import hei.school.libraries.Dto.SaleResponse;
import hei.school.libraries.entity.Sale;
import hei.school.libraries.mapper.SaleMapper;
import hei.school.libraries.service.SaleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

  private final SaleService saleService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SaleResponse createSale(@RequestBody List<SaleItemRequest> lines) {
    Sale sale = saleService.createSale(lines);
    return SaleMapper.toResponse(sale);
  }
}
