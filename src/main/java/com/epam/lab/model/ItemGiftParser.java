package com.epam.lab.model;

import com.epam.lab.model.exceptions.CreateDocumentConfigurationException;
import com.epam.lab.model.exceptions.XmlNotFoundException;
import com.epam.lab.model.exceptions.XmlParseException;
import com.epam.lab.model.sweets.Sweets;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;

public class ItemGiftParser {

    private static final Logger LOG = Logger.getLogger(ItemGiftParser.class);

    private DocumentBuilder builder;

    private XPath path;

    /**
     * Constructs a parser that can parse item lists.
     *
     * @throws CreateDocumentConfigurationException
     */
    public ItemGiftParser() throws CreateDocumentConfigurationException {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            builder = documentFactory.newDocumentBuilder();

            XPathFactory xpathFactory = XPathFactory.newInstance();
            path = xpathFactory.newXPath();
        } catch (ParserConfigurationException e) {
            throw new CreateDocumentConfigurationException("exception create new document", e);
        }
    }

    /**
     * Parses an XML file containing an item list.
     *
     * @param fileName the name of the file
     * @return an array list containing all items in the XML file
     * @throws XmlParseException
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Sweets> parse(String fileName) throws XmlParseException {

        ArrayList<Sweets> items = new ArrayList<Sweets>();
        try {
            File file = new File(fileName);
            Document doc;
            try {
                doc = builder.parse(file);
            } catch (Exception e) {
                LOG.error("xml file not found", e);
                throw new XmlNotFoundException("xml file wasn't found", e);
            }

            int itemCount = Integer.parseInt(path.evaluate("count(/gift/item)", doc));

            for (int i = 1; i <= itemCount; i++) {

                double sugar = Double.parseDouble(path.evaluate("/gift/item[" + i + "]/@sugar", doc));
                String name = path.evaluate("/gift/item[" + i + "]/name", doc);
                double weight = Double.parseDouble(path.evaluate("/gift/item[" + i
                        + "]/weight", doc));

                @SuppressWarnings("rawtypes")
                Class cl = Class.forName("com.epam.lab.model.sweets." + name);
                items.add(((Sweets) cl.getConstructor(double.class, double.class).newInstance(sugar, weight)));
            }
        } catch (Exception e) {
            LOG.error("exception with parsing xml file", e);
            throw new XmlParseException("exception with parsing xml file", e);
        }

        return items;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Sweets> parse(File file) throws XmlParseException {

        ArrayList<Sweets> items = new ArrayList<Sweets>();
        try {
            Document doc;
            try {
                doc = builder.parse(file);
            } catch (Exception ex) {
                LOG.error("xml file not found", ex);
                throw new XmlNotFoundException("xml file not found", ex);
            }

            int itemCount = Integer.parseInt(path.evaluate("count(/gift/item)", doc));

            for (int i = 1; i <= itemCount; i++) {

                double sugar;
                sugar = Double.parseDouble(path.evaluate("/gift/item[" + i + "]/@sugar", doc));
                String name = path.evaluate("/gift/item[" + i + "]/name", doc);
                double weight = Double.parseDouble(path.evaluate("/gift/item[" + i
                        + "]/weight", doc));

                // checking
                //System.out.printf("%s with %f has %f weight%n", name, sugar, weight);
                @SuppressWarnings("rawtypes")
                Class cl = Class.forName("com.epam.lab.model.sweets." + name);
                items.add(((Sweets) cl.getConstructor(double.class, double.class)
                        .newInstance(sugar, weight)));

            }
        } catch (Exception e) {
            LOG.error("exception with parsing xml file", e);
            throw new XmlParseException("exception parsing xml file", e);
        }

        return items;
    }

    // easy test
/*	public static void main(String[] args) throws Exception {

		ItemGiftParser parser = new ItemGiftParser();

		ArrayList<Sweets> items = parser.parse("./output/GiftList_1387287018363.xml");
		
		for (Sweets anItem : items)
			System.out.println(anItem.toString());
	}*/
}
