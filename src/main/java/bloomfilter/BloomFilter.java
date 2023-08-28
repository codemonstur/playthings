package bloomfilter;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class BloomFilter<T> implements Set<T> {

    private final byte[] buckets;

    public BloomFilter() {
        this(8192);
    }
    public BloomFilter(final int size) {
        this(new byte[size]);
        Arrays.fill(buckets, (byte) 0);
    }
    public BloomFilter(final byte[] buckets) {
        this.buckets = buckets;
    }


    @Override
    public boolean contains(final Object o) {
        final int index = Math.abs(o.hashCode()) % size();
        final byte mask = (byte) ((-128 & 0xFF) >>> (index % 8));
        return (mask & buckets[index / 8]) == mask;
    }

    @Override
    public boolean containsAll(final Collection c) {
        for (final var item : c)
            if (contains(item))
                return true;
        return false;
    }

    @Override
    public int size() {
        return buckets.length * 8;
    }

    @Override
    public boolean isEmpty() {
        boolean ret = false;
        for (final var bucket : buckets) {
            ret |= bucket != 0;
        }
        return !ret;
    }

    @Override
    public boolean add(final Object o) {
        final int index = Math.abs(o.hashCode()) % size();
        buckets[index / 8] |= (byte) ((-128 & 0xFF) >>> (index % 8));
        return true;
    }

    @Override
    public boolean addAll(final Collection c) {
        for (final var o : c) add(o);
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(buckets, (byte) 0);
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("BloomFilters don't store the actual objects");
    }
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("BloomFilters don't store the actual objects");
    }
    @Override
    public <U> U[] toArray(final U [] a) {
        throw new UnsupportedOperationException("BloomFilters don't store the actual objects");
    }
    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException("Removing from a BloomFilter is not possible");
    }
    @Override
    public boolean removeAll(final Collection c) {
        throw new UnsupportedOperationException("Removing from a BloomFilter is not possible");
    }
    @Override
    public boolean retainAll(final Collection c) {
        throw new UnsupportedOperationException("Removing from a BloomFilter is not possible");
    }

}
