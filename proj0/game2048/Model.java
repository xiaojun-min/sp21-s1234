package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: Xiaojun Min
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;




    private boolean getMoved;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;

    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;

    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }



    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {

        boolean[][] mergedBefore = new boolean[board.size()][board.size()];


        boolean changed;
//        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
/*
        ----hardcode version 1----
        //from row 2
        for(int col = 0; col < board.size(); col++){
            Tile t = board.tile(col,2);
            //row 2 to row 3
            if(t != null && canMove(col, 3, col, 2)){
                if(board.move(col, 3, t)){
                    mergedBefore[col][3] = true;
                    getMerged = true;
                }
            }
        }

        //from row 1
        for(int col = 0; col < board.size(); col++){
            Tile t = board.tile(col,1);
            //row 1 to row 3
            if(t != null && board.tile(col, 2)==null && canMove(col, 3, col, 1)
                    && !mergedBefore[col][3]){
                if(board.move(col, 3, t)){
                    mergedBefore[col][3] = true;
                    getMerged = true;
                }
            }
            //row 1 to row 2
            else if(t != null && canMove(col, 2, col, 1)
                    && !mergedBefore[col][2]){
                if(board.move(col, 2, t)){
                    mergedBefore[col][2] = true;
                    getMerged = true;
                }
            }
        }
        //from row 0
        for(int col = 0; col < board.size(); col++){
            Tile t = board.tile(col,0);
            //row 0 to row 3
            if(t != null && board.tile(col, 2)==null && board.tile(col, 1)==null &&
                    canMove(col, 3, col, 0) && !mergedBefore[col][3]){
                if(board.move(col, 3, t)){
                    mergedBefore[col][3] = true;
                    getMerged = true;
                }
            }
            //row 0 to row 2
            else if(t != null && board.tile(col, 1)==null && canMove(col, 2, col, 0)
            &&!mergedBefore[col][2]){
                if(board.move(col, 2, t)){
                    mergedBefore[col][2] = true;
                    getMerged = true;
                }
            }
            //row 0 to row 1
            else if(t != null && canMove(col, 1, col, 0)){
                if(board.move(col, 1, t)){
                    mergedBefore[col][1] = true;
                    getMerged = true;
                }
            }
        }
*/

/*
        ----hardcode version 2----
        //from row 2
        for(int col = 0; col < board.size(); col++){
            Tile t = board.tile(col,2);
            //row 2 to row 3
            if(t != null && canMove(col, 3, col, 2)){
                if(board.move(col, 3, t)){
                    mergedBefore[col][3] = true;
                    getMerged = true;
                }
            }
        }

        //from row 1
        for(int col = 0; col < board.size(); col++){
            Tile t = board.tile(col,1);
            //row 1 to row 3
            if(t != null  && canMove(col, 3, col, 1) && !mergedBefore[col][3]){
                if(board.move(col, 3, t)){
                    mergedBefore[col][3] = true;
                    getMerged = true;
                }
            }
            //row 1 to row 2
            else if(t != null && canMove(col, 2, col, 1) && !mergedBefore[col][2]){
                if(board.move(col, 2, t)){
                    mergedBefore[col][2] = true;
                    getMerged = true;
                }
            }
        }
        //from row 0
        for(int col = 0; col < board.size(); col++){
            Tile t = board.tile(col,0);
            //row 0 to row 3
            if(t != null && canMove(col, 3, col, 0) && !mergedBefore[col][3]){
                if(board.move(col, 3, t)){
                    mergedBefore[col][3] = true;
                    getMerged = true;
                }
            }
            //row 0 to row 2
            else if(t != null && canMove(col, 2, col, 0) &&!mergedBefore[col][2]){
                if(board.move(col, 2, t)){
                    mergedBefore[col][2] = true;
                    getMerged = true;
                }
            }
            //row 0 to row 1
            else if(t != null && canMove(col, 1, col, 0)){
                if(board.move(col, 1, t)){
                    mergedBefore[col][1] = true;
                    getMerged = true;
                }
            }
        }
*/


        //move& merge according to the side
        if(side == side.NORTH){
            score += helperMove(mergedBefore);
        }
        else if(side == side.SOUTH){
            board.setViewingPerspective(Side.SOUTH);
            score += helperMove(mergedBefore);
            board.setViewingPerspective(Side.NORTH);
        }
        else if(side == side.WEST){
            board.setViewingPerspective(Side.WEST);
            score += helperMove(mergedBefore);
            board.setViewingPerspective(Side.NORTH);
        }
        else if(side == side.EAST){
            board.setViewingPerspective(Side.EAST);
            score += helperMove(mergedBefore);
            board.setViewingPerspective(Side.NORTH);

        }





        for(int i = 0; i < board.size(); i++){
            for(int j = 0; j < board.size(); j++){
                mergedBefore[i][j] = false;
            }
        }




//        changed = true;
        changed = getMoved;
        checkGameOver();

        if (changed) {
            setChanged();
        }

        return changed;
    }

    public boolean canMove(int i, int j, int oldi, int oldj) {
        int size = board.size();
        if(i < 0 || i >= size || j < 0 || j >= size ||
                board.tile(i, j)!=null && board.tile(i, j).value() != board.tile(oldi, oldj).value()) return false;
        //check if it can go up
        for(int k = oldj+1; k < j; k++){
            if(board.tile(i, k) != null) return false;
        }

        return true;
    }

    //generic moving &merging adapted from hardcoded version 2
    public int helperMove(boolean[][] mergedBefore) {
        int sc = 0;
        for(int col = 0; col < board.size(); col++){
            for(int row = board.size()-2; row >= 0; row--){
                Tile t = board.tile(col, row);
                for(int upToRow = board.size()-1; upToRow > row; upToRow--){
                    if(t != null && canMove(col, upToRow, col, row) && (upToRow == row+1 || !mergedBefore[col][upToRow])) {
                        getMoved = true;
                        if (board.move(col, upToRow, t)) {
                            mergedBefore[col][upToRow] = true;

                            sc += board.tile(col, upToRow).value();
                        }
                        break;
                    }

                }
            }
        }
        return sc;
    }


    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for(int i = 0; i < b.size(); i++){
            for(int j = 0; j < b.size(); j++){
                if(b.tile(i, j)==null) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j)!= null && b.tile(i, j).value() == MAX_PIECE) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        //check at least one empty space on the board.
        if (emptySpaceExists(b)) return true;
        //check there are two adjacent tiles with the same value.
        for (int i = 0; i < b.size(); i++){
            for (int j = 0; j < b.size(); j++){
                if(isValidLoc(b, i, j-1) && b.tile(i, j-1).value() == b.tile(i, j).value()){
                    return true;
                }
                if(isValidLoc(b, i-1, j) && b.tile(i-1, j).value() == b.tile(i, j).value()){
                    return true;
                }
                if(isValidLoc(b, i, j+1) && b.tile(i, j+1).value() == b.tile(i, j).value()){
                    return true;
                }
                if(isValidLoc(b, i+1, j) && b.tile(i+1, j).value() == b.tile(i, j).value()){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidLoc(Board b, int i, int j) {
        int size = b.size();
        if(i < 0 || i >= size || j < 0 || j >= size) return false;
        return true;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
