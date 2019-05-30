package ru.vksychev.qchoice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.vksychev.qchoice.utils.ConnectionInfo;

import java.io.IOException;

public class ConnectWindow {


    public ConnectionInfo initWindow(){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/connect_window.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Connect");
            stage.setScene(new Scene(root));
            ConnectWindowController controller = loader.getController();
            stage.showAndWait();
            return controller.getData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
