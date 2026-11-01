package by.it.group551002.brizinski.lesson10;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {

    private class Node {
        E element;
        Node prev;
        Node next;

        Node(E element) {
            this.element = element;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    private void linkFirst(E element) {
        Node newNode = new Node(element);
        Node oldHead = head;

        newNode.next = oldHead;
        head = newNode;

        if (oldHead == null) {
            tail = newNode;
        } else {
            oldHead.prev = newNode;
        }

        size++;
    }

    private void linkLast(E element) {
        Node newNode = new Node(element);
        Node oldTail = tail;

        newNode.prev = oldTail;
        tail = newNode;

        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }

        size++;
    }

    private E unlink(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        E element = node.element;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        node.element = null;
        node.prev = null;
        node.next = null;

        size--;
        return element;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        if (index < size / 2) {
            Node current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

            return current;
        } else {
            Node current = tail;

            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }

            return current;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node current = head;

        while (current != null) {
            if (current != head) {
                result.append(", ");
            }

            result.append(
                    current.element == this
                            ? "(this Collection)"
                            : String.valueOf(current.element)
            );

            current = current.next;
        }

        result.append("]");
        return result.toString();
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    public E remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(Object element) {
        Node current = head;

        while (current != null) {
            if (element == null
                    ? current.element == null
                    : element.equals(current.element)) {
                unlink(current);
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public void addFirst(E element) {
        linkFirst(element);
    }

    @Override
    public void addLast(E element) {
        linkLast(element);
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        return head.element;
    }

    @Override
    public E getLast() {
        if (tail == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        return tail.element;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        return head == null ? null : unlink(head);
    }

    @Override
    public E pollLast() {
        return tail == null ? null : unlink(tail);
    }

    @Override
    public boolean offerFirst(E element) {
        addFirst(element);
        return true;
    }

    @Override
    public boolean offerLast(E element) {
        addLast(element);
        return true;
    }

    @Override
    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        return unlink(head);
    }

    @Override
    public E removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        return unlink(tail);
    }

    @Override
    public E peekFirst() {
        return head == null ? null : head.element;
    }

    @Override
    public E peekLast() {
        return tail == null ? null : tail.element;
    }

    @Override
    public boolean removeFirstOccurrence(Object element) {
        Node current = head;

        while (current != null) {
            if (element == null
                    ? current.element == null
                    : element.equals(current.element)) {
                unlink(current);
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object element) {
        Node current = tail;

        while (current != null) {
            if (element == null
                    ? current.element == null
                    : element.equals(current.element)) {
                unlink(current);
                return true;
            }

            current = current.prev;
        }

        return false;
    }

    @Override
    public boolean offer(E element) {
        addLast(element);
        return true;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean contains(Object element) {
        Node current = head;

        while (current != null) {
            if (element == null
                    ? current.element == null
                    : element.equals(current.element)) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }

                E element = current.element;
                current = current.next;
                return element;
            }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private Node current = tail;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }

                E element = current.element;
                current = current.prev;
                return element;
            }
        };
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }

        if (collection == this) {
            throw new IllegalArgumentException();
        }

        boolean changed = false;

        for (E element : collection) {
            addLast(element);
            changed = true;
        }

        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }

        boolean changed = false;
        Node current = head;

        while (current != null) {
            Node next = current.next;

            if (collection.contains(current.element)) {
                unlink(current);
                changed = true;
            }

            current = next;
        }

        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }

        boolean changed = false;
        Node current = head;

        while (current != null) {
            Node next = current.next;

            if (!collection.contains(current.element)) {
                unlink(current);
                changed = true;
            }

            current = next;
        }

        return changed;
    }

    @Override
    public void clear() {
        Node current = head;

        while (current != null) {
            Node next = current.next;
            current.element = null;
            current.prev = null;
            current.next = null;
            current = next;
        }

        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }

        for (Object element : collection) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Node current = head;

        for (int i = 0; i < size; i++) {
            result[i] = current.element;
            current = current.next;
        }

        return result;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array == null) {
            throw new NullPointerException();
        }

        T[] result = array;

        if (array.length < size) {
            result = (T[]) Array.newInstance(
                    array.getClass().getComponentType(), size);
        }

        Node current = head;

        for (int i = 0; i < size; i++) {
            result[i] = (T) current.element;
            current = current.next;
        }

        if (result.length > size) {
            result[size] = null;
        }

        return result;
    }
}
