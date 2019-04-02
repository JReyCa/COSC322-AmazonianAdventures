/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

public class Main {
    public static void main(String[] args) {
     //LocalGame local = new LocalGame(Bot.DUMB, Bot.MINMAX);
       OnlineGame online1 = new OnlineGame(Bot.MINMAX, "theJMan", "wahoo", 6);
       //OnlineGame online2 = new OnlineGame(Bot.DUMB, "darkJ", "whaaat", 15);
    }
}
