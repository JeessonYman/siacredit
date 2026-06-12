package com.cajatrujillo.siacredit.repository;

import com.cajatrujillo.siacredit.model.Prospecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProspectoRepository extends JpaRepository<Prospecto, Long> {
}