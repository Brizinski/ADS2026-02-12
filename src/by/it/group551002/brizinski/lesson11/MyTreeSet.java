package by.it.group551002.brizinski.lesson11;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {

    private E[] data;
    private int elementCount;

    @SuppressWarnings("unchecked")
    public MyTreeSet() {
        data = (E[]) new Object[16];
        elementCount = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = data.length * 2;
        E[] newData = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, elementCount);
        data = newData;
    }

    @SuppressWarnings("unchecked")
    private int findPosition(Object target) {
        int low = 0;
        int high = elementCount - 1;
        Comparable<? super E> key = (Comparable<? super E>) target;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable<? super E> midVal = (Comparable<? super E>) data[mid];
            int cmp = midVal.compareTo((E) key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -(low + 1);
    }

    @Override
    public int size() {
        return elementCount;
    }

    @Override
    public boolean isEmpty() {
        return elementCount == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(data, 0, elementCount, null);
        elementCount = 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        return findPosition(o) >= 0;
    }

    @Override
    public boolean add(E e) {
        if (e == null) throw new NullPointerException("Null elements are prohibited");

        int idx = findPosition(e);
        if (idx >= 0) {
            return false;
        }

        int insertIdx = -(idx + 1);

        if (elementCount == data.length) {
            resize();
        }

        System.arraycopy(data, insertIdx, data, insertIdx + 1, elementCount - insertIdx);
        data[insertIdx] = e;
        elementCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;

        int idx = findPosition(o);
        if (idx < 0) {
            return false;
        }

        int numMoved = elementCount - idx - 1;
        if (numMoved > 0) {
            System.arraycopy(data, idx + 1, data, idx, numMoved);
        }
        data[--elementCount] = null;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E item : c) {
            if (add(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object item : c) {
            if (remove(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        int newSize = 0;

        for (int i = 0; i < elementCount; i++) {
            if (c.contains(data[i])) {
                data[newSize++] = data[i];
            } else {
                modified = true;
            }
        }

        if (modified) {
            Arrays.fill(data, newSize, elementCount, null);
            elementCount = newSize;
        }

        return modified;
    }

    @Override
    public String toString() {
        if (elementCount == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elementCount; i++) {
            sb.append(data[i]);
            if (i < elementCount - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
}
