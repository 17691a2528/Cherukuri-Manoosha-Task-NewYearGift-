package com.epam.lab.model.exceptions;

@SuppressWarnings("serial")
public class XmlNotFoundException extends GiftException {

    public XmlNotFoundException() {
        super();
    }

    public XmlNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlNotFoundException(String message) {
        super(message);
    }

}
