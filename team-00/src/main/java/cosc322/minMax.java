/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

import static cosc322.BoardModel.POS_MARKED_ARROW;
import static cosc322.BoardModel.POS_AVAILABLE;
import static cosc322.BoardModel.POS_MARKED_BLACK;
import static cosc322.BoardModel.POS_MARKED_WHITE;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import ygraphs.ai.smart_fox.games.GameModel;

/**
 *
 * @author EmilyEarl
 */
public class minMax {

    // This class will determine based on the minimax algorithm which 
    // positions belong to which player (black or white). 
    static String[][] ownedBy = new String[10][10];
    static boolean amIWhitePlayer;

    private static final int ALPHA_DEFAULT = -10000000;
    private static final int BETA_DEFAULT = 10000000;

    static int alpha = -10000000;
    static int beta = 10000000;
    static int score = -1000000;
    static int count = 0;
    static ArrayList<ArrayList<int[]>> maxMinMoves = new ArrayList<ArrayList<int[]>>();

    // we just need to find where the value is maximum and the depth is the original d provided;
    // step 1. populate the arraylist with possible moves 
    // step 2. after the min max, go through the array list at d =1, and find the best possible move 
    // step 3. actually play that move on the board. 
    public static void main(String[] args) {
        int[][] test = queenMoves(new int[]{0, 3}, new BoardModel());
        // for (int i = 0; i < test.length; i++) {
        //   for (int j = 0; j < test.length; j++) {
        //     System.out.print(" " + test[i][j]);
        //}
        //System.out.println("");
        //}
        //calculateOwnership(new BoardModel());
        //for (int i = 0; i < ownedBy.length; i++) {
        //   for (int j = 0; j < ownedBy.length; j++) {
        //      System.out.print(" " + ownedBy[i][j]);
        // }
        //System.out.println("");
        // }
        // CustomGame testing = new CustomGame("this is test"); 
        BoardModel boardTest = new BoardModel();
        minMax minMaxTest = new minMax();
        applyMinMax(3, boardTest, true, minMax.alpha, minMax.beta, true, 3);
       // System.out.println("end of code");
       // playBestMove(boardTest);

    }

    // **** THIS IS WHAT WE CALL FROM THE OUTSIDE WORLD ****
    public static Move pickMinMaxMove(BoardModel model, boolean isWhite) {
        applyMinMax(2, model, true, -100000, 100000, isWhite, 2);
        ArrayList<int[]> move = playBestMove(model, isWhite);
        for (int i = 0; i < 8; i++) {
            int[][] currentQueenArray = queenMoves(model.queenPositions.get(i), model);

            for (int x = 0; x < 10; x++) {

                for (int y = 0; y < 10; y++) {
                  //  System.out.print(currentQueenArray[x][y] + " ");
                }
              //  System.out.println();
            }
           // System.out.println();
        }
        calculateOwnership(model);
//        for (int i = 0; i < ownedBy.length; i++) {
//            for (int j = 0; j < ownedBy.length; j++) {
//                System.out.print(ownedBy[i][j].toString() + " ");
//            }
//            System.out.println();
//        }
        reset();

        return new Move(move.get(0), move.get(1), move.get(2));
    }
    // *****************************************************

    private static void reset() {
        alpha = ALPHA_DEFAULT;
        beta = BETA_DEFAULT;

        maxMinMoves.clear();
    }

    public static ArrayList<int[]> playBestMove(BoardModel model, boolean isWhite) {
        int maxVal = 0;
        ArrayList<int[]> bestMove = new ArrayList<int[]>();
////        if (maxMinMoves.size() == 0){
////           Move newMove = pickMinMaxMove(model, isWhite);
////           bestMove.add(newMove.getOldQueenPosition());
////           bestMove.add(newMove.getNewQueenPosition());
////           bestMove.add(newMove.getArrowPosition());
////        }
//        else{
        Collections.shuffle(maxMinMoves);
   
        for (int i = 0; i < maxMinMoves.size(); i++) {
            if (maxMinMoves.get(i).get(3)[0] > maxVal) {
                bestMove = maxMinMoves.get(i);
            }
        //}
        }
        System.out.println("best move size: "+bestMove.size());
//        model.makeMove(bestMove.get(0), bestMove.get(1), bestMove.get(2));
        if (maxMinMoves.size()==0){
            ArrayList<int[]> quitMove = new ArrayList<int[]>();
            quitMove.add(new int[] {-1, -1});
            quitMove.add(new int[] {-1, -1});
            quitMove.add(new int[] {-1, -1});
            
            return quitMove;
        }
        
        if( maxMinMoves.get(0).get(3)[0]==0){
             ArrayList<int[]> quitMove = new ArrayList<int[]>();
            quitMove.add(new int[] {-1, -1});
            quitMove.add(new int[] {-1, -1});
            quitMove.add(new int[] {-1, -1});
            
            return quitMove;
        }
        
        
        System.out.println(bestMove.get(0)[0] + " " + bestMove.get(0)[1] + " Old Queen");
        System.out.println(bestMove.get(1)[0] + " " + bestMove.get(1)[1] + " New Queen");
        System.out.println(bestMove.get(2)[0] + " " + bestMove.get(2)[1] + "Arrow");
        //  System.out.println();
        // printMap(model);
        //  System.out.println();

        return bestMove;
        
    }

    // need to apply the min-max algorithm to determine the best move
    // The entity to maximize and minimize respectively per turn is assumed to be the number
    // of tiles "owned" on the board. 
    // How do we do this? 
    // We need to look at all possible moves 1 step away that will maximize the number of spaces 
    // that the assigned player has on the board. We need to check each 1 step move for each 
    // same coloured queen. 
    // We need to make a graph that considers: the current positions of the queens ,the number of 
    // tiles that the same coloured queens will own if it moves to any other viable location.
    // maybe make a different version - one for each of the 4 queens? 
    // alpha is the number to maximize, this is equal to the number of spaces on the board 
    // OUR team owns. 
    // beta is the number to minimize, this is equal to the number of spaces on the board 
    // that the OTHER team owns. 
    // based on the make move method, we need to test for every single arrow shot..
    // step 1. test every single queen (NEED TO CALL STILL) 
    // step 2. test every possible 1 step move for every queen (DONE) 
    // step 3. with that queen moved, test every possible arrow (DONE) 
    // step 4. add all move pairs into a graph as a node (DONE)! 
    //      each move pair is a combination of the movement of a queen and the 
    //      placement of an arrow. We need to complete all of these steps FIRST 
    //      and THEN add all options to a graph as nodes. 
    // When we make a new node in the tree we need to add a visualization of 
    // the board if that move were to take place, as well as the location of the
    // queen we moved and the location of the arrow we placed 
    // we need to distinguish how far down in the thing we are, I suppose we can use
    // d? 
    // need to instantiate some form of an arraylist or something to hold all of the possible
    // moves and successor moves. TODO: 
    // TIP: this is techincally the NegaMax algorithm with alpha beta pruning involved. 
    public static int applyMinMax(int d, BoardModel model, boolean maximizingTeam, int alpha, int beta, boolean whiteTeam, int startDepth) {
        //base case, figure out determinaion of game over

        // printMap(model);
        // System.out.println("Depth: "+d);
       

        //allPossibleMoves moveTree = new allPossibleMoves();
        int[] currentQueen;
        ArrayList<int[]> whiteQueens = new ArrayList<>();
        ArrayList<int[]> blackQueens = new ArrayList<>();
        ArrayList<int[]> myQueens = new ArrayList<>();
        // possibleMove bestestMove = null;

//        for (int count = 0; count < model.queenPositions.size(); count++) {
//            if (model.getTile(model.queenPositions.get(count)).equals(POS_MARKED_BLACK)) {
//                blackQueens.add(model.queenPositions.get(count));
//                //  System.out.println("Black: "+model.getTile(model.queenPositions.get(count)));
//            } else {
//                whiteQueens.add(model.queenPositions.get(count));
//                // System.out.println("White: "+model.getTile(model.queenPositions.get(count)));
//            }
//        }
//set black and white queens 
        for (int jam = 0; jam < 10; jam++) {
            for (int pb = 0; pb < 10; pb++) {
                if (model.getTile(new int[]{jam, pb}).equals(POS_MARKED_WHITE)) {
                    whiteQueens.add(new int[]{jam, pb});
                }
                if (model.getTile(new int[]{jam, pb}).equals(POS_MARKED_BLACK)) {
                    blackQueens.add(new int[]{jam, pb});
                }
            }
        }
        if (maximizingTeam){
           if (whiteTeam){
              myQueens = whiteQueens;
           }
           else{
               myQueens = blackQueens;
           }
       }
        if (!maximizingTeam){
            if (whiteTeam){
                myQueens = blackQueens;
            }
            else{
                myQueens = whiteQueens;
            }
        }
    
        if (d == 0 || findAllMoves(model,myQueens).isEmpty() ) {
            return baseCase(model, whiteTeam);
        }

        //  System.out.println("White Queens identified as: " + whiteQueens.get(0)[0] + "" + whiteQueens.get(0)[1] + " " + whiteQueens.get(1)[0] + whiteQueens.get(1)[1] + " " + whiteQueens.get(2)[0] + whiteQueens.get(2)[1] + " " + whiteQueens.get(3)[0] + whiteQueens.get(3)[1]);
        //if (whiteTeam) {
        //    myQueens = whiteQueens;
        //} else {
        //   myQueens = blackQueens;
        // }
        // perform a check to see which side we are on. 
    //   System.out.println("Status: "+maximizingTeam);
        if (maximizingTeam) {
          //     System.out.println("Maximizing turn");
            if (whiteTeam) {
                myQueens = whiteQueens;
            } else {
                myQueens = blackQueens;
            }

            int maxEval = -1000000;
            // considering this move was played. 

            //   printMap(model);
            //   System.out.println();
            //find all children from this state
            ArrayList<ArrayList<int[]>> children = findAllMoves(model, myQueens);
               if (children.size() == 0){
                   return baseCase(model, whiteTeam);
               }
               if(children.size()==0 && d == startDepth){
                   System.out.println("We Lost :( ");
               }
            //    printMap(model);
            //   System.out.println();
            for (int i = 0; i < children.size(); i++) {

                String m1 = stringMap(model);

                BoardModel recursionModel = new BoardModel(10);

                for (int jam = 0; jam < 10; jam++) {
                    for (int pb = 0; pb < 10; pb++) {
                        recursionModel.setTile(new int[]{jam, pb}, model.getTile(new int[]{jam, pb}));
                    }
                }
                recursionModel.queenPositions = new ArrayList<>();
                for (int x = 0; x < model.queenPositions.size(); x++) {
                    recursionModel.queenPositions.add(model.queenPositions.get(x));
                }

                // printMap(recursionModel);
                recursionModel.moveQueen(children.get(i).get(0), children.get(i).get(1));
                recursionModel.setTile(children.get(i).get(2), POS_MARKED_ARROW);
           //     System.out.println("max recursive call");
                int childVal = applyMinMax(d - 1, recursionModel, false, alpha, beta, whiteTeam, startDepth);
                recursionModel.moveQueen(children.get(i).get(1), children.get(i).get(0));
                recursionModel.setTile(children.get(i).get(2), POS_AVAILABLE);

                String m2 = stringMap(model);
                //  System.out.println("Check board Validity Max: "+m1.equals(m2));

                maxEval = max(maxEval, childVal);
                if (d == startDepth) {
                    ArrayList<int[]> blah = new ArrayList<int[]>();
                    blah.add(children.get(i).get(0));
                    blah.add(children.get(i).get(1));
                    blah.add(children.get(i).get(2));
                    blah.add(new int[]{childVal});
                    maxMinMoves.add(blah);
                }
              alpha = max(maxEval, alpha);
            //  System.out.println("A: "+alpha);
              if (alpha >= beta) {
                  break;
               }
            }
            return maxEval;

        } else {
           //       System.out.println("Minimizing turn");
            if (!whiteTeam) {
                myQueens = whiteQueens;
            } else {
                myQueens = blackQueens;
            }

            int maxEval = 1000000;
            // considering this move was played. 

            //   printMap(model);
            //   System.out.println();
            //find all children from this state
            ArrayList<ArrayList<int[]>> children = findAllMoves(model, myQueens);
               if (children.size() == 0){
                   return baseCase(model, whiteTeam);
               }
               if(children.size()==0 && d == startDepth){
                   System.out.println("We Lost :( ");
               }
            //    printMap(model);
            //   System.out.println();
            for (int i = 0; i < children.size(); i++) {

                String m1 = stringMap(model);

                BoardModel recursionModel = new BoardModel(10);

                for (int jam = 0; jam < 10; jam++) {
                    for (int pb = 0; pb < 10; pb++) {
                        recursionModel.setTile(new int[]{jam, pb}, model.getTile(new int[]{jam, pb}));
                    }
                }
                recursionModel.queenPositions = new ArrayList<>();
                for (int x = 0; x < model.queenPositions.size(); x++) {
                    recursionModel.queenPositions.add(model.queenPositions.get(x));
                }

                // printMap(recursionModel);
                recursionModel.moveQueen(children.get(i).get(0), children.get(i).get(1));
                recursionModel.setTile(children.get(i).get(2), POS_MARKED_ARROW);
        //        System.out.println("min recursive call");
                int childVal = applyMinMax(d - 1, recursionModel, true, alpha, beta, whiteTeam, startDepth);
                recursionModel.moveQueen(children.get(i).get(1), children.get(i).get(0));
                recursionModel.setTile(children.get(i).get(2), POS_AVAILABLE);

                String m2 = stringMap(model);
                //  System.out.println("Check board Validity Max: "+m1.equals(m2));

                maxEval = min(maxEval, childVal);
                if (d == startDepth) {
                    ArrayList<int[]> blah = new ArrayList<int[]>();
                    blah.add(children.get(i).get(0));
                    blah.add(children.get(i).get(1));
                    blah.add(children.get(i).get(2));
                    blah.add(new int[]{childVal});
                    maxMinMoves.add(blah);
                }
              beta = min(maxEval, alpha);
            //  System.out.println("A: "+alpha);
              if (alpha >= beta) {
                  break;
               }
            }
            return maxEval;
        }

    }

    public static ArrayList<ArrayList<int[]>> findAllMoves(BoardModel model, ArrayList<int[]> myQueens) {

        String map = stringMap(model);
        int myCounter = 0; 
        // printMap(model);
        //   System.out.println();

        ArrayList<ArrayList<int[]>> possMoves = new ArrayList<ArrayList<int[]>>();
        ArrayList<int[]> move = new ArrayList<int[]>();
        
        possMoves.clear();

        for (int peanut = 0; peanut < myQueens.size(); peanut++) {
            
            int[] currentQueen = myQueens.get(peanut);
            int[][] moves = queenMoves(currentQueen, model);
            for(int i = 0; i < moves.length -1; i++){
                for (int j = 0; j < moves[0].length -1; j++){
                   // System.out.println(moves[i][j]);
                }
            }

            for (int i = 0; i < moves.length - 1; i++) {
                for (int j = 0; j < moves[0].length - 1; j++) {
                    if (moves[i][j] == 1) {
                        // need to move queen independent of making an arrow shot 
                        model.moveQueen(currentQueen, new int[]{i, j});
                        // step 3. test every possible arrow 
                        // arrows can move the exact same as queens, so we can use the queen moves
                        // check on the queen that has just been relocated. 
                        int[][] arrowMoves = queenMoves(new int[]{i, j}, model);
                        model.moveQueen(new int[]{i, j}, currentQueen);
                        //arrowsLoop:
                        for (int x = 0; x < arrowMoves.length - 1; x++) {
                            arrowsLoop:
                            for (int y = 0; y < arrowMoves[0].length - 1; y++) {
                                // System.out.println(x+" "+y);
                                if (arrowMoves[x][y] == 1) {
                                   // System.out.println(arrowMoves[x][y]);
                                    //move.add(currentQueen);

                                    int[] newPos = new int[]{i, j};
                                   // move.add(new int[]{i, j});

                                    int[] arrowPos = new int[]{x, y};
                                   // move.add(new int[]{x, y});

                                    Move handyMove = new Move(currentQueen, newPos, arrowPos);
                                    String validationMessage = model.validateMove(handyMove);
                                    
                                    //System.out.println(validationMessage);
                                    if (validationMessage.equalsIgnoreCase("Valid")) {
                                        //  System.out.println("found valid move");
                                        possMoves.add(new ArrayList<int[]>());
                                        possMoves.get(myCounter).add(currentQueen);
                                        possMoves.get(myCounter).add(new int[]{i,j});
                                        possMoves.get(myCounter).add(new int[]{x,y});
                                        //System.out.println("check: "+myCounter);
                                        myCounter = myCounter +1;
                                        //   System.out.println(handyMove.toString());
                                        //   System.out.println(model.getTile(currentQueen));
                                        //  System.out.println(validationMessage);
                                       // System.out.println("Possible move array "+possMoves.toString());
                                    }
                                    
                                }
                            }
                        }
                        //move Queen back

                    }
                }

            }
        }
        String map2 = stringMap(model);
        //  System.out.println("The map is unchanged by find all moves: "+map.equals(map2));
      //  System.out.println("HELLO LOOK AT ME: "+possMoves.toString());
        return possMoves;
    }

    public static void printMap(BoardModel model) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String text = new String();
                if (model.getTile(new int[]{i, j}) == POS_AVAILABLE) {
                    text = "A";
                }
                if (model.getTile(new int[]{i, j}) == POS_MARKED_WHITE) {
                    text = "W";
                }
                if (model.getTile(new int[]{i, j}) == POS_MARKED_BLACK) {
                    text = "B";
                }
                if (model.getTile(new int[]{i, j}) == POS_MARKED_ARROW) {
                    text = "R";
                }
                System.out.print(text + " ");

            }
            //     System.out.println();

        }
    }

    public static String stringMap(BoardModel model) {
        String map = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String text = new String();
                if (model.getTile(new int[]{i, j}) == POS_AVAILABLE) {
                    text = "A";
                }
                if (model.getTile(new int[]{i, j}) == POS_MARKED_WHITE) {
                    text = "W";
                }
                if (model.getTile(new int[]{i, j}) == POS_MARKED_BLACK) {
                    text = "B";
                }
                if (model.getTile(new int[]{i, j}) == POS_MARKED_ARROW) {
                    text = "R";
                }
                map = map + text + " ";

            }
        }
        return map;
    }

    static class bestMove {

        int value;
        possibleMove best;

    }

    static class allPossibleMoves {

        static ArrayList<possibleMove> allMoves;

        allPossibleMoves() {
            allMoves = new ArrayList<possibleMove>();
        }

        public static void add(possibleMove newMove) {
            allMoves.add(newMove);
        }

        public static int size() {
            return allMoves.size();
        }

        public static possibleMove get(int i) {
            return allMoves.get(i);
        }
    }

    static class possibleMove {

        int depth;
        int[] oldQueenLoc;
        int[] newQueenLoc;
        int[] arrowLoc;
        int whiteOwns;
        int blackOwns;

        possibleMove(int depth, int[] oldQueenLoc, int[] newQueenLoc, int[] arrowLoc, int white, int black) {
            this.depth = depth;
            this.oldQueenLoc = oldQueenLoc;
            this.newQueenLoc = newQueenLoc;
            this.arrowLoc = arrowLoc;
            this.whiteOwns = white;
            this.blackOwns = black;

        }
    }

    public static int baseCase(BoardModel model, boolean white) {

        calculateOwnership(model);
        int numWhite = 0;
        int numBlack = 0;
        for (int u = 0; u < 10; u++) {
            for (int v = 0; v < 10; v++) {

                if (ownedBy[u][v].equals("white")) {
                    numWhite++;
                }
                if (ownedBy[u][v].equals("black")) {
                    numBlack++;
                }
            }
        }
        if (white) {
            return numWhite;
        } else {
            return numBlack;
        }
    }

    public static void calculateScore(BoardModel model) {
        
        ArrayList<int[]> white = new ArrayList<int[]>();
        ArrayList<int[]> black = new ArrayList<int[]>();
        
        for(int i=0; i<8; i++){
            if(model.getTile(model.queenPositions.get(i)).equals(POS_MARKED_WHITE)){
                white.add(model.queenPositions.get(i));
            }
            else{
                black.add(model.queenPositions.get(i));
            }
        }
        
        int[][] qb1 = queenMoves(black.get(0), model);
        int[][] qb2 = queenMoves(black.get(1), model);
        int[][] qb3 = queenMoves(black.get(2), model);
        int[][] qb4 = queenMoves(black.get(3), model);
        int[][] qw1 = queenMoves(white.get(0), model);
        int[][] qw2 = queenMoves(white.get(1), model);
        int[][] qw3 = queenMoves(white.get(2), model);
        int[][] qw4 = queenMoves(white.get(3), model);
        for (int x = 0; x < 10; x++) {
            for(int y=0; y<10; y++){
                if ((qb1[x][y] < (qw1[x][y] & qw2[x][y] & qw3[x][y] & qw4[x][y]) & qb1[x][y] > 0) || (qb2[x][y] < (qw1[x][y] & qw2[x][y] & qw3[x][y] & qw4[x][y]) & qb2[x][y] > 0) || (qb3[x][y] < (qw1[x][y] & qw2[x][y] & qw3[x][y] & qw4[x][y]) & qb3[x][y] > 0) || (qb4[x][y] < (qw1[x][y] & qw2[x][y] & qw3[x][y] & qw4[x][y]) & qb4[x][y] > 0)){
                ownedBy[x][y].equals(POS_MARKED_BLACK);
            }
                else if (((qw1[x][y] < (qb1[x][y] & qb2[x][y] & qb3[x][y] & qb4[x][y]) & qw1[x][y] > 0) || (qw2[x][y] < (qb1[x][y] & qb2[x][y] & qb3[x][y] & qb4[x][y]) & qw2[x][y] > 0) || (qw3[x][y] < (qb1[x][y] & qb2[x][y] & qb3[x][y] & qb4[x][y]) & qw3[x][y] > 0) || (qw4[x][y] < (qb1[x][y] & qb2[x][y] & qb3[x][y] & qb4[x][y]) & qw4[x][y] > 0))){
                ownedBy[x][y].equals(POS_MARKED_WHITE);
                
                }
                else {
                    ownedBy[x][y].equals("noone");
                }
            }
        }
    }

    public static void calculateOwnership(BoardModel model) {

        // if number < current Numer and greater than zero, replace. 
        // if number == current Number and greater than zero and colours same - do nothing
        // if number == current number and greater than zero and colours different - set blank. 
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (model.getTile(r, c).equals(POS_AVAILABLE)) {
                    int smallestMoves = 100;
                    String closestQueen = "";
                    for (int i = 0; i < 8; i++) {
                        int[][] currentQueenArray = queenMoves(model.queenPositions.get(i), model);

                        if (currentQueenArray[r][c] < smallestMoves && currentQueenArray[r][c] > 0) {
                            smallestMoves = currentQueenArray[r][c];
                            closestQueen = model.getTile(model.queenPositions.get(i));
                        } else if (currentQueenArray[r][c] == smallestMoves && currentQueenArray[r][c] > 0 && !closestQueen.equals(model.getTile(model.queenPositions.get(i)))) {
                            closestQueen = "noone";
                        } else if (currentQueenArray[r][c] == smallestMoves && currentQueenArray[r][c] > 0 && closestQueen.equals(model.getTile(model.queenPositions.get(i)))) {
                            closestQueen = model.getTile(model.queenPositions.get(i));
                        }
                    }

                    ownedBy[r][c] = closestQueen;
                } 
                else {
                    ownedBy[r][c] = "????";
                    // ownedBy[r][c] = model.getTile(new int[]{r, c});
                }
            }
        }
    }

    // Queen moves will return an integer array saying how many moves it takes for that queen
    // to get to each space on the board for all numbers greater than 0. A zero on the board
    // suggests that that location is either where the queen is, or that location is blocked by 
    // either another queen or an arrow. 
    public static int[][] queenMoves(int[] currentQueen, BoardModel model) {

        int[][] moveLevel = new int[10][10];
        boolean[][] visited = new boolean[10][10];
        Queue<int[]> nodesToCheck = new LinkedList<>();
        //initialize the queen array to show the location of the queen and the 
        // number of moves it takes to get to the queen. 
        int[] initQueen = new int[3];
        initQueen[0] = currentQueen[0];
        initQueen[1] = currentQueen[1];
        initQueen[2] = 0;
        moveLevel[initQueen[0]][initQueen[1]] = initQueen[2];
        nodesToCheck.add(initQueen);
        while (!nodesToCheck.isEmpty()) {
            int[] neighbourNode = nodesToCheck.poll();
            // search nodes above until positions are no longer available; 
            for (int i = neighbourNode[0] + 1; i < 10; i++) {
                if (!visited[i][neighbourNode[1]]) {
                    if (model.getTile(i, neighbourNode[1]) == BoardModel.POS_AVAILABLE) {
                        visited[i][neighbourNode[1]] = true;
                        int[] initCurrentSpot = new int[3];
                        initCurrentSpot[0] = i;
                        initCurrentSpot[1] = neighbourNode[1];
                        initCurrentSpot[2] = neighbourNode[2] + 1;
                        moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];

                        nodesToCheck.add(initCurrentSpot);

                    } else {
                        break;

                    }
                }
            }
            // search nodes below until positions are no longer available
            for (int i = neighbourNode[0] - 1; i >= 0; i--) {
                if (!visited[i][neighbourNode[1]]) {
                    if (model.getTile(i, neighbourNode[1]) == BoardModel.POS_AVAILABLE) {
                        visited[i][neighbourNode[1]] = true;
                        int[] initCurrentSpot = new int[3];
                        initCurrentSpot[0] = i;
                        initCurrentSpot[1] = neighbourNode[1];
                        initCurrentSpot[2] = neighbourNode[2] + 1;
                        moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                        nodesToCheck.add(initCurrentSpot);
                    } else {
                        break;
                    }
                }

            }
            // search nodes to the right until positions are no longer available
            for (int i = neighbourNode[1] + 1; i < 10; i++) {
                if (!visited[neighbourNode[0]][i]) {
                    if (model.getTile(neighbourNode[0], i) == BoardModel.POS_AVAILABLE) {
                        visited[neighbourNode[0]][i] = true;
                        int[] initCurrentSpot = new int[3];
                        initCurrentSpot[0] = neighbourNode[0];
                        initCurrentSpot[1] = i;
                        initCurrentSpot[2] = neighbourNode[2] + 1;
                        moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                        nodesToCheck.add(initCurrentSpot);
                    } else {
                        break;
                    }
                }
            }

            // search nodes to the left until position are no longer available; 
            for (int i = neighbourNode[1] - 1; i >= 0; i--) {
                if (!visited[neighbourNode[0]][i]) {
                    if (model.getTile(neighbourNode[0], i) == BoardModel.POS_AVAILABLE) {
                        visited[neighbourNode[0]][i] = true;
                        int[] initCurrentSpot = new int[3];
                        initCurrentSpot[0] = neighbourNode[0];
                        initCurrentSpot[1] = i;
                        initCurrentSpot[2] = neighbourNode[2] + 1;
                        moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                        nodesToCheck.add(initCurrentSpot);
                    } else {
                        break;
                    }
                }
            }
            // search nodes diagonally up and right until positions are no longer available;
            // up == decreasing in row, right == increasing in column 
            for (int i = 1; neighbourNode[0] - i >= 0 && neighbourNode[1] + i < 10; i++) {
                if (!visited[neighbourNode[0] - i][neighbourNode[1] + i]) {
                    if (model.getTile(neighbourNode[0] - i, neighbourNode[1] + i) == BoardModel.POS_AVAILABLE) {
                        visited[neighbourNode[0] - i][neighbourNode[1] + i] = true;
                        int[] initCurrentSpot = new int[3];
                        initCurrentSpot[0] = neighbourNode[0] - i;
                        initCurrentSpot[1] = neighbourNode[1] + i;
                        initCurrentSpot[2] = neighbourNode[2] + 1;
                        moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                        nodesToCheck.add(initCurrentSpot);
                    } else {
                        break;
                    }
                }
            }

            //search nodes diagonally down and left until positions are no longer available 
            for (int i = 1; neighbourNode[0] + i < 10 && neighbourNode[1] - i >= 0; i++) {
                if (!visited[neighbourNode[0] + i][neighbourNode[1] - i]) {
                    if (model.getTile(neighbourNode[0] + i, neighbourNode[1] - i) == BoardModel.POS_AVAILABLE) {
                        visited[neighbourNode[0] + i][neighbourNode[1] - i] = true;
                        int[] initCurrentSpot = new int[3];
                        initCurrentSpot[0] = neighbourNode[0] + i;
                        initCurrentSpot[1] = neighbourNode[1] - i;
                        initCurrentSpot[2] = neighbourNode[2] + 1;
                        moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                        nodesToCheck.add(initCurrentSpot);
                    } else {
                        break;
                    }
                }
            }

            // search nodes diagonally up and left until positions are no longer available 
            for (int i = 1; neighbourNode[0] - i >= 0 && neighbourNode[1] - i >= 0; i++) {
                if (!visited[neighbourNode[0] - i][neighbourNode[1] - i]) {
                    if (model.getTile(neighbourNode[0] - i, neighbourNode[1] - i) == BoardModel.POS_AVAILABLE) {
                        visited[neighbourNode[0] - i][neighbourNode[1] - i] = true;
                        int[] initCurrentSpot = new int[3];
                        initCurrentSpot[0] = neighbourNode[0] - i;
                        initCurrentSpot[1] = neighbourNode[1] - i;
                        initCurrentSpot[2] = neighbourNode[2] + 1;
                        moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                        nodesToCheck.add(initCurrentSpot);
                    } else {
                        break;
                    }
                }
            }

            // search nodes diagonally down and right until positions are no longer available
            for (int i = 1; neighbourNode[0] + i < 10 && neighbourNode[1] + i < 10; i++) {
                if (!visited[neighbourNode[0] + i][neighbourNode[1] + i]) {
                    if (model.getTile(neighbourNode[0] + i, neighbourNode[1] + i) == BoardModel.POS_AVAILABLE) {
                        visited[neighbourNode[0] + i][neighbourNode[1] + i] = true;
                        int[] initCurrentSpot = new int[3];
                        initCurrentSpot[0] = neighbourNode[0] + i;
                        initCurrentSpot[1] = neighbourNode[1] + i;
                        initCurrentSpot[2] = neighbourNode[2] + 1;
                        moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                        nodesToCheck.add(initCurrentSpot);
                    } else {
                        break;
                    }
                }
            }

        }
        return moveLevel;
    }

}
