package battleship;

import java.util.Scanner;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create boards for both players
        Board player1Board = new Board();
        Board player2Board = new Board();
        Coordinates coordinates = new Coordinates();
        
        // Player 1 ship placement
        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        placeShipsForPlayer(player1Board, coordinates, scanner, 1);
        
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        clearScreen();
        
        // Player 2 ship placement  
        System.out.println("Player 2, place your ships on the game field");
        System.out.println();
        placeShipsForPlayer(player2Board, coordinates, scanner, 2);
        
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        clearScreen();
        
        // Game loop - players take turns
        boolean player1Turn = true;
        while (true) {
            if (player1Turn) {
                // Player 1's turn
                displayGameScreen(player2Board, player1Board, 1);
                System.out.println("Player 1, it's your turn:");
                System.out.println();
                
                ShotResult result = takeShot(player2Board, scanner);
                if (result == ShotResult.LAST_SHIP_SUNK) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                }
                
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
                clearScreen();
            } else {
                // Player 2's turn
                displayGameScreen(player1Board, player2Board, 2);
                System.out.println("Player 2, it's your turn:");
                System.out.println();
                
                ShotResult result = takeShot(player1Board, scanner);
                if (result == ShotResult.LAST_SHIP_SUNK) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                }
                
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
                clearScreen();
            }
            player1Turn = !player1Turn;
        }
    }
    
    private static void placeShipsForPlayer(Board board, Coordinates coordinates, Scanner scanner, int playerNum) {
        board.drawRealBoard();
        System.out.println();
        
        Ships[] ships = Ships.values();
        for (Ships ship : ships) {
            while (true) {
                System.out.printf("Enter the coordinates of the %s (%d cells):%n%n",
                        ship.getName(), ship.getCells());

                String userInput = scanner.nextLine().trim();
                String[] coords = userInput.split("\\s+");
                if (coords.length != 2) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    System.out.println();
                    continue;
                }

                if (!coordinates.verifyCoordinates(coords[0], coords[1])) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    System.out.println();
                    continue;
                }

                List<int[]> parts = coordinates.buildPath(coords[0], coords[1]);
                if (parts.isEmpty()) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    System.out.println();
                    continue;
                }

                if (parts.size() != ship.getCells()) {
                    System.out.printf("Error! Wrong length of the %s! Try again:%n%n",
                            ship.getName());
                    continue;
                }

                if (!board.canPlaceShip(parts)) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    System.out.println();
                    continue;
                }

                board.placeShip(parts, ship);
                board.drawRealBoard();
                System.out.println();
                break;
            }
        }
    }
    
    private static void displayGameScreen(Board opponentBoard, Board myBoard, int playerNum) {
        System.out.println("Player " + playerNum + "'s turn:");
        System.out.println();
        opponentBoard.drawFogBoard();
        System.out.println("---------------------");
        myBoard.drawRealBoard();
        System.out.println();
    }
    
    private static ShotResult takeShot(Board targetBoard, Scanner scanner) {
        while (true) {
            String shotCoord = scanner.nextLine().trim().toUpperCase();

            if (shotCoord.length() < 2 || shotCoord.length() > 3) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                System.out.println();
                continue;
            }

            char rowChar = shotCoord.charAt(0);
            int col;
            try {
                col = Integer.parseInt(shotCoord.substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                System.out.println();
                continue;
            }

            int row = rowChar - 'A';
            col = col - 1;

            if (row < 0 || row >= 10 || col < 0 || col >= 10) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                System.out.println();
                continue;
            }

            ShotResult result = targetBoard.processShot(row, col);
            
            switch (result) {
                case MISS:
                    System.out.println("You missed!");
                    break;
                case HIT:
                    System.out.println("You hit a ship!");
                    break;
                case SHIP_SUNK:
                    System.out.println("You sank a ship!");
                    break;
                case LAST_SHIP_SUNK:               
                    break;
            }
            
            return result;
        }
    }
    
    private static void clearScreen() {
        // screen clear 
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}