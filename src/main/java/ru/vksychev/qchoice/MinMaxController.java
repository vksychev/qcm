package ru.vksychev.qchoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.vksychev.qchoice.utils.AlertWindow;
import ru.vksychev.qchoice.utils.Criteria;
import ru.vksychev.qchoice.utils.MyQuestions;

import java.math.BigDecimal;
import java.net.URL;

import java.util.ResourceBundle;

public class MinMaxController implements Initializable {

    Criteria criteria;

    @FXML
    Label title;

    @FXML
    Button continueButton;

    @FXML
    TextField minText, maxText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCriteria(Criteria cr) {
        criteria = cr;
        title.setText(String.format(MyQuestions.QUESTION_INFORMATION_MIN_MAX, criteria.getName()));
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public BigDecimal getMin() {
        try {
            return new BigDecimal(Integer.valueOf(minText.getText()));
        } catch (NumberFormatException e) {
            AlertWindow.showAlert("Error", "NumberFormatException", "Wrong number type.");
        }
        return null;
    }

    public BigDecimal getMax() {
        try {
            return new BigDecimal(Integer.valueOf(maxText.getText()));
        } catch (NumberFormatException e) {
            AlertWindow.showAlert("Error", "NumberFormatException", "Wrong number type.");
        }
        return null;
    }

    public boolean checkMinMax() {
        if (maxText.getText().isEmpty() || minText.getText().isEmpty()){
            AlertWindow.showAlert("Error", "NumberFormatException", "Fill all fields.");
            return false;
        }
        try {
            BigDecimal min = new BigDecimal(Integer.valueOf(minText.getText()));
            BigDecimal max = new BigDecimal(Integer.valueOf(maxText.getText()));
            if (min.compareTo(max) <= 0){
                return true;
            }
        } catch (NumberFormatException e) {
            AlertWindow.showAlert("Error", "NumberFormatException", "Wrong number type.");
        }
        return false;
    }


}






