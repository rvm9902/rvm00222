package crud_colmeia;

// Importa o DAO genérico para salvar no banco
import dao.DAO;

// Importações do JavaFX (componentes gráficos)
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Colmeia;

import java.time.LocalDate;

/**
 * 🚀 ColmeiaCreateController ----------------------------------------- Este
 * controller gerencia a tela de cadastro de colmeias. Ele coleta os dados
 * preenchidos pelo usuário e os salva no banco, além de configurar os campos
 * como ComboBox e Spinner na inicialização.
 */
public class ColmeiaCreateController {

	// Campo de texto para o número da colmeia
	@FXML
	private TextField txtNumero;

	// Campo para selecionar a data de instalação
	@FXML
	private DatePicker dateInstalacao;

	// Campo de texto para localização da colmeia
	@FXML
	private TextField txtLocalizacao;

	// ComboBox para selecionar a situação da colmeia
	@FXML
	private ComboBox<String> comboSituacao;

	// ComboBox para selecionar o tipo da colmeia
	@FXML
	private ComboBox<String> comboTipo;

	// Spinner para escolher o número de quadros
	@FXML
	private Spinner<Integer> spinnerNumeroQuadros;

	// Área de texto para observações adicionais
	@FXML
	private TextArea txtObservacoes;

	/**
	 * Este método é automaticamente chamado ao carregar o FXML. Ele configura os
	 * ComboBox e o Spinner com valores padrão.
	 */
	@FXML
	public void initialize() {

		debugCampos();


		// Adiciona opções ao ComboBox de situação
		comboSituacao.getItems().addAll("Ativa", "Inativa");

		// Adiciona opções ao ComboBox de tipo
		comboTipo.getItems().addAll("Mensal", "Bimestral", "Trimestral", "semestral", "anual");

		// Configura o spinner: mínimo 1, máximo 20, valor inicial 10
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 10);
		spinnerNumeroQuadros.setValueFactory(valueFactory);
	}

	private void debugCampos() {
		System.out.println("=== DIAGNÓSTICO DE CAMPOS ===");
		System.out.println("txtNumero: " + txtNumero);
		System.out.println("txtLocalizacao: " + txtLocalizacao);
		System.out.println("dateInstalacao: " + dateInstalacao);
		System.out.println("comboSituacao: " + comboSituacao);
		System.out.println("comboTipo: " + comboTipo);
		System.out.println("spinnerNumeroQuadros: " + spinnerNumeroQuadros);
		System.out.println("txtObservacoes: " + txtObservacoes);
		System.out.println("=== FIM DIAGNÓSTICO ===");
	}

	/**
	 * Método para limpar o estilo visual de erro nos campos (remover borda
	 * vermelha).
	 */
	private void limparEstiloErro() {
		
		if (txtNumero != null) limparBordaVermelha(txtNumero);
		if (txtLocalizacao != null) limparBordaVermelha(txtLocalizacao);
		if (comboTipo != null) limparBordaVermelha(comboTipo);
		if (comboSituacao != null) limparBordaVermelha(comboSituacao);
		if (dateInstalacao != null) limparBordaVermelha(dateInstalacao);
	}

	private void colocarBordaVermelha(Control campo) {
		// ✅ ADICIONE VERIFICAÇÃO DE NULL
		if (campo != null) {
			campo.setStyle("-fx-border-color: red; -fx-border-width: 2;");
		} else {
			System.err.println("AVISO: Campo é null em colocarBordaVermelha");
		}
	}
    private void limparBordaVermelha(Control campo) {
        // ✅ ADICIONE VERIFICAÇÃO DE NULL
        if (campo != null) {
            campo.setStyle(""); // ou "-fx-border-color: null;"
        } else {
            System.err.println("AVISO: Campo é null em limparBordaVermelha");
        }
    }

	/**
	 * Validação com realce visual dos campos obrigatórios. Retorna true se todos
	 * ok, ou false se há erros.
	 */
/**
 * Validação com realce visual dos campos obrigatórios. Retorna true se todos
 * ok, ou false se há erros.
 */
/**
 * Validação com realce visual dos campos obrigatórios. Retorna true se todos
 * ok, ou false se há erros.
 */
private boolean validarCamposComVisual() {
    limparEstiloErro();

    boolean valido = true;

    // ✅✅✅ VERIFICAÇÃO CRÍTICA - SE CAMPOS SÃO NULL, PARE AQUI MESMO
    if (txtLocalizacao == null) {
        System.err.println("❌ ERRO CRÍTICO: txtLocalizacao é NULL! Abortando validação.");
        System.err.println("💡 Isso significa que o FXML não injetou os campos corretamente.");
        return false;
    }

    // ✅ Só agora valide os campos com segurança
    if (txtNumero == null || txtNumero.getText() == null || txtNumero.getText().trim().isEmpty()) {
        if (txtNumero != null) colocarBordaVermelha(txtNumero);
        valido = false;
        System.err.println("⚠️ Campo txtNumero está vazio ou null");
    }

    // ✅ AGORA ESTÁ SEGURO - txtLocalizacao NÃO É MAIS NULL
    if (txtLocalizacao.getText() == null || txtLocalizacao.getText().trim().isEmpty()) {
        colocarBordaVermelha(txtLocalizacao);
        valido = false;
        System.err.println("⚠️ Campo txtLocalizacao está vazio");
    }

    if (comboTipo == null || comboTipo.getValue() == null || comboTipo.getValue().trim().isEmpty()) {
        if (comboTipo != null) colocarBordaVermelha(comboTipo);
        valido = false;
        System.err.println("⚠️ Campo comboTipo está vazio ou null");
    }

    if (comboSituacao == null || comboSituacao.getValue() == null || comboSituacao.getValue().trim().isEmpty()) {
        if (comboSituacao != null) colocarBordaVermelha(comboSituacao);
        valido = false;
        System.err.println("⚠️ Campo comboSituacao está vazio ou null");
    }

    if (dateInstalacao == null || dateInstalacao.getValue() == null) {
        if (dateInstalacao != null) colocarBordaVermelha(dateInstalacao);
        valido = false;
        System.err.println("⚠️ Campo dateInstalacao está vazio ou null");
    }

    System.out.println("✅ Validação concluída: " + (valido ? "VÁLIDO" : "INVÁLIDO"));
    return valido;
}
	/**
	 * Método executado ao clicar no botão "Salvar". Ele coleta os dados da tela,
	 * cria uma nova colmeia e salva no banco usando o DAO.
	 */
	@FXML
	private void salvarColmeia() {
		try {
			if (!validarCamposComVisual()) {
				Alert alerta = new Alert(Alert.AlertType.WARNING);
				alerta.setTitle("Campos Obrigatórios");
				alerta.setHeaderText("Preencha os campos obrigatórios destacados em vermelho.");
				alerta.setContentText("Os campos com borda vermelha são obrigatórios e não podem ficar vazios.");
				alerta.showAndWait();
				return;
			}
		

			// continua o salvamento normalmente
			String numero = txtNumero.getText();
			LocalDate data = dateInstalacao.getValue();
			String local = txtLocalizacao.getText();
			String situacao = comboSituacao.getValue();
			String tipo = comboTipo.getValue();
			int numeroQuadros = spinnerNumeroQuadros.getValue();
			String obs = txtObservacoes.getText();

			Colmeia nova = new Colmeia(numero, local, tipo, situacao, data, numeroQuadros, obs);

			new DAO<>(Colmeia.class).incluirTransacional(nova);

			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setTitle("JIU-DOJO");
			alerta.setHeaderText("Sucesso");
			alerta.setContentText("Matricula feita com sucesso!");
			alerta.showAndWait();

			limparCampos();

		} catch (Exception e) {
			e.printStackTrace();

			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setHeaderText("Falha ao fazer matricula");
			alerta.setContentText("Erro: " + e.getMessage());
			alerta.showAndWait();
		}
	}

	/**
	 * Limpa todos os campos da tela, deixando prontos para um novo cadastro.
	 */
	@FXML
	private void limparCampos() {
		txtNumero.clear();
		dateInstalacao.setValue(null);
		txtLocalizacao.clear();
		comboSituacao.setValue(null);
		comboTipo.setValue(null);
		spinnerNumeroQuadros.getValueFactory().setValue(10);
		txtObservacoes.clear();
	}
}                           