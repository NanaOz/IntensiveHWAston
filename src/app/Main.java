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

    }
}
