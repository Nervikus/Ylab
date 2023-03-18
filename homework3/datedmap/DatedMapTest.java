package datedmap;

public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMap datedMap = new DatedMapImpl();

        System.out.println(datedMap.containsKey("A"));
        System.out.println(datedMap.get("A"));
        System.out.println(datedMap.getKeyLastInsertionDate("A"));
        System.out.println(datedMap.keySet());
        System.out.println();
        datedMap.remove("A");

        datedMap.put("A", "a");
        System.out.println(datedMap.containsKey("A"));
        System.out.println(datedMap.get("A"));
        System.out.println(datedMap.getKeyLastInsertionDate("A"));
        System.out.println(datedMap.keySet());
        System.out.println();

        Thread.sleep(2000);

        datedMap.put("B", "b");
        System.out.println(datedMap.containsKey("B"));
        System.out.println(datedMap.get("B"));
        System.out.println(datedMap.getKeyLastInsertionDate("B"));
        System.out.println(datedMap.keySet());
        System.out.println();
        datedMap.remove("B");

        System.out.println(datedMap.containsKey("B"));
        System.out.println(datedMap.get("B"));
        System.out.println(datedMap.getKeyLastInsertionDate("B"));
        System.out.println(datedMap.getKeyLastInsertionDate("A"));
        System.out.println(datedMap.keySet());
        System.out.println();
        datedMap.remove("A");

        System.out.println(datedMap.containsKey("A"));
        System.out.println(datedMap.get("A"));
        System.out.println(datedMap.getKeyLastInsertionDate("A"));
        System.out.println(datedMap.keySet());
    }
}
