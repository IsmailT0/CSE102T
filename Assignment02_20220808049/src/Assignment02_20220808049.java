import java.util.ArrayList;
import java.util.Objects;

/**
 * @author İsmail Temüroğlu
 * @since 01.04.2023
 * */

public class Assignment02_20220808049 {
    public static void main(String[] args) {


    }

}
class Product{
    private Long Id;
    private String name;
    private int quantity;
    private double price;
    public Product(){}
    public Product(Long Id,String name, int quantity,double price){
        setId(Id);
        setName(name);
        addToInventory(quantity);
        setPrice(price);
    }

    public Long getId() {return Id;}
    public void setId(long id) {this.Id = id;}


    public String getName() {return name;}
    public void setName(String name) {this.name = name;}



    public double getPrice() {return price;}
    public void setPrice(double price) throws InvalidPriceException {
        if (price<0)
            throw new InvalidPriceException(price);
        this.price = price;
    }

    public int remaining(){return this.quantity;}

    public int addToInventory(int amount) throws InvalidAmountException{
        if (amount<0)
            throw new InvalidAmountException(amount);
        quantity+=amount;
        return quantity;
    }

    public double purchase(int amount) throws InvalidAmountException{
        if (amount<0)
            throw new InvalidAmountException(amount);
        if (amount>quantity) {
            throw new InvalidAmountException(amount,quantity);
        }
        this.quantity-=amount;
        return amount*price;
    }

    @Override
    public String toString(){
        return "Product "+ this.name +" has " +this.quantity+ " remaining.";
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Product && Math.abs(((Product) o).getPrice() - this.price) < 0.001;
    }
}

class FoodProduct extends Product{
    private int calories;
    private boolean dairy;
    private boolean eggs;
    private boolean peanuts;
    private boolean gluten;

    public FoodProduct(Long Id,String name,int quantity,double price,
                       int calories,boolean dairy,boolean eggs,boolean peanuts,boolean gluten){
        super(Id,name,quantity,price);
        setCalories(calories);
        this.dairy=dairy;
        this.eggs=eggs;
        this.peanuts=peanuts;
        this.gluten=gluten;
    }
    FoodProduct(){}

    public String getIngredients(){
        String st= super.getName();
        st+=" Ingredients:\n\t calories: " + calories + "\n\tdiary: " + dairy +"eggs: "+ eggs+"\n\tpeanuts: "+ peanuts+ "\n\tgluten"+gluten;
        return st;
    }

    public int getCalories() {return calories;}

    // throws InvalidAmountException if calories is negative
    public void setCalories(int calories) throws InvalidAmountException {
        if (calories<0)
            throw new InvalidAmountException(calories);
        this.calories = calories;
    }

    public boolean containsDairy(){return this.dairy;}

    public boolean containsPeanuts(){return this.peanuts;}

    public boolean containsEggs(){return this.eggs;}
    public boolean containsGluten(){return this.gluten;}
}

class CleaningProduct extends Product{
    private boolean liquid;
    private String whereToUse;

    public CleaningProduct(Long Id,String name,int quantity,double price,
                           boolean liquid,String whereToUse){
        super(Id, name, quantity, price);
        this.liquid=liquid;
        setWhereToUse(whereToUse);
    }
    CleaningProduct(){}

    public String getWhereToUse() {return whereToUse;}
    public void setWhereToUse(String whereToUse) {this.whereToUse = whereToUse;}

    public boolean isLiquid(){return this.liquid;}
}

class Customer{
    private String name;
    private ArrayList<Product> products;
    private ArrayList<Integer> counts;
    private double totalDue;

    public Customer(){
        products= new ArrayList<>();
        counts= new ArrayList<>();
        totalDue=0;
    }
    public Customer(String name){
        this();
        setName(name);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}


    //Makes the purchase if an exception occurs displays error message.
    public void addToCart(Product product, int count){
        try{
            totalDue+=product.purchase(count);
            products.add(product);
            counts.add(count);
        }catch (InvalidAmountException ex){
            System.out.println("ERROR:"+ ex);
        }
    }

    public String receipt() {
        String receipt = "";
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
             receipt += product.getName() + " - " + product.getPrice() + " X " + counts.get(i) + " = " +
                    (product.getPrice() * counts.get(i)) + "\n";
        }
        receipt+=  "-------------------------"+"\n";
        receipt += "Total Due = " + getTotalDue();
        return receipt;
    }

    public double getTotalDue(){return totalDue;}

    public double pay(double amount)throws InsufficientFundsException{
        if (amount >= totalDue){
            System.out.println("Thank you");
            double returnV= amount-totalDue;
            products.clear();
            counts.clear();
            totalDue=0;
            return returnV;
        }else {
            throw new InsufficientFundsException(totalDue,amount);
        }
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
        setPhone(phone);
        this.points=0;
    }

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public int getPoints() {return points;}
    public void addPoints(int points){
        if (points>0){
            this.points+=points;
        }
    }

    public double pay(double amount,boolean usePoints) throws InsufficientFundsException{
        double totalDue= getTotalDue();
        double discount = 0;
        if (usePoints && points > 0){
            if(totalDue<=points*0.01){
                discount=totalDue;
                points-=totalDue*100;
            }
            else{
                discount=(points * 0.01);
                points=0;
            }
        }

        if (amount + discount < totalDue) {
            throw new InsufficientFundsException(totalDue, amount);
        }


        addPoints((int) (totalDue-discount));

        return super.pay(amount+discount);
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
    private ArrayList<ClubCustomer> customers;

    public Store(String name,String website){
        setName(name);
        setWebsite(website);
        products= new ArrayList<>();
        customers= new ArrayList<>();
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getWebsite() {return website;}
    public void setWebsite(String website) {this.website = website;}

    public int getInventorySize(){return products.size();}

    public void addProduct(Product product){
        products.add(product);
    }

    public Product getProduct(Long ID) throws ProductNotFoundException{
        for (Product tempProduct : products) {
            if (Objects.equals(ID, tempProduct.getId()))
                return tempProduct;
        }
        throw new ProductNotFoundException(ID);
    }
    public Product getProduct(String name) throws ProductNotFoundException{
        for (Product tempProduct : products) {
            if ((name).equalsIgnoreCase((tempProduct.getName())))
                return tempProduct;
        }
        throw new ProductNotFoundException(name);
    }

    public void addCustomer(ClubCustomer customer) {
        customers.add(customer);
    }

    public  ClubCustomer getCustomer(String phone)throws CustomerNotFoundException{
        for (ClubCustomer customer : customers) {
            if (phone.equals(customer.getPhone()))
                return customer;
        }
        throw new CustomerNotFoundException(phone);
    }

    public void removeProduct(Long ID)throws ProductNotFoundException{
        boolean x= false;
        for (Product tempProduct : products) {
            if (Objects.equals(ID, tempProduct.getId())){
                products.remove(tempProduct);
                x=true;
            }
        }
        if (!x)
            throw new ProductNotFoundException(ID);
    }

    public void removeProduct(String name)throws ProductNotFoundException{
        boolean x= false;
        for (Product tempProduct : products) {
            if (name.equals(tempProduct.getName())){
                products.remove(tempProduct);
                x=true;
            }
        }
        if (!x)
            throw new ProductNotFoundException(name);
    }
    public void removeCustomer(String phone)throws CustomerNotFoundException{
        boolean x= false;
        for (ClubCustomer tempCustomer : customers) {
            if (phone.equals(tempCustomer.getPhone())){
                customers.remove(tempCustomer);
                x=true;
            }
        }
        if (!x)
            throw new CustomerNotFoundException(phone);
    }
}


//Costume Exceptions
class CustomerNotFoundException extends IllegalArgumentException{
    private String phone;

    public CustomerNotFoundException(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CustomerNotFoundException: " + phone ;
    }
}

class InsufficientFundsException extends RuntimeException {
    private double total;
    private double payment;

    public InsufficientFundsException( double total, double payment) {
        this.total = total;
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "InsufficientFundsException: " + total + " due, but only " + payment + " given";
    }
}
class InvalidAmountException extends RuntimeException{
    private int amount;
    private int quantity=-1;

    public InvalidAmountException(int amount){
        this.amount=amount;
    }

    public InvalidAmountException(int amount, int quantity) {
        this.amount = amount;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        if (quantity == -1) {
            return "InvalidAmountException: " + amount;
        } else {
            return "InvalidAmountException: " + amount + " was requested, but only " + quantity + " remaining";
        }
    }
}

class InvalidPriceException extends RuntimeException{
    private double price;

    public InvalidPriceException( double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "InvalidPriceException" + price ;
    }

}


class ProductNotFoundException extends IllegalArgumentException{
    private Long Id;
    String name;

    public ProductNotFoundException( Long id) {
        Id = id;
        name=null;
    }

    public ProductNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (name==null){
            return "ProductNotFoundException: ID - " + Id;
        }
        else {
            return "ProductNotFoundException: Name - " +name;
        }
    }
}