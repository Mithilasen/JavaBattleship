package battleship;

import java.util.Scanner;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

class Board {
    private final int size = 10;
    private char[][] realBoard = new char[size][size];
    private char[][] fogBoard = new char[size][size];
    private List<Ship> ships = new ArrayList<>();
    private int shipsSunk = 0;

    public Board() {
        initialiseBoard();
    }

    public void initialiseBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                realBoard[i][j] = '~';
                fogBoard[i][j] = '~';
            }
        }
        ships.clear();
        shipsSunk = 0;
    }

    public void drawFogBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < size; i++) {
            System.out.print((char)('A' + i));
            for (int j = 0; j < size; j++) {
                System.out.print(" " + fogBoard[i][j]);
            }
            System.out.println();
        }
    }

    public void drawRealBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < size; i++) {
            System.out.print((char)('A' + i));
            for (int j = 0; j < size; j++) {
                System.out.print(" " + realBoard[i][j]);
            }
            System.out.println();
        }
    }

    public boolean canPlaceShip(List<int[]> parts) {
        if (parts == null || parts.isEmpty()) return false;

        int n = size;
        for (int[] p : parts) {
            int r = p[0], c = p[1];
            if (r < 0 || r >= n || c < 0 || c >= n) return false;
            if (realBoard[r][c] != '~') return false;
        }

        HashSet<Integer> self = new HashSet<>();
        for (int[] p : parts) self.add(p[0]*n + p[1]);

        int[] dr = {-1,-1,-1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1,-1, 1,-1, 0, 1};

        for (int[] p : parts) {
            int r = p[0], c = p[1];
            for (int k = 0; k < 8; k++) {
                int rr = r + dr[k], cc = c + dc[k];
                if (rr >= 0 && rr < n && cc >= 0 && cc < n) {
                    int key = rr*n + cc;
                    if (!self.contains(key) && realBoard[rr][cc] == 'O') return false; 
                }
            }
        }
        return true;
    }

    public void placeShip(List<int[]> parts, Ships shipType) {
        Ship ship = new Ship(shipType, parts);
        ships.add(ship);
        for (int[] p : parts) {
            realBoard[p[0]][p[1]] = 'O';
        }
    }

    public ShotResult processShot(int row, int col) {
        char cell = realBoard[row][col];
        
        if (cell == 'O' || cell == 'X') {
            
            if (cell == 'O') {
                realBoard[row][col] = 'X';
                fogBoard[row][col] = 'X';
            }
            
            
            boolean sankShip = false;
            if (cell == 'O') {
                for (Ship ship : ships) {
                    if (!ship.isSunk() && ship.isSunk(realBoard)) {
                        ship.setSunk(true);
                        shipsSunk++;
                        sankShip = true;
                    }
                }
            }
            
            if (shipsSunk == ships.size()) {
                return ShotResult.LAST_SHIP_SUNK;
            } else if (sankShip) {
                return ShotResult.SHIP_SUNK;
            } else {
                return ShotResult.HIT;
            }
        } else if (cell == '~' || cell == 'M') {
            
            if (cell == '~') {
                realBoard[row][col] = 'M';
                fogBoard[row][col] = 'M';
            }
            return ShotResult.MISS;
        }
        return ShotResult.MISS; 
    }

    public char[][] getRealBoard() {
        return realBoard;
    }

    public char[][] getFogBoard() {
        return fogBoard;
    }
}
