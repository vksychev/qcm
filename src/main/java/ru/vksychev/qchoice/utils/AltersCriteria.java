package ru.vksychev.qchoice.utils;

public class AltersCriteria {
    private Criteria criteria;
    private String value;

    public AltersCriteria(Criteria criteria, String value) {
        this.criteria = criteria;
        this.value = value;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
