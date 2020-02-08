package com.epam.lab.model.sweets;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.Random;

public class SweetsGenerator implements Generator<Sweets>, Iterable<Sweets> {

    private static final Logger LOG = Logger.getLogger(SweetsGenerator.class);

    private static Random rand = new Random();

    private static final int SUGAR_MIN = 20;

    private static final int SUGAR_MAX = 80;

    private static final int WEIGHT_MIN = 50;

    private static final int WEIGHT_MAX = 200;

    private static final DecimalFormat PRECISION = new DecimalFormat("#.#");

    static {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        PRECISION.setDecimalFormatSymbols(dfs);
    }

    // For iteration:
    private int size = 0;

	/*	@SuppressWarnings("rawtypes")
    private Class[] types = { WhiteChocolate.class, MilkChokolate.class,
			DarkChocolate.class, DesertChocolate.class, PorousChocolate.class, };*/

    private Sweets[] instances = {new WhiteChocolate(), new MilkChokolate(),
            new DarkChocolate(), new DesertChocolate(), new PorousChocolate(),
            new Caramel(), new Chewy(), new Waffles(), new Halva(),
    };

    public SweetsGenerator() {
    }

    public SweetsGenerator(int sz) {
        size = sz;
    }

	/*
     * public Sweets next() { // Old version try { return (Sweets)
	 * types[rand.nextInt(types.length)].newInstance(); // Report programmer
	 * errors at run time: } catch (Exception e) { LOG.error("RuntimeException",
	 * e); throw new RuntimeException(e); } }
	 * 
	 * Min + (Math.random() * ((Max - Min) + 1))
	 */

    public Sweets next() {
        Sweets current = instances[rand.nextInt(instances.length)];
        double sugarParam = paramFormatter(randomSugarLevel(), PRECISION);
        double weightParam = paramFormatter(randomWeight(), PRECISION);

        try {
            return (Sweets) current.getClass()
                    .getConstructor(double.class, double.class)
                    .newInstance(sugarParam, weightParam);
            // Report programmer errors at run time:
        } catch (Exception e) {
            LOG.error("RuntimeException", e);
            throw new RuntimeException(e);
        }
    }


    private double paramFormatter(double sugarParam, DecimalFormat df) {
        return Double.parseDouble(df.format(sugarParam));
    }


    private double randomWeight() {
        return WEIGHT_MIN + (Math.random() * ((WEIGHT_MAX - WEIGHT_MIN) + 1));
    }


    private double randomSugarLevel() {
        return SUGAR_MIN + (Math.random() * ((SUGAR_MAX - SUGAR_MIN) + 1));
    }

    class SweetsIterator implements Iterator<Sweets> {
        int count = size;

        public boolean hasNext() {
            return count > 0;
        }

        public Sweets next() {
            count--;
            return SweetsGenerator.this.next();
        }

        public void remove() { // Not implemented
            LOG.error("UnsupportedOperationException");
            throw new UnsupportedOperationException();
        }
    }

    ;

    public Iterator<Sweets> iterator() {
        return new SweetsIterator();
    }

}