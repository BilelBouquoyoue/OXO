package application;

import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Game {

    private Symbol currentPlayer = Symbol.X; // Symbole du joueur actuel
    private Button[][] buttons = new Button[3][3];
    
    public Game(Button[][] buttons) {
        this.buttons = buttons;
    }
    
    private void removePlayerClassesFromButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = buttons[row][col];
                button.getStyleClass().removeAll("playerX", "playerO");
            }
        }
    }

    public void initializeGame() {
    	
        // Réinitialiser le plateau de jeu
        for (int row = 0; row < 3; row++) {
        	for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        removePlayerClassesFromButtons();
        // Remettre le joueur initial à X
        currentPlayer = Symbol.X;
    }


    public Button getButton(int row, int col) {
        return buttons[row][col];
    }
    
    public void handleButtonClick(int row, int col, Button button) {
        if (button.getText().isEmpty()) {
            button.setText(currentPlayer.toString());

            // Vérifier s'il y a un gagnant
            if (checkWin(row, col)) {
                displayGameResult("Joueur " + currentPlayer + " a gagné !");
                initializeGame();  // Réinitialiser le jeu après la fin
            } else if (isBoardFull()) {
                initializeGame();  // Réinitialiser le jeu après la fin
            } else {
                // Passez au joueur suivant
                currentPlayer = (currentPlayer == Symbol.X) ? Symbol.O : Symbol.X;
            }
        }
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == Symbol.X) ? Symbol.O : Symbol.X;
    }

    public boolean checkWin(int row, int col) {
        String symbol = currentPlayer.toString();

        // Vérifier la ligne
        if (buttons[row][0].getText().equals(symbol) &&
                buttons[row][1].getText().equals(symbol) &&
                buttons[row][2].getText().equals(symbol)) {
            return true;
        }

        // Vérifier la colonne
        if (buttons[0][col].getText().equals(symbol) &&
                buttons[1][col].getText().equals(symbol) &&
                buttons[2][col].getText().equals(symbol)) {
            return true;
        }

        // Vérifier la diagonale principale
        if (buttons[0][0].getText().equals(symbol) &&
                buttons[1][1].getText().equals(symbol) &&
                buttons[2][2].getText().equals(symbol)) {
            return true;
        }

        // Vérifier l'autre diagonale
        if (buttons[0][2].getText().equals(symbol) &&
                buttons[1][1].getText().equals(symbol) &&
                buttons[2][0].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    public boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;  // S'il y a au moins une case vide, le plateau n'est pas plein
                }
            }
        }
        return true;  // Toutes les cases sont occupées, le plateau est plein
    }

    public void displayGameResult(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Résultat du jeu");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public Symbol getCurrentPlayer() {
        return currentPlayer;
    }
}
