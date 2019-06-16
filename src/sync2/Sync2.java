/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync2;

import java.util.LinkedList;

/**
 *
 * @author eyvind
 */
public class Sync2 {

    public static void main(String[] args) {
        
        LinkedList list=new LinkedList();
        Reader r=new Reader(list);
        Writer w1=new Writer(r,"src/sync2/write1.csv",null);
        Writer w2=new Writer(r,"src/sync2/write2.csv",w1);
        r.setWriter(w1);
        r.getThread().start();
        w1.getThread().start();
        w2.getThread().start();
        
    }
    
}
