package crud_colmeia;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import model.Colmeia;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ColmeiaListController {

    // =====================================
    // Elementos da tela vinculados pelo FXML
    @FXML private TableView<Colmeia> tableColmeias;
    @FXML private TableColumn<Colmeia, Long> colId;
    @FXML private TableColumn<Colmeia, String> colIdentificacao;
    @FXML private TableColumn<Colmeia, String> colLocalizacao;
    @FXML private TableColumn<Colmeia, String> colTipo;
    @FXML private TableColumn<Colmeia, String> colStatus;
    @FXML private TableColumn<Colmeia, String> colData;
    @FXML private TableColumn<Colmeia, Integer> colQuadros;

    // Lista observável para atualizar a TableView automaticamente
    private final ObservableList<Colmeia> dados = FXCollections.observableArrayList();

    // =====================================
    // Inicialização da tela
    @FXML
    public void initialize() {
        // Configura colunas da tabela
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getId()));
        colIdentificacao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getIdentificacao()));
        colLocalizacao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLocalizacao()));
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTipo()));
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        // Coluna de Data com tratamento de valor nulo
        colData.setCellValueFactory(c ->
            new javafx.beans.property.SimpleStringProperty(
                c.getValue().getDataInstalacao() != null
                    ? c.getValue().getDataInstalacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : "Não definida" // Mostra texto se data for nula
            )
        );

        colQuadros.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getNumeroQuadros()));

        // Carrega os dados do banco
        carregarColmeias();
    }

    // =====================================
    // Carrega as colmeias do banco usando DAO
    private void carregarColmeias() {
        List<Colmeia> lista = new DAO<>(Colmeia.class).obterTodos(100, 0);
        dados.setAll(lista);          // Atualiza a lista observável
        tableColmeias.setItems(dados); // Atualiza a tabela
    }

    // =====================================
    // Botão Cadastrar
    @FXML
    private void abrirCadastro() {
        try {
            Node tela = FXMLLoader.load(getClass().getResource("/telas/view/TelaCadastroColmeia.fxml"));
            StackPane painel = (StackPane) tableColmeias.getScene().lookup("#painelConteudo");
            painel.getChildren().setAll(tela); // Substitui conteúdo do painel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  // =====================================
// Botão Editar
@FXML
private void editarColmeia() {
    Colmeia selecionada = tableColmeias.getSelectionModel().getSelectedItem();
    if (selecionada == null) {
        new Alert(Alert.AlertType.WARNING, "Selecione um Cadastro para editar.").showAndWait();
        return;
    }
    
    try {
        // Carrega a tela de EDIÇÃO específica
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/view/TelaEditarColmeia.fxml"));
        Node tela = loader.load();
        
        // Obtém o controller de edição
        ColmeiaEditController controller = loader.getController();
        
        // Passa a colmeia selecionada para edição
        controller.carregarColmeia(selecionada);
        
        // Troca a tela
        StackPane painel = (StackPane) tableColmeias.getScene().lookup("#painelConteudo");
        painel.getChildren().setAll(tela);
        
    } catch (IOException e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de edição: " + e.getMessage()).showAndWait();
    }
}


    // =====================================
    // Botão Excluir
    @FXML
    private void excluirColmeia() {
        Colmeia selecionada = tableColmeias.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione uma colmeia para excluir.").showAndWait();
            return;
        }
        new DAO<>(Colmeia.class).removerPorIdTransacional(selecionada.getId()); // Remove do banco
        carregarColmeias(); // Recarrega a tabela

        
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("JIU-DOJO");
        alerta.setHeaderText("Sucesso");
        alerta.setContentText("Matricula excluida com Sucesso");
        alerta.showAndWait();


    }
}
