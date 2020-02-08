package com.epam.lab.controller;

import com.epam.lab.model.ItemGiftBuilder;
import com.epam.lab.model.ItemGiftParser;
import com.epam.lab.model.NewYearGift;
import com.epam.lab.model.exceptions.CreateDocumentConfigurationException;
import com.epam.lab.model.sweets.Sweets;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Formatter;

public class GiftController {
    private static final Logger LOG = Logger.getLogger(GiftController.class);

    private NewYearGift newYearGift;

    private ArrayList<Sweets> items;

    private Formatter formatter;

    private ItemGiftParser giftParser;

    private double totalWeight = 0;

    private int counter = 1;

    private final static int START_OF_COUNT = 1;

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public GiftController() throws CreateDocumentConfigurationException {
        items = new ArrayList<Sweets>();
        newYearGift = new NewYearGift();
        formatter = new Formatter(System.out);
        giftParser = new ItemGiftParser();
        totalWeight = 0;
    }

    private void printTitle(String msg) {
        formatter.format("%-38s\n\n", msg);
        formatter.format("%-3s%-20s %5s %10s\n", "#", "Name", "Sugar", "Weight");
        formatter.format("%-3s%-20s %5s %10s\n", "-", "----", "-----", "------");
    }

    private void print(Sweets item) {
        formatter.format("%-3d%-20.15s %5.2f %10.2f\n", counter++, item
                .getClass().getSimpleName(), item.getSugarLevel(), item
                .getWeight());
        totalWeight += item.getWeight();
    }

    private void printTotalWeight() {
        formatter.format("%-23s %5s %10s\n", "", "", "------");
        formatter.format("%-3s%-20s %5s %10.2f\n", "", "Total Weight", "",
                totalWeight);
    }

    private void printSpace() {
        formatter.format("\n%-38s\n\n\n",
                "========================================");
    }

    private void generateGift(int nTimes) {
        items = newYearGift.generate(nTimes);

        for (Sweets sweet : items) {
            print(sweet);
        }
    }

    private void printGift() {
        for (Sweets item : items) {
            print(item);
        }
    }

    private void printGift(ArrayList<Sweets> sweets) {
        for (Sweets sweet : sweets) {
            print(sweet);
        }
    }

    public void writeToXmlFile(String xmlContent) {
        File theDir = new File("./output");
        if (!theDir.exists())
            theDir.mkdir();

        String fileName = "./output/" + this.getClass().getSimpleName() + "_"
                + Calendar.getInstance().getTimeInMillis() + ".xml";

        try (OutputStream stream = new FileOutputStream(new File(fileName))) {
            try (OutputStreamWriter out = new OutputStreamWriter(stream, StandardCharsets.UTF_16)) {
                out.write(xmlContent);
                out.write("\n");
            }
        } catch (IOException ex) {
            LOG.error("Cannot write to file!", ex);
        }
    }

    private String generateXmlContent(ItemGiftBuilder builder) {
        String content = null;

        Document doc = builder.build(items);
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");

        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);
        content = ser.writeToString(doc);

        return content;
    }

    private ArrayList<Sweets> extractSugar(double start, double end) {
        ArrayList<Sweets> exList = new ArrayList<>();
        Collections.sort(items, newYearGift.getSugarLevelComparator());

        for (Sweets sweet : items) {
            double value = sweet.getSugarLevel();
            if (value >= start && value <= end) {
                exList.add(sweet);
            }
        }

        return exList;
    }

/*	public static void main(String[] args) throws CreateDocumentConfigurationException {

		GiftController giftController = new GiftController();
		int n = 10;

		// general overwiev 
		giftController.showGiftContent(n);

		// sort by weight
		giftController.showSortedByWeight();

		// sort by sugar level
		giftController.showSortedBySugar();

		// show gift list with sugar limitation
		double start = 30, end = 60;
		giftController.showExtractedSugar(start, end);

		// write to xml file
		//giftController.writeToXmlFile();
	}*/

    public static void main(String[] args) throws CreateDocumentConfigurationException {
        GiftController giftController = new GiftController();
        giftController.printTitle("Here is title");
    }

    public void showExtractedSugar(double lowLimit, double higherLimit) {
        ArrayList<Sweets> extract = extractSugar(lowLimit, higherLimit);
        printTitle("New Year's Gift with extracted sugar\n(from "
                + lowLimit + " to " + higherLimit + "):");
        printGift(extract);
        printTotalWeight();
        printSpace();
        setCountersToStart();
    }

    public void writeToXmlFile() throws CreateDocumentConfigurationException {
        ItemGiftBuilder builder = new ItemGiftBuilder();
        String xmlContent = generateXmlContent(builder);
        writeToXmlFile(xmlContent);
    }

    public void showSortedBySugar() {
        Collections.sort(items, newYearGift.getSugarLevelComparator());
        printTitle("New Year's Gift by sorted Sugar level:");
        printGift();
        printTotalWeight();
        printSpace();
        setCountersToStart();
    }

    public void showSortedByWeight() {
        Collections.sort(items, newYearGift.getWeightComparator());
        printTitle("New Year's Gift by sorted Weigh:");
        printGift();
        printTotalWeight();
        printSpace();
        setCountersToStart();
    }

    public void showGiftContent(int nTimes) {
        printTitle("New Year's Gift (list of contents):");
        generateGift(nTimes);
        printTotalWeight();
        printSpace();
        setCountersToStart();
    }

    public void parseXmlFile(File selectedFile) {
        try {
            items = giftParser.parse(selectedFile);
        } catch (Exception e) {
            LOG.error("Error parsing file!", e);
        }
        showParsedData();
    }

    public void showParsedData() {
        printTitle("New Year's Gift (list of contents):");
        for (Sweets sweet : items) {
            print(sweet);
        }
        printTotalWeight();
        printSpace();

        // set to start
        setCountersToStart();
        // output by sorted parameters
        showSortedBySugar();
        showSortedByWeight();
    }

    private void setCountersToStart() {
        setCounter(START_OF_COUNT);
        setTotalWeight(0);
    }

}
