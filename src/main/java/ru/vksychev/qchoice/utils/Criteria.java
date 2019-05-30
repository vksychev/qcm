package ru.vksychev.qchoice.utils;


import java.math.BigDecimal;
import java.util.List;

public class Criteria {
    private String name;
    private CriteriaTarget target;
    private BigDecimal min, max;
    private CriteriaValue value;
    private List<String> values;

    public Criteria(String name, List<String> values){
        this.name = name;
        this.values = values;
    }

    @Override
    public String toString() {
        return name + " " + values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CriteriaTarget getTarget() {
        return target;
    }

    public void setTarget(CriteriaTarget target) {
        this.target = target;
    }

    public void setMinMax(BigDecimal min,BigDecimal max){
        this.min = min;
        this.max = max;
    }

    public BigDecimal getMax(){
        return max;
    }

    public BigDecimal getMin(){
        return min;
    }

    public CriteriaValue getValue() {
        return value;
    }

    public void setValue(CriteriaValue value) {
        this.value = value;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

}
