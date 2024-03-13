module workshop_javafx {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens model.entities to javafx.base;
	 exports gui;
	 opens gui;
	 
}
