package ru.vk.competition.minbenchmark.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.vk.competition.minbenchmark.entity.SingleQuery;
import ru.vk.competition.minbenchmark.service.SingleQueryService;

@Slf4j
@RestController
@RequestMapping("/api/single-query")
@RequiredArgsConstructor
public class SingleQueryController {

    private final SingleQueryService singleQueryService;

    @GetMapping("/get-all-single-queries")
    public Flux<SingleQuery> getAllTableQueries() {
        return singleQueryService.getAllQueries();
    }

    @GetMapping("/get-single-query-by-id/{id}")
    public Mono<SingleQuery> getTableQueryById(@PathVariable Integer id) {
        return singleQueryService.getQueryById(id);
    }

    @DeleteMapping("/delete-single-query-by-id/{id}")
    public Mono<ResponseEntity<Void>> deleteTableQueryById(@PathVariable Integer id) {
        return singleQueryService.deleteQueryById(id);
    }

    @PostMapping("/add-new-query")
    public Mono<ResponseEntity<Void>> addNewQueryToTable(@RequestBody SingleQuery singleQuery) {
        return singleQueryService.addQueryWithQueryId(singleQuery);
    }

    @PutMapping("/modify-single-query")
    public Mono<ResponseEntity<Void>> modifyQueryInTable(@RequestBody SingleQuery singleQuery) {
        return singleQueryService.updateQueryWithQueryId(singleQuery);
    }
}