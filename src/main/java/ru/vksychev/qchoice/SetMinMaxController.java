package ru.vksychev.qchoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.vksychev.qchoice.utils.Criteria;
import ru.vksychev.qchoice.utils.MyQuestions;

import java.net.URL;

import java.util.ResourceBundle;

public class SetMinMaxController implements Initializable {

    private Criteria criteria;
    private ToggleGroup newInformation;

    @FXML
    Label title;

    @FXML
    Button continueButton;

    @FXML
    RadioButton allRadio,mmRadio;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newInformation = new ToggleGroup();
        allRadio.setToggleGroup(newInformation);
        mmRadio.setToggleGroup(newInformation);
        newInformation.selectToggle(allRadio);
    }

    public void setCriteria(Criteria cr) {
        criteria = cr;
        title.setText(String.format(MyQuestions.QUESTION_INFORMATION_SET_MIN_MAX, criteria.getName()));
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public boolean isMinMax(){
        return mmRadio.isSelected();
    }

}






