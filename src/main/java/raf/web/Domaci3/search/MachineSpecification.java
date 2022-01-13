package raf.web.Domaci3.search;

import org.springframework.data.jpa.domain.Specification;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MachineSpecification implements Specification<Machine> {

    private MachineCriteria criteria;
    private DateTimeFormatter formatter;
    public MachineSpecification(MachineCriteria criteria) {
        this.criteria = criteria;
         formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @Override
    public Predicate toPredicate(Root<Machine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        System.err.println(criteria.getKey() + " ___ "+ criteria.getValue());
        if(criteria.getKey().equalsIgnoreCase("dateFrom")){
            return criteriaBuilder.greaterThanOrEqualTo(root.get("created"), Date.from(LocalDate.parse(criteria.getValue().toString(),formatter).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }else if(criteria.getKey().equalsIgnoreCase("dateTo")){
            return criteriaBuilder.lessThanOrEqualTo(root.get("created"), Date.from(LocalDate.parse(criteria.getValue().toString(),formatter).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }else if(criteria.getKey().equalsIgnoreCase("name")){
            return criteriaBuilder.like(root.get(criteria.getKey()), "%"+criteria.getValue().toString().toUpperCase()+"%");
        }else{
            return criteriaBuilder.equal(root.get(criteria.getKey()), StatusEnum.valueOf(criteria.getValue().toString()));
        }
    }
}
