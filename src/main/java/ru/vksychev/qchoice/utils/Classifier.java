package ru.vksychev.qchoice.utils;

import java.util.List;

public abstract class Classifier {

    List<Criteria> criterias;

    public void setCriterias(List<Criteria> criterias){
        this.criterias = criterias;
    }

    abstract public List<Alternative> getTop(List<Alternative> alternatives, int n);
}
