package raf.web.Domaci3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raf.web.Domaci3.model.ErrorMassage;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IErrorMassageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ErrorMassageService implements IService<ErrorMassage,Long> {

    private IErrorMassageRepository repository;

    @Autowired
    public ErrorMassageService(IErrorMassageRepository repository) {
        this.repository = repository;
    }

    @Override
    public <S extends ErrorMassage> S save(S var1) {
        return repository.save(var1);
    }

    @Override
    public Optional<ErrorMassage> findById(Long var1) {
        return repository.findById(var1);
    }

    @Override
    public List<ErrorMassage> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        repository.deleteById(var1);
    }

    public List<ErrorMassage> findByUser(User user){
        return repository.findByUser(user.getUserId());
    }
}
