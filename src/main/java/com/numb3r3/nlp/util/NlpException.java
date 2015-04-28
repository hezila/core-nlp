package com.numb3r3.nlp.util;
/**
 * An exception class used for handling errors related to layered sequences.
 *
 * @author afader
 *
 */
public class NlpException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NlpException(Exception cause) {
        super(cause);
    }

    public NlpException(String message, Exception cause) {
        super(message, cause);
    }

    public NlpException(String message) {
        super(message);
    }
}
