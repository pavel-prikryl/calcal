package cz.pokus.calcal.backend.jpa.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.pokus.calcal.backend.jpa.entity.DummyTable;
import cz.pokus.calcal.backend.jpa.entity.Measurement;
import cz.pokus.calcal.backend.jpa.repository.DummyTableRepository;
import cz.pokus.calcal.backend.jpa.repository.MeasurementRepository;

@Transactional
@Service
public class CalCalServiceImpl implements CalCalService {

    @Autowired
    private DummyTableRepository dtRepo;

    @Autowired
    private MeasurementRepository mRepo;

    @Override
    public List<DummyTable> dummyTableFindAll() {
        return dtRepo.findAll();
    }

    @Override
    public void dummyTableSave(DummyTable dummyTable) {
        dtRepo.save(dummyTable);
    }

    @Override
    public int genDummyTable() {
        Random rnd = new Random();
        int ret = 0;
        for (int i = 0; i < 10; i++) {
            DummyTable line = new DummyTable();
            line.setTextik("textik_" + rnd.nextInt(100000));
            dtRepo.save(line);
            ret++;
        }
        return ret;
    }

    @Override
    public List<Measurement> findAll() {
        return mRepo.findAll();
    }

    @Override
    public Measurement save(Measurement m) {
        if (m.getId() == null) {
            m.setCreated(new Date());
        }
        return mRepo.save(m);
    }

    @Override
    public List<Measurement> findByNameEquals(String name) {
        return mRepo.findByNameEquals(name);
    }

}
