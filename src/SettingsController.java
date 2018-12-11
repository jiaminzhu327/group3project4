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
    private Button settings_applySettings;


    @FXML
    private void applyUserSettings(){
        Stage stage = (Stage)settings_applySettings.getScene().getWindow();
        stage.close();
    }


    public boolean ageBoxSelected()
    {
        return settings_AgeCB.isSelected();
    }

}
