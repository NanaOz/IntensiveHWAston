package app;

public class Main {
    public static void main(String[] args) {

        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add("a");
        myArrayList.add("b");
        myArrayList.add("c");
        myArrayList.display();

        myArrayList.remove(1);
        myArrayList.display();


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
