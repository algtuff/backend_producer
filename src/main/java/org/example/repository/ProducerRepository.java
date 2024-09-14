package org.example.repository;

import jakarta.persistence.Tuple;
import org.example.dto.WinnerDTO;
import org.example.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static org.example.constants.ApplicationConstants.QUERY_WINNER_MAX_INTERVAL;
import static org.example.constants.ApplicationConstants.QUERY_WINNER_MIN_INTERVAL;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    @Query(value = QUERY_WINNER_MAX_INTERVAL, nativeQuery = true)
    List<Tuple> findWinnerMaxInterval();

    @Query(value = QUERY_WINNER_MIN_INTERVAL, nativeQuery = true)
    List<Tuple> findWinnerMinInterval();
}
