/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

public class Move {
    private int[] oldQ, newQ, arrow;
        
    public int[] getOldQueenPosition() {
        return oldQ;
    }

    public int[] getNewQueenPosition() {
        return newQ;
    }

    public int[] getArrowPosition() {
        return arrow;
    }

    public Move(int[] oldQ, int[] newQ, int[] arrow) {
        this.oldQ = oldQ;
        this.newQ = newQ;
        this.arrow = arrow;
    }
    
    public String toString() {
        return "Queen at [" + oldQ[0] + "," + oldQ[1] + "] to "
            + "[" + newQ[0] + "," + newQ[1] + "].\n"
            + "Arrow fired to [" + arrow[0] + "," + arrow[1] + "].\n";
    }
}
