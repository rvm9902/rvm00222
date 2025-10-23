package tela_main_controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.scene.control.Label;
import java.util.Locale;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 🚀 MainLayoutController ----------------------------------------- Este controller
 * gerencia o menu lateral do sistema. Ele troca as telas exibidas no painel
 * central dinamicamente, permitindo a navegação entre Dashboard, Cadastro de
 * Colmeia, etc.
 */

public class MainLayoutController {

	

	// StackPane é um painel onde as telas são "empilhadas".
	// Aqui, ele funciona como "conteúdo principal" da aplicação.
	@FXML
	private StackPane painelConteudo;



	// Esse método é chamado automaticamente quando o FXML é carregado
	@FXML
	public void initialize() {
		
		abrirDashboard(); // Abre o dashboard logo no início

	}


	

	/**
	 * Método chamado ao clicar no botão "Dashboard". Ele carrega a tela
	 * TelaDashboard.fxml dentro do painel.
	 */
	public void abrirDashboard() {
		carregarTela("/telas/view/TelaDashboard.fxml");
	}

	/**
	 * Método chamado ao clicar no botão "Colmeias". Ele carrega a tela de cadastro
	 * de colmeia.
	 */
	public void abrirListaColmeia() {
	    carregarTela("/telas/view/TelaListaColmeia.fxml");
	}


	/**
	 * Este método centraliza o carregamento de qualquer FXML no painel de conteúdo
	 * (StackPane). Ele substitui a tela atual.
	 *
	 * @param caminho Caminho do arquivo FXML a ser carregado
	 */
	private void carregarTela(String caminho) {
		try {
			// Carrega o FXML especificado
			Node tela = FXMLLoader.load(getClass().getResource(caminho));

			tela.setOpacity(0); // invisível no início somente efeito da tela estilos

			// Substitui o conteúdo do painel pela nova tela
			painelConteudo.getChildren().setAll(tela);

			// Animação fade-in
			FadeTransition fade = new FadeTransition(Duration.millis(900), tela);
			fade.setFromValue(0);
			fade.setToValue(1);
			fade.play();

		} catch (IOException e) {
			e.printStackTrace(); // Mostra erro no console
		}
	}

	/**
	 * Método chamado ao clicar no botão "Sair". Encerra a aplicação com segurança.
	 */
	@FXML
	private void sair() {
		Platform.exit();
	}
}
