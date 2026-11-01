    package by.it.group551002.brizinski.lesson10;
    
    import java.lang.reflect.Array;
    import java.util.Collection;
    import java.util.Deque;
    import java.util.Iterator;
    import java.util.NoSuchElementException;
    
    public class MyArrayDeque<E> implements Deque<E> {
        int start_capacity = 16;
        private Object[] array = new Object[start_capacity];
        int head = 0, size = 0, tail = -1;
        double k = 1.5;
    
        private void reallocate(int min_size) {
            if (min_size > array.length) {
                int newCapacity = array.length == 0 ? 16 : (int) (array.length * k);
    
                if (newCapacity < min_size) {
                    newCapacity = min_size;
                }
    
                Object[] newElements = new Object[newCapacity];
    
                for (int i = 0; i < size; i++) {
                    newElements[i] = array[(head + i) % array.length];
                }
    
                array = newElements;
                head = 0;
                tail = size - 1;
            }
        }
    
        private int index(int position) {
            return (head + position) % array.length;
        }
    
        @Override
        public int size() {
            return size;
        }
    
        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append('[');
    
            for (int i = 0; i < size; ++i) {
                if (i != 0) {
                    str.append(", ");
                }
    
                Object e = array[index(i)];
                str.append(e == this ? "(this Collection)" : String.valueOf(e));
            }
    
            str.append("]");
            return str.toString();
        }
    
        @Override
        public boolean add(E e) {
            addLast(e);
            return true;
        }
    
        @Override
        public void addFirst(E e) {
            reallocate(size + 1);
    
            head = (head - 1 + array.length) % array.length;
            array[head] = e;
            size++;
    
            if (size == 1) {
                tail = head;
            }
        }
    
        @Override
        public void addLast(E e) {
            reallocate(size + 1);
    
            tail = (tail + 1 + array.length) % array.length;
            array[tail] = e;
            size++;
        }
    
        @Override
        public E element() {
            if (size == 0) {
                throw new NoSuchElementException("Deque is empty");
            }
    
            return (E) array[head];
        }
    
        @Override
        public E getFirst() {
            return element();
        }
    
        @Override
        public E getLast() {
            if (size == 0) {
                throw new NoSuchElementException("Deque is empty");
            }
    
            return (E) array[tail];
        }
    
        @Override
        public E poll() {
            return pollFirst();
        }
    
        @Override
        public E pollFirst() {
            if (size == 0) {
                return null;
            }
    
            E deleted = (E) array[head];
            array[head] = null;
            size--;
    
            if (size == 0) {
                head = 0;
                tail = -1;
            } else {
                head = (head + 1) % array.length;
            }
    
            return deleted;
        }
    
        @Override
        public E pollLast() {
            if (size == 0) {
                return null;
            }
    
            E deleted = (E) array[tail];
            array[tail] = null;
            size--;
    
            if (size == 0) {
                head = 0;
                tail = -1;
            } else {
                tail = (tail - 1 + array.length) % array.length;
            }
    
            return deleted;
        }
    
        @Override
        public boolean offerFirst(E e) {
            addFirst(e);
            return true;
        }
    
        @Override
        public boolean offerLast(E e) {
            addLast(e);
            return true;
        }
    
        @Override
        public E removeFirst() {
            if (size == 0) {
                throw new NoSuchElementException("Deque is empty");
            }
    
            return pollFirst();
        }
    
        @Override
        public E removeLast() {
            if (size == 0) {
                throw new NoSuchElementException("Deque is empty");
            }
    
            return pollLast();
        }
    
        @Override
        public E peekFirst() {
            return size == 0 ? null : (E) array[head];
        }
    
        @Override
        public E peekLast() {
            return size == 0 ? null : (E) array[tail];
        }
    
        @Override
        public boolean removeFirstOccurrence(Object o) {
            for (int i = 0; i < size; i++) {
                int currentIndex = index(i);
                Object current = array[currentIndex];
    
                if (o == null ? current == null : o.equals(current)) {
                    removeAt(i);
                    return true;
                }
            }
    
            return false;
        }
    
        @Override
        public boolean removeLastOccurrence(Object o) {
            for (int i = size - 1; i >= 0; i--) {
                int currentIndex = index(i);
                Object current = array[currentIndex];
    
                if (o == null ? current == null : o.equals(current)) {
                    removeAt(i);
                    return true;
                }
            }
    
            return false;
        }
    
        private void removeAt(int position) {
            for (int i = position; i < size - 1; i++) {
                array[index(i)] = array[index(i + 1)];
            }
    
            array[index(size - 1)] = null;
            size--;
    
            if (size == 0) {
                head = 0;
                tail = -1;
            } else {
                tail = (tail - 1 + array.length) % array.length;
            }
        }
    
        @Override
        public boolean offer(E e) {
            return offerLast(e);
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
        public void push(E e) {
            addFirst(e);
        }
    
        @Override
        public E pop() {
            return removeFirst();
        }
    
        @Override
        public boolean remove(Object o) {
            return removeFirstOccurrence(o);
        }
    
        @Override
        public boolean contains(Object o) {
            for (int i = 0; i < size; i++) {
                Object current = array[index(i)];
    
                if (o == null ? current == null : o.equals(current)) {
                    return true;
                }
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
                private int position = 0;
    
                @Override
                public boolean hasNext() {
                    return position < size;
                }
    
                @Override
                public E next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
    
                    return (E) array[index(position++)];
                }
            };
        }
    
        @Override
        public Iterator<E> descendingIterator() {
            return new Iterator<E>() {
                private int position = size - 1;
    
                @Override
                public boolean hasNext() {
                    return position >= 0;
                }
    
                @Override
                public E next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
    
                    return (E) array[index(position--)];
                }
            };
        }
    
        @Override
        public boolean addAll(Collection<? extends E> c) {
            if (c == null) {
                throw new NullPointerException();
            }
    
            if (c == this) {
                throw new IllegalArgumentException();
            }
    
            boolean changed = false;
    
            for (E e : c) {
                addLast(e);
                changed = true;
            }
    
            return changed;
        }
    
        @Override
        public boolean removeAll(Collection<?> c) {
            if (c == null) {
                throw new NullPointerException();
            }
    
            boolean changed = false;
            int i = 0;
    
            while (i < size) {
                if (c.contains(array[index(i)])) {
                    removeAt(i);
                    changed = true;
                } else {
                    i++;
                }
            }
    
            return changed;
        }
    
        @Override
        public boolean retainAll(Collection<?> c) {
            if (c == null) {
                throw new NullPointerException();
            }
    
            boolean changed = false;
            int i = 0;
    
            while (i < size) {
                if (!c.contains(array[index(i)])) {
                    removeAt(i);
                    changed = true;
                } else {
                    i++;
                }
            }
    
            return changed;
        }
    
        @Override
        public void clear() {
            for (int i = 0; i < size; i++) {
                array[index(i)] = null;
            }
    
            head = 0;
            tail = -1;
            size = 0;
        }
    
        @Override
        public boolean containsAll(Collection<?> c) {
            if (c == null) {
                throw new NullPointerException();
            }
    
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }
    
            return true;
        }
    
        @Override
        public Object[] toArray() {
            Object[] result = new Object[size];
    
            for (int i = 0; i < size; i++) {
                result[i] = array[index(i)];
            }
    
            return result;
        }
    
        @Override
        public <T> T[] toArray(T[] a) {
            if (a == null) {
                throw new NullPointerException();
            }
    
            T[] result = a;
    
            if (a.length < size) {
                result = (T[]) Array.newInstance(
                        a.getClass().getComponentType(), size);
            }
    
            for (int i = 0; i < size; i++) {
                result[i] = (T) array[index(i)];
            }
    
            if (result.length > size) {
                result[size] = null;
            }
    
            return result;
        }
    }
