package ru.vksychev.qchoice;

import ru.vksychev.qchoice.utils.Alternative;
import ru.vksychev.qchoice.utils.Classifier;
import ru.vksychev.qchoice.utils.Comparison;
import ru.vksychev.qchoice.utils.Criteria;

import java.util.ArrayList;
import java.util.List;

public class QuickChoice {
    private String namesTitle;
    private Classifier classifier;
    private List<String> titles;
    private List<String> criteriasInterested;
    private List<String> criteriasNotInterested;
    private List<Criteria> criteriasList;
    private List<Alternative> alternatives;
    private List<Comparison> comparisons;

    QuickChoice(Classifier c){
        namesTitle = "name";
        criteriasNotInterested = new ArrayList<>();
        classifier = c;
        comparisons = new ArrayList<>();
        defaultNotInterested();
    }

    public void setComparisons(List<Comparison> comparisons) {
        this.comparisons = comparisons;
    }

    public void addComparisons(List<Comparison> comparisons) {
        this.comparisons.addAll(comparisons);
    }


    public List<Alternative> getTop(int n){
        classifier.setCriterias(criteriasList);
        return classifier.getTop(alternatives, n);
    }

    public void setTitles(List<String> list){
        titles = list;
        criteriasInterested = list;
    }

    public Criteria getCriteria(String name){
        for(Criteria c : criteriasList){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    public List<String> getCriterias(){
        List<String> result = new ArrayList<>(titles);
        result.removeAll(criteriasNotInterested);
        return result;
    }

    public List<String> getTitles(){
        return titles;
    }

    public void setNameTitle(String title){
        namesTitle = title;
        defaultNotInterested();
    }

    public String getNameTitle(){
        return namesTitle;
    }

    public void setNotInterested(List<String> notInterested){
        defaultNotInterested();
        criteriasNotInterested.addAll(notInterested);
        criteriasInterested.removeAll(criteriasNotInterested);
    }

    public List<Criteria> getCriteriasList() {
        return criteriasList;
    }

    public void setCriteriasList(List<Criteria> criteriasList) {
        this.criteriasList = criteriasList;
    }

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    private void defaultNotInterested(){
        criteriasNotInterested.clear();
        criteriasNotInterested.add(namesTitle);
        criteriasNotInterested.add("id");
    }
}
