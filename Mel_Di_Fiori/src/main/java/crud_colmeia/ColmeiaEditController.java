package crud_colmeia;

import dao.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import model.Colmeia;

import java.io.IOException;
import java.time.LocalDate;

public class ColmeiaEditController {

    // Campos do formulário
    @FXML private TextField txtIdentificacao;
    @FXML private TextField txtLocalizacao;
    @FXML private ComboBox<String> cbTipo;
    @FXML private ComboBox<String> cbStatus;
    @FXML private DatePicker dpDataInstalacao;
    @FXML private Spinner<Integer> spNumeroQuadros;
    @FXML private Button btnAtualizar;
    @FXML private Button btnCancelar;
    @FXML private Label lblTitulo;

    private Colmeia colmeiaEmEdicao;

    @FXML
    public void initialize() {
        configurarComponentes();
        lblTitulo.setText("Editar Cadastro");
    }

    // =====================================
    // Configura os componentes do formulário
    private void configurarComponentes() {
        // Configura ComboBoxes
        cbTipo.getItems().addAll("Mensal", "Bimestral", "Trimestral", "Semestral ", "Anual");
        cbStatus.getItems().addAll("Ativa", "Inativa");
        
        // Configura Spinner
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10);
        spNumeroQuadros.setValueFactory(valueFactory);
        
        // Configura DatePicker com data atual como padrão
        dpDataInstalacao.setValue(LocalDate.now());
    }

    // =====================================
    // Carrega os dados da colmeia para edição
    public void carregarColmeia(Colmeia colmeia) {
        this.colmeiaEmEdicao = colmeia;
        preencherCamposComDados(colmeia);
    }

    // =====================================
    // Preenche os campos do formulário
    private void preencherCamposComDados(Colmeia colmeia) {
        txtIdentificacao.setText(colmeia.getIdentificacao());
        txtLocalizacao.setText(colmeia.getLocalizacao());
        cbTipo.setValue(colmeia.getTipo());
        cbStatus.setValue(colmeia.getStatus());
        dpDataInstalacao.setValue(colmeia.getDataInstalacao());
        
        // Configura o spinner com o valor atual
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, colmeia.getNumeroQuadros());
        spNumeroQuadros.setValueFactory(valueFactory);
    }

    // =====================================
    // Botão Atualizar
    @FXML
    private void atualizarColmeia() {
        try {
            if (!validarCampos()) {
                return;
            }
            
            // Atualiza os dados da colmeia
            colmeiaEmEdicao.setIdentificacao(txtIdentificacao.getText().trim());
            colmeiaEmEdicao.setLocalizacao(txtLocalizacao.getText().trim());
            colmeiaEmEdicao.setTipo(cbTipo.getValue());
            colmeiaEmEdicao.setStatus(cbStatus.getValue());
            colmeiaEmEdicao.setDataInstalacao(dpDataInstalacao.getValue());
            colmeiaEmEdicao.setNumeroQuadros(spNumeroQuadros.getValue());
            
            // Atualiza no banco de dados
            new DAO<>(Colmeia.class).atualizarTransacional(colmeiaEmEdicao);
            
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", 
                       "Colmeia Atualizada", 
                       "Colmeia atualizada com sucesso!");
            
            // Volta para a lista
            voltarParaLista();
            
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta(Alert.AlertType.ERROR, "Erro", 
                        "Erro ao Atualizar", 
                        "Ocorreu um erro ao atualizar a colmeia: " + e.getMessage());
        }
    }

    // =====================================
    // Botão Cancelar
    @FXML
    private void cancelarEdicao() {
        // Confirmação antes de cancelar
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Cancelamento");
        confirmacao.setHeaderText("Deseja cancelar a edição?");
        confirmacao.setContentText("Todas as alterações não salvas serão perdidas.");
        confirmacao.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        
        if (confirmacao.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            voltarParaLista();
        }
    }

    // =====================================
    // Volta para a lista de colmeias
    private void voltarParaLista() {
        try {
            Node tela = FXMLLoader.load(getClass().getResource("/telas/view/TelaListaColmeias.fxml"));
            StackPane painel = (StackPane) btnAtualizar.getScene().lookup("#painelConteudo");
            painel.getChildren().setAll(tela);
        } catch (IOException e) {
            e.printStackTrace();
            exibirAlerta(Alert.AlertType.ERROR, "Erro", 
                        "Erro de Navegação", 
                        "Não foi possível voltar para a lista: " + e.getMessage());
        }
    }

    // =====================================
    // Validação dos campos
    private boolean validarCampos() {
        // Valida identificação
        if (txtIdentificacao.getText() == null || txtIdentificacao.getText().trim().isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, "Validação", 
                        "Campo Obrigatório", 
                        "A identificação da colmeia é obrigatória.");
            txtIdentificacao.requestFocus();
            return false;
        }
        
        // Valida localização
        if (txtLocalizacao.getText() == null || txtLocalizacao.getText().trim().isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, "Validação", 
                        "Campo Obrigatório", 
                        "A localização da colmeia é obrigatória.");
            txtLocalizacao.requestFocus();
            return false;
        }
        
        // Valida tipo
        if (cbTipo.getValue() == null || cbTipo.getValue().isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, "Validação", 
                        "Campo Obrigatório", 
                        "O tipo da colmeia é obrigatório.");
            cbTipo.requestFocus();
            return false;
        }
        
        // Valida status
        if (cbStatus.getValue() == null || cbStatus.getValue().isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, "Validação", 
                        "Campo Obrigatório", 
                        "O status da colmeia é obrigatório.");
            cbStatus.requestFocus();
            return false;
        }
        
        // Valida data
        if (dpDataInstalacao.getValue() == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Validação", 
                        "Campo Obrigatório", 
                        "A data de instalação é obrigatória.");
            dpDataInstalacao.requestFocus();
            return false;
        }
        
        return true;
    }

    // =====================================
    // Método auxiliar para exibir alertas
    private void exibirAlerta(Alert.AlertType tipo, String titulo, String cabecalho, String conteudo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }
}