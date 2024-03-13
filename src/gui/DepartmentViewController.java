package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.service.DepartmentViewService;

public class DepartmentViewController implements Initializable {

	
	private DepartmentViewService service;
	
	@FXML
	private TableView<Department> departmentView;
	@FXML
	private TableColumn<Department, Integer> columnId;
	@FXML
	private TableColumn<Department, String> columnName;
	@FXML
	private Button newBt;
	@FXML
	
	private ObservableList<Department> obsList;
	
	public void onNewBtAction() {
		System.out.println("Initialize button");
	}
	
	
	public void setDepartmentViewService(@SuppressWarnings("exports") DepartmentViewService service) {
		this.service= service;
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
	
	
	public void viewUpdate() {
		if(service == null) {
			throw new IllegalStateException("Service null");
		}
		List<Department>list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		departmentView.setItems(obsList);
	}

}
