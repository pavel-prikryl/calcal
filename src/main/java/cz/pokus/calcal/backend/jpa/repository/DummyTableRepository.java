package cz.pokus.calcal.backend.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.pokus.calcal.backend.jpa.entity.DummyTable;

@Repository
public interface DummyTableRepository extends JpaRepository<DummyTable, Integer> {
    
}
