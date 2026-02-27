package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.LogAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogAccessRepository extends JpaRepository<LogAccess, Long> {
}
