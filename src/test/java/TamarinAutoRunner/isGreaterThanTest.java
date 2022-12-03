package TamarinAutoRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import TamarinAutoRunner.Node;

public class isGreaterThanTest {

    static HashSet<String> s1 = new HashSet<String>();
    static HashSet<String> s2 = new HashSet<String>();
    static HashSet<String> s3 = new HashSet<String>();

    static Node n1;
    static Node n2;
    static Node n3;

    @BeforeAll
    public static void init() {
        s1.add("Cocky");
        s1.add("Chatty");
        s1.add("ObjectForger");

        s2.add("Cocky");
        s2.add("ObjectForger");

        s3.add("ReceiptForger");
        s3.add("Cocky");

        n1 = new Node(s1, s1.size());
        n2 = new Node(s2, s2.size());
        n3 = new Node(s3, s3.size());
    }

    @Test
    public void greater_1() {
        int actual = n1.isGreaterThan(n2);
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void smaller_1() {
        int actual = n2.isGreaterThan(n1);
        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test
    public void incomparable_1() {
        int actual = n2.isGreaterThan(n3);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void incomparable_2() {
        int actual = n3.isGreaterThan(n2);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void incomparable_3() {
        int actual = n1.isGreaterThan(n3);
        int expected = 0;
        assertEquals(expected, actual);
    }

}
