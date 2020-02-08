package com.epam.lab.model;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.epam.lab.model.exceptions.CreateDocumentConfigurationException;
import com.epam.lab.model.sweets.Sweets;

public class ItemGiftBuilder {

    private DocumentBuilder builder;

    private Document doc;

    /**
     * Constructs an item list builder.
     *
     * @throws CreateDocumentConfigurationException
     */
    public ItemGiftBuilder() throws CreateDocumentConfigurationException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new CreateDocumentConfigurationException("exception create new document", e);
        }
    }

    /**
     * Builds a DOM document for an array list of items.
     *
     * @param items the items
     * @return a DOM document describing the items
     */
    public Document build(ArrayList<Sweets> items) {
        doc = builder.newDocument();
        doc.appendChild(createItems(items));
        return doc;
    }

    /**
     * Builds a DOM element for an array list of items.
     *
     * @param items the items
     * @return a DOM element describing the items
     */
    private Element createItems(ArrayList<Sweets> items) {
        Element e = doc.createElement("gift");

        for (Sweets anItem : items)
            e.appendChild(createItem(anItem));

        return e;
    }

    /**
     * Builds a DOM element for an item.
     *
     * @param anItem the item
     * @return a DOM element describing the item
     */
    private Element createItem(Sweets anItem) {
        Element e = doc.createElement("item");

        e.appendChild(createTextElement("name", "" + anItem.getClass().getSimpleName()));
        // possibly to set sugar as attribute:
        e.setAttribute("sugar", anItem.getSugarLevel() + "");
        // or as element:
        //e.appendChild(createTextElement("sugar", "" + anItem.getSugarLevel()));
        e.appendChild(createTextElement("weight", "" + anItem.getWeight()));

        return e;
    }

    /**
     * Builds the text content for document
     *
     * @param name element
     * @param text content
     * @return text element
     */
    private Element createTextElement(String name, String text) {
        Text t = doc.createTextNode(text);
        Element e = doc.createElement(name);
        e.appendChild(t);
        return e;
    }

}
