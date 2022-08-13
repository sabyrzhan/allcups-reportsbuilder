package ru.vk.competition.minbenchmark.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vk.competition.minbenchmark.entity.TableMeta;

@Repository
public interface TablesMetaRepository extends CrudRepository<TableMeta, Integer> {
}
