package ru.vk.competition.minbenchmark.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "tables_meta")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableMeta {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tableName")
    private String tableName;

    @Column(name = "columnsAmount")
    private Integer columnsAmount;

    @Column(name = "primaryKeyColumn")
    @JsonProperty("primaryKey")
    private String primaryKeyColumn;

    @Column(name = "columnInfos")
    private String columnInfos;
}
