package cz.pokus.calcal.backend.jpa.service;

import java.util.List;

import cz.pokus.calcal.backend.jpa.entity.DummyTable;
import cz.pokus.calcal.backend.jpa.entity.Measurement;

public interface CalCalService {
    
    List<Measurement> findAll();    
    Measurement save(Measurement m);
    List<Measurement> findByNameEquals(String name);
    
    List<DummyTable> dummyTableFindAll();
    void dummyTableSave(DummyTable dummyTable);
    int genDummyTable();
}
