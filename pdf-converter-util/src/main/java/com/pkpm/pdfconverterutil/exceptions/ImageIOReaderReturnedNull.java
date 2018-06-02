package com.pkpm.pdfconverterutil.exceptions;

/**
 *
 * @author nando
 */
@SuppressWarnings("serial")
public class ImageIOReaderReturnedNull extends RuntimeException {

    public ImageIOReaderReturnedNull(){
    }

    public ImageIOReaderReturnedNull(String msg) {
        super(msg);

    }
}
