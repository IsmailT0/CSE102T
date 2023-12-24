import java.util.ArrayList;

/**
 * @author İsmail Temüroğlu
 * @since 16/03/2023
 * */
public class Assignment01_20220808049 {}
class Product{
    private String Id;
    private String name;
    private int quantity;
    private double price;
    public Product(){}
    public Product(String Id,String name, int quantity,double price){
        this.Id=Id;
        this.name=name;
        this.quantity=quantity;
        this.price=price;
    }

    public String getId() {return Id;}
    public void setId(String id) {Id = id;}


    public String getName() {return name;}
    public void setName(String name) {this.name = name;}


    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public int remaining(){return this.quantity;}

    public int addToInventory(int amount){
        if (amount>0)
            quantity+=amount;
        return quantity;
    }

    public double purchase(int amount){
        if (amount<0 || amount>this.quantity)
            return 0;
        quantity-=amount;
        return amount*price;
    }

    @Override
    public String toString(){
        return "Product "+ this.name +" has " +this.quantity+ " remaining.";
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Product && Math.abs(((Product) o).getPrice()-this.price)<0.001)
            return true;
        else
            return false;
    }

}
class FoodProduct extends Product{
    private int calories;
    private boolean dairy;
    private boolean eggs;
    private boolean peanuts;
    private boolean gluten;

    public FoodProduct(String Id,String name,int quantity,double price,
                int calories,boolean dairy,boolean eggs,boolean peanuts,boolean gluten){
        super(Id,name,quantity,price);
        this.calories=calories;
        this.dairy=dairy;
        this.eggs=eggs;
        this.peanuts=peanuts;
        this.gluten=gluten;
    }
    FoodProduct(){}

    public int getCalories() {return calories;}
    public void setCalories(int calories) {this.calories = calories;}



    public boolean containsDairy(){return this.dairy;}

    public boolean containsPeanuts(){return this.peanuts;}

    public boolean containsEggs(){return this.eggs;}
    public boolean containsGluten(){return this.gluten;}
}
class CleaningProduct extends Product{
    private boolean liquid;
    private String whereToUse;

    public CleaningProduct(String Id,String name,int quantity,double price,
                    boolean liquid,String whereToUse){
        super(Id, name, quantity, price);
        this.liquid=liquid;
        this.whereToUse=whereToUse;
    }
    CleaningProduct(){}

    public String getWhereToUse() {return whereToUse;}
    public void setWhereToUse(String whereToUse) {this.whereToUse = whereToUse;}

    public boolean isLiquid(){return this.liquid;}
}
class Customer{
    private String name;

    public Customer(){}
    public Customer(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

}
class ClubCustomer extends Customer{
    private String phone;
    private int points;

    ClubCustomer(){}
    public ClubCustomer(String name,String  phone){
        super(name);
        this.phone=phone;
        points=0;
    }

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public int getPoints() {return points;}
    public void addPoints(int points){
        if (points>0){
            this.points+=points;
        }
    }

    @Override
    public String toString(){
        return super.getName() + " has " +getPoints()+" points";
    }
}

class Store{
    private String name;
    private String website;
    private ArrayList<Product> products;

    public Store(String name,String website){
        this.name=name;
        this.website=website;
        products= new ArrayList<>();
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getWebsite() {return website;}
    public void setWebsite(String website) {this.website = website;}

    public int getInventorySize(){
        return products.size();
    }

    public void addProduct(Product product,int index){
        if (index<0  || index> products.size() )
            addProduct(product);
        else
            products.add(index,product);
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public Product getProduct(int index){
        if (index<0  || index> products.size() )
            return null;
        return products.get(index);
    }

    public int getProductIndex(Product p){
        for (int i=0 ;i<products.size();i++)
            if (products.get(i)==p)
                return i;
        return -1;
    }
}