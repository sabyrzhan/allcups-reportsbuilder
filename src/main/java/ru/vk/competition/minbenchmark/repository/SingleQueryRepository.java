package ru.vk.competition.minbenchmark.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.competition.minbenchmark.entity.SingleQuery;

import java.util.Optional;

@Repository
public interface SingleQueryRepository extends CrudRepository<SingleQuery, String> {

    Optional<SingleQuery> findByQueryId(Integer id);

    @Transactional
    void deleteByQueryId(Integer id);
}
