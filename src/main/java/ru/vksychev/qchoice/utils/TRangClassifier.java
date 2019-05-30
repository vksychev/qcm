package ru.vksychev.qchoice.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TRangClassifier extends Classifier {

    private double a = 0;
    private double b = 1;

    @Override
    public List<Alternative> getTop(List<Alternative> alternatives, int n) {
        for (Alternative alt : alternatives) {
            Double d = getRang(alt);
            if (d == null) return null;
            alt.setRang(d);
        }
        alternatives.sort(Comparator.comparing(Alternative::getRang).reversed());
        for (Alternative alt : alternatives) {
            System.out.println(alt.getName() + ": " + alt.getRang());
        }
        if (n > alternatives.size()) return alternatives;
        return alternatives.subList(0, n);
    }

    private Double getRang(Alternative alt) {
        List<Double> rangs = getAllRangs(alt);
        boolean weights = false;
        if (!weights) {
            return sum(rangs);
        }
        return null;
    }

    private List<Double> getAllRangs(Alternative alt) {
        if (criterias == null) return null;

        List<Double> rangs = new ArrayList<>();
        for (AltersCriteria altCriteria : alt.getCriterias()) {
            if (altCriteria.getCriteria().getValue().equals(CriteriaValue.NUMERICALLY)) {

                if (altCriteria.getCriteria().getMin() != null && altCriteria.getCriteria().getMax() != null) {
                    double min = altCriteria.getCriteria().getMin().doubleValue();
                    double max = altCriteria.getCriteria().getMax().doubleValue();
                    double value = Double.valueOf(altCriteria.getValue());
                    rangs.add(normalize(min,max,value,altCriteria.getCriteria().getTarget()));
                } else {
                    double min = min(altCriteria.getCriteria().getValues());
                    double max = max(altCriteria.getCriteria().getValues());
                    double value = Double.valueOf(altCriteria.getValue());
                    rangs.add(normalize(min,max,value,altCriteria.getCriteria().getTarget()));
                }
            } else {
                double min = 0;
                double max = altCriteria.getCriteria().getValues().size() - 1;
                double index = altCriteria.getCriteria().getValues().indexOf(altCriteria.getValue());
                double result = a + (b - a) * ((max - index) - min) / (max - min);
                rangs.add(altCriteria.getCriteria().getTarget() == CriteriaTarget.MAX ? result : 1d - result);

            }

        }
        System.out.println(alt.getName() + ": " + rangs);
        return rangs;
    }

    private Double normalize(double min, double max, double value, CriteriaTarget target) {
        double result = a + (b - a) * (value - min) / (max - min);
        if (Double.isNaN(result)) result = 1;
        return target == CriteriaTarget.MAX ? result : 1d - result;
    }

    private Double sum(List<Double> list) {
        Double result = 0d;
        for (Double d : list) {
            if (!Double.isNaN(d)) {
                result += d;
            } else {
                result += 1;
            }
        }
        return result;
    }

    private Double min(List<String> list) {
        boolean isChanged = false;
        Double min = 0d;

        for (String s : list) {
            double t = Double.valueOf(s);
            if (min > Double.valueOf(s) || !isChanged) {
                min = t;
                isChanged = true;
            }
        }
        if (isChanged = false) {
            return null;
        }
        return min;
    }

    private Double max(List<String> list) {
        boolean isChanged = false;
        Double max = 0d;

        for (String s : list) {
            double t = Double.valueOf(s);
            if (max < Double.valueOf(s) || !isChanged) {
                max = t;
                isChanged = true;
            }
        }
        if (isChanged = false) {
            return null;
        }
        return max;
    }
}
