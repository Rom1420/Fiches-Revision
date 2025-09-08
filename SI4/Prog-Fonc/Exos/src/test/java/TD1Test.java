import org.example.*;
import org.junit.Test;
import static org.junit.Assert.*;


public class TD1Test {
    @Test
    public void testLengthWithEmptyList() {
        Lst<Object> emptyList = null;
        assertEquals(0, TD1.length(emptyList));
    }

    @Test
    public void testLengthWithNonEmptyList() {
        Lst<Object> nonEmptyList = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        assertEquals(3, TD1.length(nonEmptyList));
    }

    @Test
    public void testMaxOne() {
        Lst<Integer> list = new Lst<>(5, null);
              assertEquals(5, TD1.max(list));
    }

    @Test
    public void testMaxFirst() {
        Lst<Integer> list = new Lst<>(10, new Lst<>(2, new Lst<>(3, null)));
        assertEquals(10, TD1.max(list));
    }

    @Test
    public void testMaxLast() {
        Lst<Integer> list = new Lst<>(10, new Lst<>(2, new Lst<>(30, null)));
        assertEquals(30, TD1.max(list));
    }

    @Test
    public void testMaxIn() {
        Lst<Integer> list = new Lst<>(10, new Lst<>(200, new Lst<>(30, null)));
        assertEquals(200, TD1.max(list));
    }

    @Test
    public void testMemberWithValueInList() {
        Lst<Object> list = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        assertTrue(TD1.member(2, list));
    }

    @Test
    public void testMemberWithValueNotInList() {
        Lst<Object> list = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        assertFalse(TD1.member(4, list));
    }

    @Test
    public void testAppendWithEmptyLists() {
        assertEquals(null, TD1.append(null, null));
    }

    @Test
    public void testAppendWithNonEmptyLists() {
        Lst<Object> list1 = new Lst<>(1, new Lst<>(2, null));
        Lst<Object> list2 = new Lst<>(3, new Lst<>(4, null));
        Lst<Object> expected = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null))));
        assertEquals(expected, TD1.append(list1, list2));
    }

    @Test
    public void testRemoveWithValueInList() {
        Lst<Object> list = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        Lst<Object> expected = new Lst<>(1, new Lst<>(3, null));
        assertEquals(expected, TD1.remove(2, list));
    }

    @Test
    public void testRemoveWithValueNotInList() {
        Lst<Object> list = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        assertEquals(list, TD1.remove(4, list));
    }

    @Test
    public void testRemoveAllWithValueInList() {
        Lst<Object> list = new Lst<>(1, new Lst<>(2, new Lst<>(1, null)));
        Lst<Object> expected = new Lst<>(2, null);
        assertEquals(expected, TD1.removeAll(1, list));
    }

    @Test
    public void testRemoveAllWithValueNotInList() {
        Lst<Object> list = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        assertEquals(list, TD1.remove(4, list));
    }

    @Test
    public void testFizzBuzzWithValidInput() {
        Lst<String> expected = new Lst<>("2", new Lst<>("Fizz", new Lst<>("4", new Lst<>("Buzz", new Lst<>("Fizz", new Lst<>("7", new Lst<>("8", new Lst<>("Fizz", new Lst<>("Buzz", new Lst<>("11", new Lst<>("Fizz", new Lst<>("13", new Lst<>("14", new Lst<>("FizzBuzz", new Lst<>("16", new Lst<>("17", new Lst<>("Fizz", new Lst<>("19", new Lst<>("Buzz", null)))))))))))))))))));
        assertEquals(expected, TD1.fizzbuzz(2, 21));
    }

    @Test
    public void testFromArrayWithEmptyArray() {
        Object[] emptyArray = new Object[0];
        assertEquals(null, TD1.fromArray(emptyArray));
    }

    @Test
    public void testFromArrayWithNonEmptyArray() {
        Object[] nonEmptyArray = new Object[] {1, 2, 3};
        Lst<Object> expected = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        assertEquals(expected, TD1.fromArray(nonEmptyArray));
    }

    @Test
    public void testReverseWithEmptyList() {
        assertEquals(null, TD1.reverse(null));
    }

    @Test
    public void testReverseWithNonEmptyList() {
        Lst<Integer> lst = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        Lst<Integer> reversedLst = TD1.reverse(lst);
        assertEquals(new Lst<>(3, new Lst<>(2, new Lst<>(1, null))), reversedLst);
    }

    @Test
    public void testInsertWithEmptyList() {
        Lst<Integer> insertedLst = TD1.insert(1, null);
        assertEquals(new Lst<>(1, null), insertedLst);
    }

    @Test
    public void testInsertWithNonEmptyList() {
        Lst<Integer> lst = new Lst<>(1, new Lst<>(2, new Lst<>(4, null)));
        Lst<Integer> insertedLst = TD1.insert(3, lst);
        assertEquals(new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null)))), insertedLst);
    }

    @Test
    public void testSortWithEmptyList() {
        assertEquals(null, TD1.sort(null));
    }

    @Test
    public void testSortWithNonEmptyList() {
        Lst<Integer> lst = new Lst<>(4, new Lst<>(1, new Lst<>(3, null)));
        Lst<Integer> insertedLst = TD1.sort(lst);
        assertEquals(new Lst<>(1, new Lst<>(3, new Lst<>(4, null))), insertedLst);
    }
}