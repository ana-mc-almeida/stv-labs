package ap;

// === IMPORTS FROM THE FENIX CLASS PLAN === 
// import org.testng.annotations.Test;
// import org.testng.annotations.DataProvider;
// import org.testng.annotations.AfterMethod;
// import org.testng.annotations.BeforeMethod;
// import static org.testng.Assert.*;

// === IMPORTS FROM THE CLASS ===
import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class TestCalculator {

    /*
     * givenValidNameWith2ChartWhenCreatingThenCreate
     * 
     * This is a really long name for a test method, but it ok because u don't have
     * to call it directly.
     * This way, u will know specifically what this test is doing when something
     * fails
     */
    public void givenValidNameWith2ChartWhenCreatingThenCreate() {
        String name = "ab";
        Calculator calculator = new Calculator(name);
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    /*
     * You can give an name to the data provider
     * 
     * @DataProvider(name = "validNamesForConstructor")
     * If u don't give a name, the default name is the method name
     */
    @DataProvider
    private Object[][] validNamesForConstructor() {
        return new Object[][] { { "ab" }, { "abcde" }, { "abc" } };
    }

    @Test(dataProvider = "validNamesForConstructor")
    public void givenValidNameWhenCreatingThenCreate(String name) {
        Calculator calculator = new Calculator(name);
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    @DataProvider
    private Object[][] invalidNamesForConstructor() {
        return new Object[][] { { null }, { "" }, { "a" }, { "abcdef" } };
    }

    @Test(dataProvider = "invalidNamesForConstructor")
    public void givenInvalidNameWhenCreatingThenThrowException(String name) {
        try {
            new Calculator(name);
            fail("Invalid Name. Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /*
     * Other way to write the test.
     * Must be used in very limited cases.
     * One of the cases is when u are testing a constructor
     */
    @Test(dataProvider = "invalidNamesForConstructor", expectedExceptions = IllegalArgumentException.class)
    public void givenInvalidNameWhenCreatingThenThrowException_(String name) {
        new Calculator(name);
    }

    @DataProvider
    private Object[][] validValuesForSum() {
        return new Object[][] {
                { 1, 2, 3 },
                { 0, 0, 0 },
                { -2, -5, -7 },
                { 2, -5, -3 },
                { -8, 9, 1 },
                { null, 8, 8 },
                { 0, null, 0 },
                { null, null, 0 }
        };
    }

    /*
     * Let's define a global Calculator var to use an BeforeMethod
     */
    private Calculator calc;

    /*
     * The name it's not relevant but 'setUp' is the convention
     */
    @BeforeMethod
    private void setUp() {
        calc = new Calculator("test");
    }

    @Test(dataProvider = "validValuesForSum")
    public void givenValuesToAddWhenSumThenReturnsResult(Integer i1, Integer i2, Integer expectedResult) {
        Integer res = calc.sum(i1, i2);

        assertEquals(res, expectedResult);
        assertEquals(calc.getNumberOfOperations(), 1);
    }

    @DataProvider
    private Object[][] invalidValuesForDivide() {
        return new Object[][] {
                { 5, 0 },
                { 7, null },
                { null, null }
        };
    }

    @Test(dataProvider = "invalidValuesForDivide")
    public void givenInvalidValuesWhenDivideThenThrowException(Integer i1, Integer i2) {
        // Using the assertThrows we garantie that the expection it's being throwed in
        // the correct part of the code
        // This is usefull when the same exception can be throwed in multiple parts of
        // the test
        assertThrows(
                IllegalArgumentException.class,
                () -> calc.divide(i1, i2));
        assertEquals(calc.getNumberOfOperations(), 0);
    }
}