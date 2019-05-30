package ru.vksychev.qchoice.utils;

public class Comparison {
    Criteria left;
    Operator operator;
    Criteria right;

    public Comparison(Criteria left, Operator operator, Criteria right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {

        return "[" + left + operator.getText() + right + "]";
    }

    public enum Operator {
        LESS("<"),
        MORE(">"),
        EQUALS("==");

        private String text;

        Operator(String value){
            text = value;
        }

        public  String getText(){
            return this.text;
        }
    }
}
