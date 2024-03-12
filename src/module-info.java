module workshop_javafx {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	
	 exports gui;
	 opens gui;
}
