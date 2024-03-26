package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangerListener;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.service.SellerViewService;

public class SellerFormController implements Initializable {

	Seller entity;
	
	SellerViewService service;
	
	List<DataChangerListener> listChange= new ArrayList<>();
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private DatePicker dpBirthDate;
	@FXML
	private TextField txtBaseSalary;
	@FXML
	private Label lbErrorName;
	@FXML
	private Label lbErrorEmail;
	@FXML
	private Label lbErrorBirthDate;
	@FXML
	private Label lbErrorBaseSalary;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	@FXML                 
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null || service == null) {
			throw new IllegalStateException("Entity or Service was null");
		}
		try {
		entity = updateService();
		service.saveOrUpdate(entity);
		notifyDataChangeListeners();
		Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	private void notifyDataChangeListeners() {
	for (DataChangerListener listener : listChange) {
		listener.onDatachaged();
	}
		
	}
	public void subscribeDataChangeListener(DataChangerListener listener) {
		
		listChange.add(listener);
	}
	// pegando o texto do campo de cadastro e passando para entidade
	private Seller updateService() {
		
		Seller obj = new Seller();
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if(txtName.getText() == null || txtName.getText().trim().equalsIgnoreCase("")) {
			exception.addErrors("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}
	
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void setSellerViewService(SellerViewService service) {
		this.service = service;
	}
	
	public void updateData() {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}
		Locale.setDefault(Locale.US);
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if(entity.getBirthDate() != null) {
		dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		
		}
	}
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
		
	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);//aceitar so numeros inteiros
		Constraints.setTextFieldMaxLength(txtName, 70);//aceitar no max de 30 caracters
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		;
	}
	
	private void setErrorMessages(Map<String, String>errors) {
		Set<String> fields = errors.keySet();
		if(fields.contains("name")) {
			lbErrorName.setText(errors.get("name"));
		}
	}
}
