package raf.web.Domaci3.repositories;

import org.springframework.data.repository.CrudRepository;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.User;

import java.util.List;
import java.util.Optional;

public interface IMachineRepository extends CrudRepository<Machine,Long> {
    @Override
    Optional<Machine> findById(Long aLong);

    Optional<List<Machine>> findByCreatedBy(User user);
}
