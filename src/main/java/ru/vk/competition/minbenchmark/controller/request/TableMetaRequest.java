package ru.vk.competition.minbenchmark.controller.request;

import lombok.Data;
import ru.vk.competition.minbenchmark.models.ColumnInfo;

import java.util.List;

@Data
public class TableMetaRequest {
    private String tableName;
    private Integer columnsAmount;
    private String primaryKey;
    private List<ColumnInfo> columnInfos;
}
