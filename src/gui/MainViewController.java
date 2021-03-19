package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {
		// Passa uma fun��o como par�metro para realizar a inje��o de depend�ncia
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemDepartmentAction() {
		// Passa uma fun��o como par�metro para realizar a inje��o de depend�ncia
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			// Pega uma refer�ncia da cena
			Scene mainScene = Main.getMainScene();

			// Pega refer�ncia do VBox da janela principal:
			// getRoot pega o scrollPane, getContent pega todo o conte�do, que j� � um VBox, a� basta fazer o casting para VBox
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			// Pega refer�ncia do Menu (primeiro filho da VBox da janela principal)
			Node mainMenu = mainVBox.getChildren().get(0);
			
			// Limpa os filhos da mainVBox
			mainVBox.getChildren().clear();
			
			// Agora adiciona novamente o menu e depois os filhos do newVBox, que � a nova janela
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			// O m�todo getControler vai retornar o controlador do tipo que for chamado
			// Se o tipo do par�metro controler, passado na express�o lamda da chamada da loadView, for DeparmentListController,
			// ent�o o tipo da vari�vel local controler ser� do tipo DeparmentListController
			T controller  = loader.getController();
			
			// Executa a fun��o que veio como par�metro
			initializingAction.accept(controller);
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
}