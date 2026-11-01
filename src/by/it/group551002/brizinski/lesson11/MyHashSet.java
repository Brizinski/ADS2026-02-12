package by.it.group551002.brizinski.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Arrays;

public class MyHashSet<E> implements Set<E> {

    private static final class Entry<T> {
        T val;
        Entry<T> next;

        Entry(T val, Entry<T> next) {
            this.val = val;
            this.next = next;
        }
    }

    private Entry<E>[] buckets;
    private int count;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        buckets = (Entry<E>[]) new Entry[16];
        count = 0;
    }

    private int calcIndex(Object key) {
        if (key == null) return 0;
        return (key.hashCode() & 0x7FFFFFFF) % buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        Entry<E>[] oldBuckets = buckets;
        buckets = (Entry<E>[]) new Entry[oldBuckets.length * 2];

        for (Entry<E> head : oldBuckets) {
            Entry<E> curr = head;
            while (curr != null) {
                int idx = calcIndex(curr.val);
                buckets[idx] = new Entry<>(curr.val, buckets[idx]);
                curr = curr.next;
            }
        }
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(buckets, null);
        count = 0;
    }

    @Override
    public boolean add(E e) {
        int idx = calcIndex(e);
        Entry<E> curr = buckets[idx];

        while (curr != null) {
            if (Objects.equals(e, curr.val)) {
                return false;
            }
            curr = curr.next;
        }

        buckets[idx] = new Entry<>(e, buckets[idx]);
        count++;

        if (count > buckets.length * 0.75) {
            grow();
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int idx = calcIndex(o);
        Entry<E> curr = buckets[idx];
        Entry<E> prev = null;

        while (curr != null) {
            if (Objects.equals(o, curr.val)) {
                if (prev == null) {
                    buckets[idx] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                count--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        int idx = calcIndex(o);
        Entry<E> curr = buckets[idx];

        while (curr != null) {
            if (Objects.equals(o, curr.val)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;

        for (Entry<E> bucket : buckets) {
            Entry<E> curr = bucket;
            while (curr != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(curr.val);
                first = false;
                curr = curr.next;
            }
        }
        return sb.append("]").toString();
    }

    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean addAll(Collection<? extends E> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
}