package unit;

import bloomfilter.BloomFilter;
import org.junit.Test;

import static org.junit.Assert.*;

public class BloomFilterTest {

    @Test
    @SuppressWarnings({"ConstantConditions", "MismatchedQueryAndUpdateOfCollection"})
    public void emptyTest() {
        final var bloomFilter = new BloomFilter<>();

        assertTrue("Newly initialized bloomfilter isn't empty", bloomFilter.isEmpty());
    }

    @Test
    public void containsTest() {
        final String simple = "Hello, world!";

        final var bloomFilter = new BloomFilter<String>();
        bloomFilter.add(simple);

        assertTrue("BloomFilter is missing added value", bloomFilter.contains(simple));
    }

    @Test
    public void containsNotTest() {
        final String simple = "Hello, world!";
        final String notSimple = "Hello, mars!";
        final var bloomFilter = new BloomFilter<String>();
        bloomFilter.add(simple);

        assertFalse(bloomFilter.contains(notSimple));
    }

    @SuppressWarnings({"ConstantConditions", "SuspiciousMethodCalls"})
    @Test
    public void containsCollision() {
        final var bloomFilter = new BloomFilter<String>();

        final var object1 = newObjectWithHashCode(8);
        final var object2 = newObjectWithHashCode(8);
        final var object3 = newObjectWithHashCode(8 + bloomFilter.size());
        bloomFilter.add(object1);

        assertTrue(bloomFilter.contains(object2));
        assertTrue(bloomFilter.contains(object3));
    }

    private static Object newObjectWithHashCode(final int hashCode) {
        return new Object() {
            public int hashCode() {
                return hashCode;
            }
        };
    }

}
