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
}
