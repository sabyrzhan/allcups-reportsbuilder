package ru.vk.competition.minbenchmark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.vk.competition.minbenchmark.controller.request.TableMetaRequest;
import ru.vk.competition.minbenchmark.entity.TableMeta;
import ru.vk.competition.minbenchmark.models.ColumnInfo;
import ru.vk.competition.minbenchmark.repository.TableManagerRepository;
import ru.vk.competition.minbenchmark.repository.TablesMetaRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class TableManagerController {
    private final TableManagerRepository tableManagerRepository;
    private final TablesMetaRepository tablesMetaRepository;
    private final ObjectMapper objectMapper;

    @PostMapping("/create-table")
    public Mono<ResponseEntity<Void>> createTable(@RequestBody TableMetaRequest tableMeta) {
        return Mono.fromCallable(() -> {
            tableManagerRepository.createTable(toTableMeta(tableMeta));
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }).publishOn(Schedulers.boundedElastic());
    }

    @GetMapping("/get-table-by-name/{tableName}")
    public Mono<ResponseEntity<Object>> getTableByName(@PathVariable String tableName) {
        return Mono.fromCallable(() -> {
            return tablesMetaRepository.findByTableName(tableName)
                    .map(tableMeta -> {
                        try {
                            var result = new TableMetaRequest();
                            result.setTableName(tableName);
                            result.setColumnsAmount(tableMeta.getColumnsAmount());
                            result.setPrimaryKey(tableMeta.getPrimaryKeyColumn());
                            result.setColumnInfos(List.of(objectMapper.readValue(tableMeta.getColumnInfos(), ColumnInfo[].class)));
                            return new ResponseEntity<Object>(result, HttpStatus.OK);
                        } catch (Exception e) {
                            log.error("Error while getting table by tableName: {}", e, e);
                            return new ResponseEntity<Object>("", HttpStatus.OK);
                        }
                    }).orElse(new ResponseEntity<>("", HttpStatus.OK));
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
