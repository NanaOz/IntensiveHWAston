package app;

import java.util.Arrays;

public class MyArrayList {
    private int size;
    private Object[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        this.size = 0;
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Метод для добавления элемента
     *
     * @param element элемент, который нужно добавить
     */
    public void add(Object element) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = element;
    }

    /**
     * Метод для получения элемента по индексу
     *
     * @param index индекс элемента
     * @return элемент
     */
    public Object get(int index) {
        checkIndexOfBoundsExeption(index);

        return elements[index];
    }

    /**
     * Метод удаления элемента из массива
     *
     * @param index индекс элемента
     */
    public void remove(int index) {
        checkIndexOfBoundsExeption(index);

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[--size] = null;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
    }

    /**
     * Метод увеличения размера массива
     */
    private void resize() {
        int newCapacity = elements.length * 2;
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Метод проверки на выход за пределы массива
     *
     * @param index индекс элемента
     */
    private void checkIndexOfBoundsExeption(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс за пределами массива" + index);
        }
    }
}
