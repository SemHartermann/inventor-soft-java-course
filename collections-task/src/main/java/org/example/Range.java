package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * У цій задачи, для Чисельних типів скористуємося методом range() з інтерфейсу InstStream.
 * Він буде повертати нам вже готовий стрім з початкового до кінцевого елементу з різницею 1.
 * Для чисел з  плаваючею точкою будемо робити те саме але з початку домножимо
 * і після поділемо на 10, тим самим отримаємо різницю у 0,1.
 *
 * Для нечисельних типів будемо зміеювати наш перший елемент згідно з наданою як аргумент
 * функцією, та додавати його в Сет допоки елемент не буде рівен останньому
 * @param <T>
 */

public class Range<T> implements Set<T> {

    private final Set<T> innerSet;

    public Range(Set<T> innerSet) {
        this.innerSet = innerSet;
    }

    public static Range<Long> of(Long start, Long end) {

        Set<Long> bufSet = new TreeSet<>();

        if (start.equals(end)) {
            return new Range<>(bufSet);
        }

        bufSet = LongStream
                .range(start, end + 1)
                .boxed()
                .collect(Collectors.toSet());

        return new Range<>(bufSet);
    }

    public static Range<Integer> of(Integer start, Integer end) {

        Set<Integer> bufSet = new TreeSet<>();

        if (start.equals(end)) {
            return new Range<>(bufSet);
        }

        bufSet = IntStream
                .range(start, end + 1)
                .boxed()
                .collect(Collectors.toSet());

        return new Range<>(bufSet);
    }

    public static Range<Short> of(Short start, Short end) {

        Set<Short> bufSet = new TreeSet<>();

        if (start.equals(end)) {
            return new Range<>(bufSet);
        }

        bufSet = IntStream
                .range(start, end + 1)
                .boxed()
                .map(o->(short)(int)o)
                .collect(Collectors.toSet());

        return new Range<>(bufSet);
    }

    public static Range<Byte> of(Byte start, Byte end) {

        Set<Byte> bufSet = new TreeSet<>();

        if (start.equals(end)) {
            return new Range<>(bufSet);
        }

        bufSet = IntStream
                .range(start, end + 1)
                .boxed()
                .map(o->(byte)(int)o)
                .collect(Collectors.toSet());

        return new Range<>(bufSet);
    }

    public static Range<Float> of(Float start, Float end) {

        Set<Float> bufSet = new TreeSet<>();

        if (start.equals(end)) {
            return new Range<>(bufSet);
        }

        bufSet = IntStream
                .range((int)(start*10), (int)(end*10+1))
                .boxed()
                .map(o->(float)o)
                .map(o->o/10)
                .collect(Collectors.toSet());

        return new Range<>(bufSet);
    }

    public static Range<Double> of(Double start, Double end) {

        Set<Double> bufSet = new TreeSet<>();

        if (start.equals(end)) {
            return new Range<>(bufSet);
        }

        bufSet = IntStream
                .range((int)(start*10), (int)(end*10+1))
                .boxed()
                .map(o->(double)o)
                .map(o->o/10)
                .collect(Collectors.toSet());

        return new Range<>(bufSet);
    }

    public static <E> Range<E> of(E start, E end, Function<E, E> func) {

        Set<E> bufSet = new HashSet<>();

        if (start.equals(end)) {
            return new Range<>(bufSet);
        }

        while (!start.equals(func.apply(end))){
            bufSet.add(start);
            start = func.apply(start);
        }

        return new Range<>(bufSet);
    }

    @Override
    public int size() {
        return innerSet.size();
    }

    @Override
    public boolean isEmpty() {
        return innerSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return innerSet.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return innerSet.iterator();
    }

    @Override
    public Object[] toArray() {
        return innerSet.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) innerSet.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return innerSet.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return innerSet.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return innerSet.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return innerSet.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return innerSet.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return innerSet.removeAll(c);
    }

    @Override
    public void clear() {
        innerSet.clear();
    }
}