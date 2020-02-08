package com.epam.lab.model;

import java.util.ArrayList;
import java.util.Comparator;

import com.epam.lab.model.sweets.Sweets;
import com.epam.lab.model.sweets.SweetsGenerator;

public class NewYearGift {

    private SweetsGenerator generator;

    private ArrayList<Sweets> newYearsList;

    private static SugarLevelComparator sugarComparator = new SugarLevelComparator();

    private static WeightComparator weightComparator = new WeightComparator();

    public SugarLevelComparator getSugarLevelComparator() {
        return sugarComparator;
    }

    public WeightComparator getWeightComparator() {
        return weightComparator;
    }

    public NewYearGift() {
        generator = new SweetsGenerator();
        newYearsList = new ArrayList<Sweets>();
    }

	/*
     * public void generate(int times) { for (int i = 0; i < times; i++)
	 * System.out.println(generator.next()); }
	 */

    public ArrayList<Sweets> generate(int times) {

        for (int i = 0; i < times; i++) {
            newYearsList.add(generator.next());
        }
        return newYearsList;
    }

    public static void generateNewYearGift(int numbers) {
        for (Sweets sweet : new SweetsGenerator(numbers))
            System.out.println(sweet);
    }

    private static class SugarLevelComparator implements Comparator<Sweets> {

        public int compare(Sweets sweetsOne, Sweets sweetsTwo) {
            return (sweetsOne.getSugarLevel() < sweetsTwo.getSugarLevel() ? -1
                    : (sweetsOne.getSugarLevel() == sweetsTwo.getSugarLevel() ? 0 : 1));
        }

    }

    private static class WeightComparator implements Comparator<Sweets> {

        public int compare(Sweets sweetsOne, Sweets sweetsTwo) {
            return (sweetsOne.getWeight() < sweetsTwo.getWeight() ? -1
                    : (sweetsOne.getWeight() == sweetsTwo.getWeight() ? 0 : 1));
        }

    }

	/*
	 * public static void main(String[] args) { NewYearGift gift = new
	 * NewYearGift(); gift.generate(); generateNewYearGift(10); }
	 */

}
