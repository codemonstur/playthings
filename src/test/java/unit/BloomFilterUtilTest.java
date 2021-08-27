package unit;

import org.junit.Test;

import static bloomfilter.BloomFilterUtil.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BloomFilterUtilTest {

    @Test
    public void emptyTest() {
        final var bloomFilter = new byte[8192];

        assertTrue("Newly initialized bloomfilter isn't empty", bloomFilterIsEmpty(bloomFilter));
    }

    @Test
    public void containsTest() {
        final var bloomFilter = new byte[8192];
        final String simple = "Hello, world!";

        bloomFilterAdd(bloomFilter, simple);

        assertTrue("BloomFilter is missing added value", bloomFilterContains(bloomFilter, simple));
    }

    @Test
    public void containsNotTest() {
        final var bloomFilter = new byte[8192];
        final String simple = "Hello, world!";
        final String notSimple = "Hello, mars!";

        bloomFilterAdd(bloomFilter, simple);

        assertFalse(bloomFilterContains(bloomFilter, notSimple));
    }

    @Test
    public void containsCollision() {
        final var bloomFilter = new byte[8192];

        final var object1 = newObjectWithHashCode(8);
        final var object2 = newObjectWithHashCode(8);
        final var object3 = newObjectWithHashCode(8 + (bloomFilter.length * 8));
        bloomFilterAdd(bloomFilter, object1);

        assertTrue(bloomFilterContains(bloomFilter, object2));
        assertTrue(bloomFilterContains(bloomFilter, object3));
    }

    private static Object newObjectWithHashCode(final int hashCode) {
        return new Object() {
            public int hashCode() {
                return hashCode;
            }
        };
    }

}
