package raf.web.Domaci3.search;

import org.springframework.data.jpa.domain.Specification;
import raf.web.Domaci3.model.Machine;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MachineSpecification implements Specification<Machine> {

    @Override
    public Predicate toPredicate(Root<Machine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
