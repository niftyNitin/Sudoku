package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGenerator {
    public static int[][] newGameGrid() {
        return unsolveGame(getSolvedgame());
    }

    private static int[][] unsolveGame(int[][] solvedgame) {
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while (solvable == false) {
            SudokuUtilities.copySudokuArrayValues(solvedgame, solvableArray);

            int index = 0;

            while (index < 60) {
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }

                int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
                SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);

                solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
            }
        }

        return solvableArray;
    }

    private static int[][] getSolvedgame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        for (int value = 1; value <= GRID_BOUNDARY; value++) {
            int allocations = 0;
            int interrupt = 0;

            List<Coordinates> allocTracker = new ArrayList<>();

            int attempts = 0;
            while (allocations < GRID_BOUNDARY) {
                if (interrupt > 200) {
                    allocTracker.forEach(coordinates -> {
                        newGrid[coordinates.getX()][coordinates.getY()] = 0;
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500) {
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (newGrid[xCoordinate][yCoordinate] == 0) {
                    newGrid[xCoordinate][yCoordinate] = value;

                    if (GameLogic.sudokuIsInvalid(newGrid)) {
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    } else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }

            }
        }
        return newGrid;
    }

    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}