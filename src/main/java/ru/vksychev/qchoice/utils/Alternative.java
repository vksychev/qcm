package ru.vksychev.qchoice.utils;

import java.util.ArrayList;
import java.util.List;

public class Alternative {
    private List<AltersCriteria> criterias;
    private String name;
    private Double rang;

    public Alternative(String name, List<AltersCriteria> criterias){
        this.name = name;
        this.criterias = criterias;
    }

    public String getValue(String name){
        for (AltersCriteria criteria: criterias){
            if(criteria.getCriteria().getName().equals(name)){
                return criteria.getValue();
            }
        }
        return null;
    }

    public List<String> getValues(){
        List<String> res = new ArrayList<>();
        for (AltersCriteria a :criterias){
            res.add(a.getValue());
        }
        return res;
    }

    public List<AltersCriteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<AltersCriteria> criterias) {
        this.criterias = criterias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name + ": [ ");
        for(AltersCriteria a : criterias){
            result.append(a.getCriteria().getName()).append(" : ").append(a.getValue()).append(", ");
        }
        return result.deleteCharAt(result.lastIndexOf(",")).append("]").toString();
    }

    public Double getRang() {
        return rang;
    }

    public void setRang(Double rang) {
        this.rang = rang;
    }
}
