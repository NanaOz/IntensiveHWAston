package app;

import java.util.LinkedList;

public class MyLinkedList<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
        }
    }

    /**
     * Добавление в конец списка
     *
     * @param data данные для добавления
     */
    public void add(T data) {
        Node<T> newNode = new Node<>(data);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Добавление по индексу
     *
     * @param index индекс вставки
     * @param data  данные для добавления
     */
    public void add(int index, T data) {
        checkIndexOfBoundsException(index);

        if (index == size) {
            add(data);
            return;
        }

        Node<T> newNode = new Node<>(data);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    /**
     * Метод для получения узла по индексу
     *
     * @param index индекс узла
     * @return узел
     */
    private Node<T> getNode(int index) {
        checkIndexOfBoundsException(index);

        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    /**
     * Удаление элемента по индексу
     *
     * @param index индекс удаляемого элемента
     */
    public void remove(int index) {
        checkIndexOfBoundsException(index);

        if (index == 0) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            Node<T> current = getNode(index);
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }

    /**
     * Метод получения размера
     *
     * @return размер списка
     */
    public int size() {
        return size;
    }

    /**
     * Метод проверки на выход за пределы массива
     *
     * @param index индекс элемента
     */
    private void checkIndexOfBoundsException(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс за пределами массива" + index);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
