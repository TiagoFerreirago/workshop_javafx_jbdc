package gui.utils;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	public static Stage currentStage(ActionEvent acEvent) {
		return (Stage)((Node)acEvent.getSource()).getScene().getWindow();
	}
}
