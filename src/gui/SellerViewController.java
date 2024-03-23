package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangerListener;
import gui.utils.Alerts;
import gui.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.service.SellerViewService;

public class SellerViewController implements Initializable, DataChangerListener {

	
	private SellerViewService service;
	
	@FXML
	private TableView<Seller> departmentView;
	@FXML
	private TableColumn<Seller, Integer> columnId;
	@FXML
	private TableColumn<Seller, String> columnName;
	@FXML
	private TableColumn<Seller, String> columnEmail;
	@FXML
	private TableColumn<Seller, Date> columnBirthDate;
	@FXML
	private TableColumn<Seller, Double> columnBaseSalary;
	
	@FXML
	private Button newBt;
	@FXML
	private TableColumn<Seller,Seller> tableColumnEDIT;
	@FXML
	private TableColumn<Seller,Seller> tableColumnREMOVE;
	
	@FXML
	private ObservableList<Seller> obsList;
	@FXML
	public void onNewBtAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		//iniciaro formulario com o objeto vazio
		Seller obs = new Seller();
		createDialogForm(obs,parentStage, "/gui/SellerForm.fxml");
	}
	
	
	public void setSellerViewService(@SuppressWarnings("exports") SellerViewService service) {
		this.service= service;
	}
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(columnBirthDate, "dd/MM/yyyy");
		columnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(columnBaseSalary,2);
		Stage stage = (Stage)Main.getMainScene().getWindow();
		departmentView.prefHeightProperty().bind(stage.heightProperty());
	}
	
	
	
	public void viewUpdate() {
		if(service == null) {
			throw new IllegalStateException("Service null");
		}
		List<Seller>list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		departmentView.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}
	//Criar uma pagina de dialogo que é um stage dentro do outro
	private void createDialogForm(Seller obs, Stage parentStage, String currentView) {
	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(currentView));
			Pane pane = loader.load();//painel carregando a view
			//pegando o controle da view
			SellerFormController controll = loader.getController();
			controll.setSeller(obs);//instanciando o objeto na view controller
			controll.setSellerViewService(new SellerViewService());//instanciando o SellerViewService
			controll.subscribeDataChangeListener(this);
			controll.updateData(); //preenchendo o formulario id e name do objeto instaciado
			
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter department Data");
			//criar um cena dentro da outra com o painel como paramentro
			dialogStage.setScene(new Scene(pane));
			//a tela nao pode ser redimensionada
			dialogStage.setResizable(false);
			//definir o Stage Pai
			dialogStage.initOwner(parentStage);
			//a tela principal nao pode ser acessada por enquanto que a pagina secundaria estiver aberta
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "", currentView, null);
		}
		
	}


	@Override
	public void onDatachaged() {
		viewUpdate();
		
	}
	
	private void initEditButtons() {
		
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>(){
			private final Button button = new Button("Edit");
			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, Utils.currentStage(event), "/gui/SellerForm.fxml"));
			}
		});
	}
	
	private void initRemoveButtons() {
		
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>(){
			private final Button button= new Button("Remove");
			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
		
		
		
	}


	private void removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete? ");
		if(result.get() == ButtonType.OK) {
			if(obj == null) {
				throw new IllegalStateException("Entity was null");
			}
			try {
			service.remove(obj);
			viewUpdate();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}	
}
