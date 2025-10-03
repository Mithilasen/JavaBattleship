package battleship;

import java.util.Scanner;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

class Ship {
    private Ships type;
    private List<int[]> coordinates;
    private boolean sunk;

    public Ship(Ships type, List<int[]> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
        this.sunk = false;
    }

    public boolean isSunk(char[][] board) {
        for (int[] coord : coordinates) {
            if (board[coord[0]][coord[1]] != 'X') {
                return false;
            }
        }
        return true;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public Ships getType() {
        return type;
    }
}
