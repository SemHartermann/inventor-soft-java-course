package org.example;

import java.util.*;
import java.util.function.Function;

public class Range<T extends Comparable<T>> implements Set<T> {

    private T start;
    private T end;
    private Function<T, T> func;
    private Set<T> additionalElements;
    private Set<T> removedElements;

    private Range(T start, T end, Function<T, T> func) {
        this.start = start;
        this.end = end;
        this.func = func;
        this.additionalElements = new HashSet<>();
        this.removedElements = new HashSet<>();
    }

    public static <E extends Comparable<E>> Range<E> of(E start, E end) {

        Function<E, E> function;

        if (start instanceof Number && !(start instanceof Float) && !(start instanceof Double)) {
            function = e -> (E) Integer.valueOf((int) ((Number) e).longValue() + 1);
        } else {
            function = e -> (E) Float.valueOf((float) ((Number) e).doubleValue() + 0.1f);
        }
        return new Range<>(start, end, function);
    }

    public static <E extends Comparable<E>> Range<E> of(E start, E end, Function<E, E> func) {
        return new Range<>(start, end, func);
    }

    public int size() {

        if (start == null || end == null) {
            return 0;
        }

        if (start instanceof Number && !(start instanceof Float) && !(start instanceof Double)) {

            return (int) (((Number) end).longValue() + 1 - ((Number) start).longValue())
                    + additionalElements.size()
                    - removedElements.size();

        } else if ((start instanceof Float) || (start instanceof Double)) {

            return (int) Math.round(((Number) end).doubleValue() * 10 + 1
                    - ((Number) start).doubleValue() * 10)
                    + additionalElements.size()
                    - removedElements.size();

        } else {
            int counter = 0;
            T bufStart = start;
            while (!bufStart.equals(func.apply(end))) {
                counter++;
                bufStart = func.apply(bufStart);
            }
            return counter + additionalElements.size() - removedElements.size();
        }
    }

    public boolean isEmpty() {
        return start == null ||
                end == null ||
                start.equals(end) ||
                removedElements.containsAll(Arrays.asList(this.toArray()));
    }

    public boolean contains(Object o) {
        try {
            return (start.compareTo((T) o) <= 0
                    && end.compareTo((T) o) >= 0
                    && !removedElements.contains(o))
                    || additionalElements.contains(o);
        } catch (ClassCastException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {

            private T currentElement = start;

            private final Iterator<T> addIterator = additionalElements.iterator();

            @Override
            public boolean hasNext() {
                return (!currentElement.equals(func.apply(end))
                        || addIterator.hasNext())
                        && !removedElements.containsAll(additionalElements);
            }

            @Override
            public T next() {

                T buf = currentElement;

                T addBuf;

                if (removedElements.contains(buf)) {
                    currentElement = func.apply(currentElement);
                    return next();
                }

                if (end.compareTo(buf) < 0) {
                    addBuf = addIterator.next();
                    if (removedElements.contains(buf)) {
                        return next();
                    }
                    return addBuf;
                }

                currentElement = func.apply(currentElement);

                return buf;
            }
        };

        return iterator;

    }

    public Object[] toArray() {

        Object[] arr = new Object[this.size()];
        int i = 0;

        for (var element : this) {
            arr[i] = element;
            i++;
        }

        return arr;
    }

    public <T1> T1[] toArray(T1[] a) {

        Iterator<T> iterator = this.iterator();

        for (int i = 0; i < a.length; i++) {
            if (iterator.hasNext()) {
                try{
                    a[i] = (T1) iterator.next();
                }
                catch (ClassCastException | NullPointerException e) {
                    throw new IllegalArgumentException();
                }
            } else {
                break;
            }
        }

        return a;
    }

    public boolean add(T t) {
        if (!contains(t)) {
            additionalElements.add(t);
            removedElements.remove(t);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(Object o) {
        return contains(o) && removedElements.add((T) o);
    }

    public boolean containsAll(Collection<?> c) {
        for (var o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T e : c)
            if (add(e))
                modified = true;
        return modified;
    }

    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        for (var m : c) {
            if (remove(m)) {
                modified = true;
            }
        }
        return modified;
    }

    public void clear() {
        start = null;
        end = null;
        func = null;
        additionalElements.clear();
        additionalElements = null;
        removedElements.clear();
        removedElements = null;
    }
}
