package org.example;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

public class Range<T> implements Set<T> {

    private T start;

    private T end;

    private Function<T, T> func;

    private Range(T start, T end) {
        this.start = start;
        this.end = end;
    }

    private Range(T start, T end, Function<T, T> func) {
        this.start = start;
        this.end = end;
        this.func = func;
    }

    public static <E> Range<E> of(E start, E end) {
        return new Range<>(start, end);
    }

    public static <E> Range<E> of(E start, E end, Function<E, E> func) {
        return new Range<>(start, end, func);
    }

    public int size() {

        if (start instanceof Number && !(start instanceof Float) && !(start instanceof Double)) {

            return (int) (((Number) end).longValue() + 1 - ((Number) start).longValue());

        } else if ((start instanceof Float) || (start instanceof Double)) {

            return (int) Math.round(((Number) end).doubleValue() * 10 + 1 - ((Number) start).doubleValue() * 10);

        } else {
            int counter = 0;
            T bufStart = start;
            while (!bufStart.equals(func.apply(end))) {
                counter++;
                bufStart = func.apply(bufStart);
            }
            return counter;
        }
    }

    public boolean isEmpty() {
        return start.equals(end);
    }

    public boolean contains(Object o) {

        if (start instanceof Number && o instanceof Number) {

            if ((!(o instanceof Float || o instanceof Double) && !(start instanceof Float || start instanceof Double))
                    || (o instanceof Float || o instanceof Double) && (start instanceof Float || start instanceof Double)) {

                return ((Number) start).doubleValue() <= ((Number) o).doubleValue()
                        && ((Number) o).doubleValue() <= ((Number) end).doubleValue();
            }

        } else if (o.getClass() == end.getClass()) {
            T bufStart = start;
            while (!bufStart.equals(func.apply(end))) {
                if (o.equals(bufStart)) {
                    return true;
                }
                bufStart = func.apply(bufStart);
            }
        }

        return false;
    }

    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {

            private T currentElement = start;


            @Override
            public boolean hasNext() {
                if (func!=null){
                    return !currentElement.equals(func.apply(end));
                } else if (start instanceof Number && !(start instanceof Float) && !(start instanceof Double)) {
                    return !currentElement.equals(((Number) end).intValue() + 1);
                } else {
                    return !currentElement.equals(((Number) end).floatValue() + 0.1f);
                }
            }

            @Override
            public T next() {
                T buf = currentElement;
                if (func!=null){
                    currentElement = func.apply(currentElement);
                } else if (start instanceof Number && !(start instanceof Float) && !(start instanceof Double)) {
                    currentElement = (T) Integer.valueOf(((Number) currentElement).intValue() + 1);
                } else {
                    currentElement = (T) Float.valueOf(((Number) currentElement).floatValue() + 0.1f);
                }
                return buf;
            }
        };

        return iterator;

    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public boolean add(T t) {
        return false;
    }

    public boolean remove(Object o) {
        return false;
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
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public void clear() {
    }
}
