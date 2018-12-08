import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private CheckBox  settings_AgeCB;

    @FXML
    private CheckBox settings_emailCB;

    @FXML
    private CheckBox settings_friendsListCB;

    @FXML
    private CheckBox settings_postsCB;

    @FXML
    private Button settings_defaultSettings;

    @FXML
    private Button settings_applySettings;

    @FXML
    private void applyDefaultSettings(){

        settings_AgeCB.setSelected(false);
        settings_emailCB.setSelected(false);
        settings_friendsListCB.setSelected(false);
        settings_postsCB.setSelected(false);
    }

    @FXML
    private void applyUserSettings(){

        //Closes settings window after values are stored
        Stage stage = (Stage)settings_applySettings.getScene().getWindow();
        stage.close();
    }

}
