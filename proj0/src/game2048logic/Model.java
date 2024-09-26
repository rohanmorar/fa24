package game2048logic;

import edu.princeton.cs.algs4.In;
import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;

import java.util.Formatter;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }


    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Helper fn that returns true if at least one space is equal to the tileValue passed. **/
    public boolean searchBoardForTileValue(Object expectedTileValue) {
        for (int x = 0; x < board.size(); x++) {
            for (int y = 0; y < board.size(); y++) {
                Tile currentTile = tile(x, y);
                if (currentTile == null && expectedTileValue == null) {
                    return true;
                } else if (currentTile != null && currentTile.value() ==  (Integer) expectedTileValue){
                    return true;
                }
            }
        }
        return false;
    }

    /** Helper fn that checks tiles above, below, left, and right of a currentTile.
     * 1. returns true if any adjacent tile is null and currentTile is null
     * 2. returns true if any adjacent tile's value equals the currentTile value **/
//    public boolean equalAdjacentTileValue(int x, int y, Tile currentTile) {
//        Tile tileAbove = tile(x, y+1);
//        Tile tileBelow = tile(x, y-1);
//        Tile tileLeft = tile(x-1, y);
//        Tile tileRight = tile(x+1, y);
//        int SIZE = board.size();
//        Tile topRightTile = tile(SIZE, SIZE);
//        Tile topLeftTile = tile(0, SIZE);
//        Tile botRightTile = tile(SIZE, 0);
//        Tile botLeftTile = tile(0, 0);
//
//        if (botLeftTile == null && (tile(x+1, y) == null || tile(x, y+1) == null)) {
//            return true;
//        } else if (botRightTile == null && (tile(x-1, y) == null || tile(x, y+1) == null)) {
//            return true;
//        } else if (topLeftTile == null && (tile(x, y-1) == null || tile(x+1, y) == null)) {
//            return true;
//        } else if (topRightTile == null && (tile(x, y-1) == null || tile(x-1, y) == null)) {
//            return true;
//        } else {
//            if (currentTile == null
//                    && (tileAbove == null
//                    || tileBelow == null
//                    || tileLeft == null
//                    || tileRight == null)) {
//                return true;
//            } else if (currentTile != null) {
//                return currentTile.value() == tileAbove.value()
//                        || currentTile.value() == tileBelow.value()
//                        || currentTile.value() == tileLeft.value()
//                        || currentTile.value() == tileRight.value();
//            }
//            return false;
//        }
//    }

    /** Helper fn that returns true if at least one space is equal to the tileValue passed. **/
    public boolean searchBoardForEqualAdjacentTileValues() {
        int SIZE = board.size();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Tile currentTile = tile(x, y);
//                if (equalAdjacentTileValue(x, y, currentTile)) {
//                    return true;
//                }
            }
        }
        return false;
    }

    /** Returns true if at least one space on the board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        return searchBoardForTileValue(null);
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public boolean maxTileExists() {
        return searchBoardForTileValue(MAX_PIECE);
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public boolean atLeastOneMoveExists() {
        boolean c1 = emptySpaceExists();
        boolean c2 = searchBoardForEqualAdjacentTileValues();
        return c1 || c2;
    }

    /**
     * Moves the tile at position (x, y) as far up as possible.
     *
     * Rules for Tilt:
     * 1. If two Tiles are adjacent in the direction of motion (ignoring empty space)
     *    and have the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void moveTileUpAsFarAsPossible(int x, int y) {
        Tile currTile = board.tile(x, y);
        int myValue = currTile.value();
        int targetY = y;

        // TODO: Tasks 5, 6, and 10. Fill in this function.
    }

    /** Handles the movements of the tilt in column x of board B
     * by moving every tile in the column as far up as possible.
     * The viewing perspective has already been set,
     * so we are tilting the tiles in this column up.
     * */
    public void tiltColumn(int x) {
        // TODO: Task 7. Fill in this function.
    }

    public void tilt(Side side) {
        // TODO: Tasks 8 and 9. Fill in this function.
    }

    /** Tilts every column of the board toward SIDE.
     */
    public void tiltWrapper(Side side) {
        board.resetMerged();
        tilt(side);
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
