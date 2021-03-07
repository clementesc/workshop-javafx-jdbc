package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menItemSeller;
	
	@FXML
	private MenuItem menItemDepartment;
	
	@FXML
	private MenuItem menItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		System.out.println("onMenuItemDepartmentAction");
	}
	
	@FXML
	public void onMenuItemSAboutAction() {
		System.out.println("onMenuItemSAboutAction");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

}
