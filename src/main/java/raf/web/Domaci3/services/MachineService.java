package raf.web.Domaci3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.repositories.IMachineRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService implements IService<Machine,Long> {

    private final IMachineRepository repository;
    @Autowired
    public MachineService(IMachineRepository repository) {
        this.repository = repository;
    }

    @Override
    public <S extends Machine> S save(S var1) {
        return repository.save(var1);
    }

    @Override
    public Optional<Machine> findById(Long var1) {
        return repository.findById(var1);
    }

    @Override
    public List<Machine> findAll() {
        return (List<Machine>) repository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        repository.deleteById(var1);
    }

    public IMachineRepository getRepository() {
        return repository;
    }
}
