package raf.web.Domaci3.search;

import org.springframework.data.jpa.domain.Specification;
import raf.web.Domaci3.model.Machine;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MachineSpecification implements Specification<Machine> {

    private MachineCriteria criteria;

    public MachineSpecification(MachineCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Machine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        System.err.println(root.join(criteria.getKey()).get("name"));
        if(criteria.getKey().equalsIgnoreCase("dateFrom")){
            return criteriaBuilder.greaterThanOrEqualTo(root.join(criteria.getKey()), criteria.getValue().toString());
        }else if(criteria.getKey().equalsIgnoreCase("dateTo")){
            return criteriaBuilder.lessThanOrEqualTo(root.join(criteria.getKey()), criteria.getValue().toString());
        }else{
            return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
    }
}
