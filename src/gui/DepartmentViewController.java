package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentViewController implements Initializable {

	@FXML
	private TableView<Department> departmentView;
	@FXML
	private TableColumn<Department, Integer> columnId;
	@FXML
	private TableColumn<Department, String> columnName;
	@FXML
	private Button newBt;
	@FXML
	public void onNewBtAction() {
		System.out.println("Initialize button");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage)Main.getMainScene().getWindow();
		departmentView.prefHeightProperty().bind(stage.heightProperty());
	}
	

}
