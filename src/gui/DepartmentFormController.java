package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.utils.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

	Department entity;
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
	public void onBtSaveAction() {
		System.out.println("Save");
	}
	@FXML
	public void onBtCancelAction() {
		System.out.println("Cancel");
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;
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
