package com.pkpm.pdfconverterutil.exceptions;

/**
 *
 * @author nando
 */
@SuppressWarnings("serial")
public class NoSuchImageIOReader extends RuntimeException {

    public NoSuchImageIOReader() {
    }

    public NoSuchImageIOReader(String msg) {
        super(msg);
    }

}
