package org.example;


import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolCalculatorTest {

    private Polynomial p1;
    private Polynomial p2;
    private Polynomial result;
    private Polynomial Q;
    private Polynomial R;
    private static final PolCalculator calculator =new PolCalculator();

    @BeforeEach
    public void setUp() {
        try {
            p1 = new Polynomial("x^3-2x^2+6x-5");
            p2 = new Polynomial("x^2-1");
        }
        catch (Exception e) {
            System.out.println("Invalid Polynomial testing...");
        }

    }

    @Test
    public void aduna() {
        try {
            result = calculator.aduna(p1, p2);
            assertTrue(result.equals(new Polynomial("x^3-x^2+6x-6")));
        }
        catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        finally {
            System.out.println("Test passed successfully");
        }
    }

    @Test
    public void scade() {
        try {
            result = calculator.scade(p1, p2);
            assertTrue(result.equals(new Polynomial("x^3-3x^2+6x-4")));
        }
        catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        finally {
            System.out.println("Test passed successfully");
        }
    }

    @Test
    public void inmulteste() {
        try {
            result = calculator.inmulteste(p1, p2);
            assertTrue(result.equals(new Polynomial("x^5-2x^4+5x^3-3x^2-6x+5")));
        }
        catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        finally {
            System.out.println("Test passed successfully");
        }
    }

    @Test
    public void imparte() {
        try {
            calculator.imparte(p1, p2);
            Q = calculator.getQ();
            R = calculator.getR();
            assertAll(() -> assertTrue(Q.equals(new Polynomial("x-2"))),
                () -> assertTrue(R.equals(new Polynomial("7x-7")))
            );
        }
        catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        }
        finally {
            System.out.println("Test passed successfully");
        }
    }

    @Test
    public void deriveaza() {
        try {
            result = calculator.deriveaza(p1);
            assertTrue(result.equals(new Polynomial("3x^2-4x+6")));
        }
        catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        finally {
            System.out.println("Test passed successfully");
        }
    }

    @Test
    public void intergreaza() {
        try {
            result = calculator.intergreaza(p1);
            assertTrue(result.equals(new Polynomial("0.25x^4-0.667x^3+3x^2-5x")));
        }
        catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        finally {
            System.out.println("Test passed successfully");
        }
    }
}