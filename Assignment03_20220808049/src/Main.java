import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Store s1 = new Store("Migros", "www.migros.com.tr");
        Store s2 = new Store("BIM", "www.bim.com.tr");
        Customer c = new Customer("CSE 102");
        Customer cc = new Customer("Club CSE 102");
        s1.addCustomer(cc);
        Product p = new Product(123456L, "Computer", 1000.00);
        FoodProduct fp = new FoodProduct(456798L, "Snickers", 2, 250, true, true, true, false);
        CleaningProduct cp = new CleaningProduct(31654L, "Mop", 99, false, "Multi-room");
        System.out.println(cp);
        s1.addToInventory(p, 20);
        s2.addToInventory(p, 10);
        s2.addToInventory(fp, 100);
        s1.addToInventory(cp, 28);
        System.out.println(s1.getName() + " has " + s1.getCount() + " products");
        System.out.println(s1.getProductCount(p));
        System.out.println(s1.purchase(p, 2));
        s1.addToInventory(p, 3);
        System.out.println(s1.getProductCount(p));
        System.out.println(s2.getProductCount(p));


        //System.out.println(s1.getProductCount(fp));//results in Exception

        //System.out.println(s2.purchase (fp, 288)); // results in Exception


        c.addToCart(s1, p, 2);
        c.addToCart(s1, fp, 1); // NOTE: This does not stop the program because the Exception is caught
        c.addToCart(s1, cp, 1);
        System.out.println("Total due - " +c.getTotalDue (s1));
        System.out.println("\n\nReceipt: \n" + c.receipt(s1));


        //System.out.println("\n\nReceipt: \n" + c.receipt(s2)); // results in Exception

        //System.out.println("After paying: "+ c.pay(s1, 2000, true)); // results in Exception
        System.out.println("After paying:" + c.pay(s1, 2100, true));

        //System.out.println("Total due "+c.getTotalDue(s1));// results in Exception -
        //System.out.println("\n\nReceipt: \n " +c.receipt(s1)); // results in Exception

        cc.addToCart(s2, fp, 2);
        cc.addToCart(s2, fp, 1);
        System.out.println(cc.receipt(s2));

        cc.addToCart(s2, fp, 10);
        System.out.println(cc.receipt(s2));



        s2.addCustomer(cc);
        s2.setCustomerPoints(cc,2600);
        System.out.println("After paying" + cc.pay(s2,20,true));
        System.out.println(s2.getCustomerPoints(cc));


        
    }


    public static HashMap<String,ArrayList<String>> switchMap(HashMap<String , ArrayList<String>> mymap){
        HashMap<String,ArrayList<String>> temp = new HashMap<>();
        for (String  key: mymap.keySet()){
            String valueKey = key;
            ArrayList<String> strings= mymap.get(key);


            for (String tempString : strings){
                if (temp.containsKey(tempString)){
                    temp.get(tempString).add(valueKey);
                }else {
                    temp.put(tempString,new ArrayList<>(Arrays.asList(valueKey)));
                }
            }

        }
        return temp;
    }

}