package raf.web.Domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.User;

import java.util.List;
import java.util.Optional;

public interface IMachineRepository extends JpaRepository<Machine,Long>, JpaSpecificationExecutor<Machine> {
    @Override
    Optional<Machine> findById(Long aLong);

    Optional<List<Machine>> findByCreatedBy(User user);
}
