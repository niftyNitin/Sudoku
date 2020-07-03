package sudoku.computationlogic;

import sudoku.problemdomain.SudokuGame;

import java.util.stream.IntStream;

public class SudokuUtilities {
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
        IntStream.range(0, SudokuGame.GRID_BOUNDARY).forEach(
                xIndex -> System.arraycopy(
                        oldArray[xIndex],
                        0,
                        newArray[xIndex],
                        0,
                        SudokuGame.GRID_BOUNDARY
                )
        );
    }

    public static int[][] copyToNewArray(int[][] oldArray) {
        int[][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
        copySudokuArrayValues(oldArray, newArray);

        return newArray;
    }
}
