package ru.vk.competition.minbenchmark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.vk.competition.minbenchmark.controller.request.TableMetaRequest;
import ru.vk.competition.minbenchmark.entity.TableMeta;
import ru.vk.competition.minbenchmark.repository.TableManagerRepository;

@Slf4j
@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class TableManagerController {
    private final TableManagerRepository tableManagerRepository;
    private final ObjectMapper objectMapper;

    @PostMapping("/create-table")
    public Mono<ResponseEntity<Void>> createTable(@RequestBody TableMetaRequest tableMeta) {
        return Mono.fromCallable(() -> {
            tableManagerRepository.createTable(toTableMeta(tableMeta));
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }).publishOn(Schedulers.boundedElastic());
    }

    @SneakyThrows
    public TableMeta toTableMeta(TableMetaRequest request) {
        var tableMeta = new TableMeta();
        tableMeta.setTableName(request.getTableName());
        tableMeta.setColumnsAmount(request.getColumnsAmount());
        tableMeta.setPrimaryKeyColumn(request.getPrimaryKey());
        tableMeta.setColumnInfos(objectMapper.writeValueAsString(request.getColumnInfos()));

        return tableMeta;
    }
}
