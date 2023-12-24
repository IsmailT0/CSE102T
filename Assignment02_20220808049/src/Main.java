import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store s = new Store ("Migros", "www.migros.com.tr");
        Customer c = new Customer ("CSE 102");
        ClubCustomer cc = new ClubCustomer ("Club CSE 102", "05551234567");
        //s.addCustomer (c); // compiler error because c is Customer not ClubCustomer
        s.addCustomer(cc);
        Product p = new Product (123456L, "Computer", 20, 1000.00);
        FoodProduct fp = new FoodProduct (456798L, "Snickers", 100, 2, 250, true,
                true, true, false);
        CleaningProduct cp = new CleaningProduct (31654L, "Mop", 28, 99,
                false, "Multi-room");
        s.addProduct(p);
        s.addProduct(fp);

        s.addProduct(cp);

        //System.out.println(s.getProduct("shoes")); // Exception due to product not being in store
//        cc.addToCart(fp,10);
//        cc.addPoints(2000);
//        System.out.println(cc.pay(0,true));
//        System.out.println(cc.getPoints());

        System.out.println(s.getInventorySize());
        //System.out.println(s.getProduct("shoes"));
        System.out.println(cp.purchase(2));
        s.getProduct("Computer").addToInventory(3);
        //System.out.println(fp.purchase (200)); // results in Exception
        c.addToCart(p, 2);
        c.addToCart(s.getProduct("snickers"), -2); // NOTE: This does not stop the program because the Exception is caught
        c.addToCart(s.getProduct("snickers"), 1);
        System.out.println("Total due - " + c.getTotalDue());
        System.out.println("\n\nReceipt:\n" + c.receipt());

        //System.out.println("After paying:" + c.pay (1000)); // results in Exception " );
        System.out.println("After paying: "+ c.pay(2020));
        System.out.println("Total due "+c.getTotalDue());
        System.out.println("\n\nReceipt 1:\n" + c.receipt());
        //Customer c2 = s.getCustomer("05551234568"); // Exception
        cc.addToCart(s.getProduct("snickers"), 2);
        cc.addToCart(s.getProduct("snickers"), 1);
        System.out.println("\n\nReceipt 2:\n" + cc.receipt());
        Customer c3 = s.getCustomer("05551234567");
        c3.addToCart(s.getProduct("snickers"), 10);
        System.out.println("\n\nReceipt 3:\n" + cc.receipt());
        System.out.println(((ClubCustomer) c3). pay (26, false));
        c3.addToCart(s.getProduct (31654L), 3);
        System.out.println(c3.getTotalDue());
        System.out.println(c3. receipt());
        System.out.println(cc.pay (3*99, false));
        c3.addToCart(s.getProduct (31654L), 3);
        System.out.println(c3.getTotalDue());
        System.out.println(c3. receipt());
        System.out.println(cc.pay(3*99, true));
//        ClubCustomer cc = new ClubCustomer("ali","111");
//        Product p1= new Product(123L,"Computer",20,2000);
//        FoodProduct fp = new FoodProduct (456798L, "Snickers", 100, 2, 250, true,
//               true, true, false);
//
//        CleaningProduct cp = new CleaningProduct (31654L, "Mop", 28, 99,
//                false, "Multi-room");
//
//        Store store= new Store("Bİm","bim.com");
//        store.addProduct(p1);
//        store.addProduct(fp);
//        store.addProduct(cp);
//        cc.addToCart(store.getProduct(123L),1);
//        cc.addPoints(100);

//        System.out.println(cc.pay(2000,true));

}

}