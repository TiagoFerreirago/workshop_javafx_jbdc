package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.utils.Alerts;
import javafx.beans.value.ObservableListValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entities.Department;
import model.service.DepartmentViewService;

public class MainViewController implements Initializable {

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	
	public void onMenuItemSellerAction() {
		System.out.print("Seller");
	}
	
	public void onMenuItemDepartmentAction() {
		loadView2("/gui/DepartmentList.fxml");
	}
	
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	private synchronized void loadView(String absolutName) {
		
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(absolutName));
		VBox newVBox= loader.load();
		
		//chama a cena principal
		Scene mainScene = Main.getMainScene();
		//pegando o elemento principal, para acessar o Vbox e fazer uma atribuição aos filhos
		VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
		Node mainMenu = mainVBox.getChildren().get(0);//primeiro filho da panela principal
		mainVBox.getChildren().clear();//limpando o campo de filhos
		
		mainVBox.getChildren().add(mainMenu); // adicionando os filhos do Vbox principal
		mainVBox.getChildren().addAll(newVBox.getChildren());// adicionando a pagina de about passada no paramentro
		
		
		
		}
		catch (IOException e) {
			Alerts.showAlert("IOException" ,"Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
private synchronized void loadView2(String absolutName) {
		
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(absolutName));
		VBox newVBox= loader.load();
		
		//chama a cena principal
		Scene mainScene = Main.getMainScene();
		//pegando o elemento principal, para acessar o Vbox e fazer uma atribuição aos filhos
		VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
		Node mainMenu = mainVBox.getChildren().get(0);//primeiro filho da panela principal
		mainVBox.getChildren().clear();//limpando o campo de filhos
		
		mainVBox.getChildren().add(mainMenu); // adicionando os filhos do Vbox principal
		mainVBox.getChildren().addAll(newVBox.getChildren());// adicionando a pagina de about passada no paramentro
		
		DepartmentViewController ct = loader.getController();
		ct.setDepartmentViewService(new DepartmentViewService());
		ct.viewUpdate();
		}
		catch (IOException e) {
			Alerts.showAlert("IOException" ,"Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
