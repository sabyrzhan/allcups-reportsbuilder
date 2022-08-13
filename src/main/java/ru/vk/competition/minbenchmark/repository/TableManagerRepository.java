package ru.vk.competition.minbenchmark.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.competition.minbenchmark.entity.TableMeta;
import ru.vk.competition.minbenchmark.models.ColumnInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class TableManagerRepository {
    private final TablesMetaRepository tablesMetaRepository;
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public boolean createTable(TableMeta tableMeta) {
        String query = "create table " + tableMeta.getTableName() + " (";
        List<ColumnInfo> columns = List.of(objectMapper.readValue(tableMeta.getColumnInfos(), ColumnInfo[].class));
        List<String> columnStrings = new ArrayList<>();
        for(ColumnInfo columnInfo : columns) {
            if (columnInfo.getTitle().equals(tableMeta.getPrimaryKeyColumn())) {
                columnStrings.add(String.format("%s %s PRIMARY KEY", columnInfo.getTitle(), columnInfo.getType()));
            } else {
                columnStrings.add(String.format("%s %s", columnInfo.getTitle(), columnInfo.getType()));
            }
        }
        query += columnStrings.stream().collect(Collectors.joining(",")) + ")";

        try {
            log.info("Executing query: {}", query);
            jdbcTemplate.execute(query);
            tablesMetaRepository.save(tableMeta);
            return true;
        } catch (Exception e) {
            log.error("Create table error", e, e);
            return false;
        }
    }

    public boolean dropTable(String tableName) {
        return tablesMetaRepository.findByTableName(tableName).map(tableMeta -> {
            String query = "drop table " + tableName;
            try {
                jdbcTemplate.execute(query);
                tablesMetaRepository.deleteByTableName(tableName);
                return true;
            } catch (Exception e) {
                log.error("Drop table error", e, e);
                return false;
            }
        }).orElse(false);
    }
}
