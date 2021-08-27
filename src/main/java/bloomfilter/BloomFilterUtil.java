package bloomfilter;

import java.util.Arrays;
import java.util.Collection;

public enum BloomFilterUtil {;

    public static BloomFilterBuilder newBloomFilter() {
        return newBloomFilter(8192);
    }
    public static BloomFilterBuilder newBloomFilter(final int size) {
        final byte[] filter = new byte[size];
        Arrays.fill(filter, (byte) 0);
        return new BloomFilterBuilder(filter);
    }
    public static BloomFilterBuilder newBloomFilter(final byte[] filter) {
        return new BloomFilterBuilder(filter);
    }

    public static class BloomFilterBuilder {
        private final byte[] filter;
        public BloomFilterBuilder(final byte[] filter) {
            this.filter = filter;
        }
        public BloomFilterBuilder add(final Object o) {
            bloomFilterAdd(filter, o);
            return this;
        }
        public BloomFilterBuilder addAll(final Collection c) {
            for (final var o : c) add(o);
            return this;
        }
        public byte[] build() {
            return filter;
        }
    }

    public static boolean bloomFilterContains(final byte[] filter, final Object o) {
        final int index = Math.abs(o.hashCode()) % (filter.length * 8);
        final byte mask = (byte) ((-128 & 0xFF) >>> (index % 8));
        return (mask & filter[index / 8]) == mask;
    }

    public static void bloomFilterAdd(final byte[] filter, final Object o) {
        final int index = Math.abs(o.hashCode()) % (filter.length * 8);
        filter[index / 8] |= (byte) ((-128 & 0xFF) >>> (index % 8));
    }

    public static boolean bloomFilterIsEmpty(final byte[] filter) {
        boolean ret = false;
        for (final var bucket : filter) {
            ret |= bucket != 0;
        }
        return !ret;
    }

    public static void clear(final byte[] filter) {
        Arrays.fill(filter, (byte) 0);
    }

}
