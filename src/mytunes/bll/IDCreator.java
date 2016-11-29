/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

public class IDCreator {

    private static int id = 0;

    /**
     * Increases the working id and returns the value
     *
     * @return
     */
    public static int createId() {
        id++;
        return id;
    }

}
