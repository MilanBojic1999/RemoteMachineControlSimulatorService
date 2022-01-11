package raf.web.Domaci3.search;

public class MachineCriteria {

    private String key;
    private Object value;

    public MachineCriteria(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public MachineCriteria() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
