package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.Dto.GenreRevenueResponse;
import hei.school.libraries.service.StatisticsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/revenue-by-genre")
    public List<GenreRevenueResponse> getRevenueByGenre() {
        return statisticsService.getRevenueByGenre();
    }
}