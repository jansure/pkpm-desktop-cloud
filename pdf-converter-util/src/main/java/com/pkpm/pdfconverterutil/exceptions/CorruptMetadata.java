package com.pkpm.pdfconverterutil.exceptions;

@SuppressWarnings("serial")
public class CorruptMetadata extends RuntimeException {

    public CorruptMetadata() {
    }

    public CorruptMetadata(String msg) {
        super(msg);
    }

}
