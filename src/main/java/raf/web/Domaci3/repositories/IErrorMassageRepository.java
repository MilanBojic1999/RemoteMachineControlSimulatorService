package raf.web.Domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import raf.web.Domaci3.model.ErrorMassage;
import raf.web.Domaci3.model.User;

import java.util.List;
import java.util.Optional;

public interface IErrorMassageRepository extends JpaRepository<ErrorMassage,Long> {

    @Override
    Optional<ErrorMassage> findById(Long aLong);

    @Override
    List<ErrorMassage> findAll();

    @Query("select err from ErrorMassage err where err.machine.createdBy.userId = :userId")
    List<ErrorMassage> findByUser(long userId);
}
