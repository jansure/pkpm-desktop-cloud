/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pkpm.pdfconverterutil.exceptions;

/**
 *
 * @author obs
 */
public class NotSupportedDocumentException extends RuntimeException{
    public NotSupportedDocumentException() {}

    public NotSupportedDocumentException(String msg) {
        super(msg);
    }
}
