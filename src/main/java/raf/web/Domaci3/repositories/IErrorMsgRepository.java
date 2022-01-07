package raf.web.Domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.web.Domaci3.model.ErrorMassage;

import java.util.List;
import java.util.Optional;

public interface IErrorMsgRepository extends JpaRepository<ErrorMassage,Long> {

    @Override
    Optional<ErrorMassage> findById(Long id);

}
