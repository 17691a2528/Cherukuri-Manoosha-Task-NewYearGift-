package com.epam.lab.model.exceptions;

@SuppressWarnings("serial")
public class XmlParseException extends GiftException {

    public XmlParseException() {
        super();
    }

    public XmlParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParseException(String message) {
        super(message);
    }

    public XmlParseException(Throwable cause) {
        super(cause);
    }

}
