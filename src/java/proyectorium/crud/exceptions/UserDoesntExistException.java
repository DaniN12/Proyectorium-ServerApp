/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectorium.crud.exceptions;

/**
 *
 * @author 2dam
 */
public class UserDoesntExistException extends Exception {

    /**
     * Creates a new instance of <code>UserDoesntExistException</code> without
     * detail message.
     */
    public UserDoesntExistException() {
    }

    /**
     * Constructs an instance of <code>UserDoesntExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UserDoesntExistException(String msg) {
        super(msg);
    }
}
