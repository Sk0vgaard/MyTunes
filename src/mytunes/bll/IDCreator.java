/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

public class IDCreator {

    private static int id = 0;

//    private static IDCreator instance;
//
//    public static IDCreator getInstance() {
//        if (instance == null) {
//            instance = new IDCreator();
//        }
//        return instance;
//    }
//
//    private IDCreator() {
//
//    }
    public static int createID() {
        id++;
        return id;
    }

}
