package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.LogData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ILogDataRepository extends JpaRepository<LogData, Long> {

    @Query("SELECT NOW()")
    Instant getDateSystemNow();

}
