package com.pkpm.pdfconverterutil.exceptions;

/**
 *
 * @author nando
 */
@SuppressWarnings("serial")
public class NoSuchImageDecoder extends RuntimeException {

    public NoSuchImageDecoder() {
    }

    public NoSuchImageDecoder(String msg) {
        super(msg);
    }
}
