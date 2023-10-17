package application;
	

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;




public class Main extends Application {

    private Game game; 
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) {
    	
        this.primaryStage = primaryStage;

        // Crée le menu principal
        VBox menuPrincipal = createMainMenu();

        // Crée une scène pour le menu principal
        Scene scene = new Scene(menuPrincipal, 800, 800);
        String cssFile = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
        
        primaryStage.setResizable(false);
        
        primaryStage.setTitle("OXO Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void displayRules() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu OXO");
        alert.setHeaderText(null);
        alert.setContentText("Les règles du jeu OXO sont simples :\n"
                + "1. Deux joueurs s'alternent pour placer leur symbole (X ou O) dans une case vide.\n"
                + "2. Le joueur qui parvient à aligner trois de ses symboles horizontalement, verticalement ou diagonalement gagne la partie.\n"
                + "3. Si toutes les cases sont remplies et aucun joueur n'a gagné, la partie est déclarée nulle.");

        alert.showAndWait();
    }
    
    private VBox createMainMenu() {
        // Crée le bouton "Nouvelle Partie"
        Button nouvellePartieButton = new Button("Nouvelle Partie");
        nouvellePartieButton.setOnAction(e -> startNewGame());
        nouvellePartieButton.getStyleClass().add("button-new-game");
        nouvellePartieButton.setOnAction(e -> startNewGame());
        
        Button reglesButton = new Button("Règles");
        reglesButton.getStyleClass().add("button-regles");
        reglesButton.setOnAction(e -> displayRules());

        // Ajoute d'autres éléments de menu si nécessaire

        // Crée un VBox pour organiser les éléments du menu
        VBox menuBox = new VBox();
        menuBox.getStyleClass().add("menu-background");
        
        menuBox.setSpacing(10);  // Espacement entre les éléments
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().add(nouvellePartieButton);
        menuBox.getChildren().add(reglesButton);
        
        
        VBox.setVgrow(menuBox, Priority.ALWAYS);
        menuBox.setMaxWidth(Double.MAX_VALUE);
        menuBox.setMaxHeight(Double.MAX_VALUE);
        

        return menuBox;
    }

    
    public void startNewGame(){
        // Création de la grille de boutons pour le plateau de jeu
        GridPane gridPane = new GridPane();
        Button[][] buttons = new Button[3][3];  // Tableau pour stocker les boutons
        
        

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setMinSize(190, 190);

                final int row = i;  // Variable finale pour i
                final int column = j;  // Variable finale pour j
               
                button.setOnAction(e -> handleButtonClick(row, column, button));
                gridPane.add(button, j, i);
                buttons[i][j] = button;  // Ajoute le bouton au tableau
            }
        }
        

        // Création de la scène et ajout de la racine
        
        HBox hBox = new HBox(gridPane);
        hBox.setAlignment(Pos.CENTER);
        
        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("play-background");
        Scene scene = new Scene(vBox, 800, 800);
        String cssFile = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(cssFile);

        primaryStage.setTitle("OXO Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.game = new Game(buttons);
        game.initializeGame();
       
    }

    

    private void handleButtonClick(int row, int col, Button button) {
        if (button.getText().isEmpty()) {
            Symbol currentPlayer = game.getCurrentPlayer();

            // Appliquer la classe CSS appropriée en fonction du joueur
            if (currentPlayer == Symbol.X) {
                button.getStyleClass().add("playerX");
            } else {
                button.getStyleClass().add("playerO");
            }

            // Mettre à jour le texte du bouton et le titre de la fenêtre
            button.setText(currentPlayer.toString());
            primaryStage.setTitle("OXO Game - Joueur " + currentPlayer);

            // Vérifier s'il y a un gagnant
            if (game.checkWin(row, col)) {
                displayGameResult("Joueur " + currentPlayer + " a gagné !");
                game.initializeGame();  // Réinitialiser le jeu après la fin
            } else if (game.isBoardFull()) {
                displayGameResult("Match nul !");
                game.initializeGame();  // Réinitialiser le jeu après la fin
            } else {
                // Passez au joueur suivant
                game.switchPlayer();
            }
        }
    }

    private void displayGameResult(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Résultat du jeu");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}