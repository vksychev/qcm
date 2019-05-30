package ru.vksychev.qchoice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import ru.vksychev.qchoice.utils.ConnectionInfo;
import ru.vksychev.qchoice.utils.DataBaseDriver;
import ru.vksychev.qchoice.utils.MyDataBase;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectWindowController implements Initializable {

    private ConnectionInfo data;

    @FXML
    private javafx.scene.control.Button buttonClose;
    @FXML
    private javafx.scene.control.Button buttonConnect;
    @FXML
    private javafx.scene.control.TextField textIP;
    @FXML
    private javafx.scene.control.TextField textPort;
    @FXML
    private javafx.scene.control.TextField textUser;
    @FXML
    private javafx.scene.control.TextField textPassword;
    @FXML
    private javafx.scene.control.TextField textDB;
    @FXML
    private javafx.scene.control.ComboBox comboDriver;

    @FXML
    private void onCloseListener() {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onConnectListener() {
        DataBaseDriver driver = (DataBaseDriver) comboDriver.getValue();
        String url = null;
        switch (driver.getName()) {
            case (MyDataBase.MYSQL): {
                if (textPort.getText().isBlank()) {
                    url = "jdbc:mysql://" + textIP.getText() + "/" + textDB.getText() + MyDataBase.FIX;
                } else {
                    url = "jdbc:mysql://" + textIP.getText() + ":" + textPort.getText().strip()
                            + "/" + textDB.getText() + MyDataBase.FIX;
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException();
            }
        }
        data = new ConnectionInfo(url, textUser.getText(), textPassword.getText());
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<DataBaseDriver> drivers = FXCollections.observableArrayList();
        //Set New Drivers
        drivers.add(new DataBaseDriver(MyDataBase.MYSQL, MyDataBase.MYSQL_DRIVER));
        drivers.add(new DataBaseDriver(MyDataBase.POSTSQL, null));
        comboDriver.setItems(drivers);
        comboDriver.getSelectionModel().select(0);

    }

    ConnectionInfo getData() {
        return data;
    }
}
