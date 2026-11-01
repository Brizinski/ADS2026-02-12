package by.it.group551002.brizinski.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Arrays;

public class MyLinkedHashSet<E> implements Set<E> {

    private static final class LinkedEntry<T> {
        T value;
        LinkedEntry<T> next;
        LinkedEntry<T> prevNode;
        LinkedEntry<T> nextNode;

        LinkedEntry(T value) {
            this.value = value;
        }
    }

    private LinkedEntry<E>[] bucketArray;
    private LinkedEntry<E> first;
    private LinkedEntry<E> last;
    private int elementCount;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        bucketArray = (LinkedEntry<E>[]) new LinkedEntry[16];
        elementCount = 0;
    }

    private int computeHash(Object obj, int capacity) {
        if (obj == null) return 0;
        return (obj.hashCode() & 0x7FFFFFFF) % capacity;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        LinkedEntry<E>[] newBucketArray = (LinkedEntry<E>[]) new LinkedEntry[bucketArray.length * 2];
        LinkedEntry<E> curr = first;

        while (curr != null) {
            int idx = computeHash(curr.value, newBucketArray.length);
            curr.next = newBucketArray[idx];
            newBucketArray[idx] = curr;
            curr = curr.nextNode;
        }
        bucketArray = newBucketArray;
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
        Arrays.fill(bucketArray, null);
        first = null;
        last = null;
        elementCount = 0;
    }

    @Override
    public boolean add(E e) {
        int idx = computeHash(e, bucketArray.length);
        LinkedEntry<E> curr = bucketArray[idx];

        while (curr != null) {
            if (Objects.equals(e, curr.value)) {
                return false;
            }
            curr = curr.next;
        }

        LinkedEntry<E> entry = new LinkedEntry<>(e);
        entry.next = bucketArray[idx];
        bucketArray[idx] = entry;

        if (last == null) {
            first = entry;
            last = entry;
        } else {
            last.nextNode = entry;
            entry.prevNode = last;
            last = entry;
        }

        elementCount++;

        if (elementCount > bucketArray.length * 0.75) {
            rehash();
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int idx = computeHash(o, bucketArray.length);
        LinkedEntry<E> curr = bucketArray[idx];
        LinkedEntry<E> prev = null;

        while (curr != null) {
            if (Objects.equals(o, curr.value)) {
                if (prev == null) {
                    bucketArray[idx] = curr.next;
                } else {
                    prev.next = curr.next;
                }

                if (curr.prevNode != null) {
                    curr.prevNode.nextNode = curr.nextNode;
                } else {
                    first = curr.nextNode;
                }

                if (curr.nextNode != null) {
                    curr.nextNode.prevNode = curr.prevNode;
                } else {
                    last = curr.prevNode;
                }

                elementCount--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        int idx = computeHash(o, bucketArray.length);
        LinkedEntry<E> curr = bucketArray[idx];

        while (curr != null) {
            if (Objects.equals(o, curr.value)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
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
        boolean changed = false;
        for (E item : c) {
            if (add(item)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object item : c) {
            if (remove(item)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        LinkedEntry<E> curr = first;
        while (curr != null) {
            LinkedEntry<E> nextNode = curr.nextNode;
            if (!c.contains(curr.value)) {
                remove(curr.value);
                changed = true;
            }
            curr = nextNode;
        }
        return changed;
    }

    @Override
    public String toString() {
        if (first == null) return "[]";

        StringBuilder sb = new StringBuilder("[");
        LinkedEntry<E> curr = first;
        while (curr != null) {
            sb.append(curr.value);
            if (curr.nextNode != null) {
                sb.append(", ");
            }
            curr = curr.nextNode;
        }
        return sb.append("]").toString();
    }

    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
}