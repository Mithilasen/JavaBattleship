package battleship;

import java.util.Scanner;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

class Coordinates {
    
    public Coordinates() {
    }
    
    public boolean verifyCoordinates(String coord1, String coord2) {    
        boolean isCharMatching = false;
        boolean isNumMatching = false;

        char char1 = Character.toUpperCase(coord1.charAt(0));
        char char2 = Character.toUpperCase(coord2.charAt(0));

        int int1; 
        try {
            int1 = Integer.parseInt(coord1.substring(1));
        } catch(NumberFormatException e) {
            return false;
        }

        int int2;
        try {
            int2 = Integer.parseInt(coord2.substring(1));
        } catch(NumberFormatException e) {
            return false;
        }

        if ((char1 < 'K') && (char2 < 'K') && (int1 <= 10) && (int2 <= 10)) {
            if (char1 == char2) {
                isCharMatching = true;
            } else if (int1 == int2) {
                isNumMatching = true;
            } else {
                return false;
            }
        } else {
            return false;
        }     

        return isCharMatching || isNumMatching;
    }

    public List<int[]> buildPath(String coord1, String coord2) {
        List<int[]> parts = new ArrayList<>();

        coord1 = coord1.trim().toUpperCase();
        coord2 = coord2.trim().toUpperCase();
        if (coord1.length() < 2 || coord2.length() < 2) return parts;

        char r1 = coord1.charAt(0), r2 = coord2.charAt(0);
        int c1, c2;
        try {
            c1 = Integer.parseInt(coord1.substring(1));
            c2 = Integer.parseInt(coord2.substring(1));
        } catch (NumberFormatException e) {
            return parts;
        }
        int row1 = r1 - 'A', row2 = r2 - 'A';
        int col1 = c1 - 1, col2 = c2 - 1;

        if (row1 == row2) {
            int step = (col2 >= col1) ? 1 : -1;
            for (int c = col1; ; c += step) {
                parts.add(new int[]{row1, c});
                if (c == col2) break;
            }
        } else if (col1 == col2) {
            int step = (row2 >= row1) ? 1 : -1;
            for (int r = row1; ; r += step) {
                parts.add(new int[]{r, col1});
                if (r == row2) break;
            }
        }
        return parts;
    }

    public String formatParts(List<int[]> parts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            int r = parts.get(i)[0], c = parts.get(i)[1];
            if (i > 0) sb.append(' ');
            sb.append((char)('A' + r)).append(c + 1);
        }
        return sb.toString();
    }
}