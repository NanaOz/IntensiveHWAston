package app;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        workOfMyArrayList();

        workOfMyLinkedList();

        workHashMapRebuilding();
    }

    private static void workHashMapRebuilding() {
        HashMap<HashMapRebuild, String> map = new HashMap<>(8);

        for (int i = 0; i < 15; i++) {
            map.put(new HashMapRebuild("key" + i), "value" + i);

            boolean isTree = i >= 8;

            System.out.println("--- Итерация " + i + " ---");
            System.out.println("Общий размер HashMap: " + map.size());
            System.out.println("Размер цепочки в бакете: " + (i + 1));
            System.out.println("Тип ноды: " + (isTree ? "TreeNode" : "Node"));
        }
    }

    private static void workOfMyArrayList() {
        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add("a");
        myArrayList.add("b");
        myArrayList.add("c");
        myArrayList.display();

        myArrayList.remove(1);
        myArrayList.display();
    }

    private static void workOfMyLinkedList() {
        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add("skjcd");
        myLinkedList.add("opiop");
        myLinkedList.add("wtfwef");

        myLinkedList.add(2, "dnkjnks");
        System.out.println(myLinkedList);

        myLinkedList.remove(1);
        System.out.println(myLinkedList);
    }
}
