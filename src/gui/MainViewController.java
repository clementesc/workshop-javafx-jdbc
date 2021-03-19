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
		// Passa uma função como parâmetro para realizar a injeção de dependência
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemDepartmentAction() {
		// Passa uma função como parâmetro para realizar a injeção de dependência
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
			
			// Pega uma referência da cena
			Scene mainScene = Main.getMainScene();

			// Pega referência do VBox da janela principal:
			// getRoot pega o scrollPane, getContent pega todo o conteúdo, que já é um VBox, aí basta fazer o casting para VBox
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			// Pega referência do Menu (primeiro filho da VBox da janela principal)
			Node mainMenu = mainVBox.getChildren().get(0);
			
			// Limpa os filhos da mainVBox
			mainVBox.getChildren().clear();
			
			// Agora adiciona novamente o menu e depois os filhos do newVBox, que é a nova janela
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			// O método getControler vai retornar o controlador do tipo que for chamado
			// Se o tipo do parâmetro controler, passado na expressão lamda da chamada da loadView, for DeparmentListController,
			// então o tipo da variável local controler será do tipo DeparmentListController
			T controller  = loader.getController();
			
			// Executa a função que veio como parâmetro
			initializingAction.accept(controller);
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
}