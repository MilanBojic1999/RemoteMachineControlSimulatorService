package raf.web.Domaci3.search;

import java.util.List;

public class MachineCriteriaList {

    private List<MachineCriteria> list;

    public MachineCriteriaList(List<MachineCriteria> list) {
        this.list = list;
    }

    public MachineCriteriaList() {
    }

    public List<MachineCriteria> getList() {
        return list;
    }

    public void setList(List<MachineCriteria> list) {
        this.list = list;
    }
}
