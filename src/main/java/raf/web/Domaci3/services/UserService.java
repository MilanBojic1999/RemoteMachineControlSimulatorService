package raf.web.Domaci3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<User,Long> {

    private IUserRepository repository;

    @Autowired
    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public <S extends User> S save(S user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
