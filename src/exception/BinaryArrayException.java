/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Anderson
 */
public class BinaryArrayException extends Exception {

    public String message;

    public BinaryArrayException() {
    }

    public BinaryArrayException(String message) {
        this.message = message;
    }

}
