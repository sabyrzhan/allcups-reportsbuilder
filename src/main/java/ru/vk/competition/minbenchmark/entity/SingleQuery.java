package ru.vk.competition.minbenchmark.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "single_query")
@NoArgsConstructor
@AllArgsConstructor
public class SingleQuery {

    @Id
    @Column(name = "queryId")
    private Integer queryId;

    @Column(name = "query")
    private String query;
}