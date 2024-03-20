package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.service.DepartmentViewService;

public class DepartmentFormController implements Initializable {

	Department entity;
	
	DepartmentViewService service;
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label lbErrorName;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null || service == null) {
			throw new IllegalStateException("Entity or Service was null");
		}
		try {
		entity = updateService();
		service.saveOrUpdate(entity);
		Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	// pegando o texto do campo de cadastro e passando para entidade
	private Department updateService() {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		return obj;
	}
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentViewService(DepartmentViewService service) {
		this.service = service;
	}
	
	public void updateData() {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
		
	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);//aceitar so numeros inteiros
		Constraints.setTextFieldMaxLength(txtName, 30);//aceitar no max de 30 caracters
	}
}
