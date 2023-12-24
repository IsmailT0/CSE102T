import java.util.HashMap;
import java.util.Map;


/**
 * @author İsmail Temüroğlu
 * @since 21.05.2023
 */

public class Assignment03_20220808049 {
    public static void main(String[] args) {

    }
}

class Product {
    private Long Id;
    private String name;

    private double price;



    public Product(Long Id, String name, double price) {
        setId(Id);
        setName(name);
        setPrice(price);
    }

    public Long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws InvalidPriceException {
        if (price < 0)
            throw new InvalidPriceException(price);
        this.price = price;
    }

    @Override
    public String toString() {
        return Id + " - " + name + " @ " + price;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Product && Math.abs(((Product) o).getPrice() - this.price) < 0.001;
    }
}

class FoodProduct extends Product {
    private int calories;
    private boolean dairy;
    private boolean eggs;
    private boolean peanuts;
    private boolean gluten;

    public FoodProduct(Long Id, String name, double price,
                       int calories, boolean dairy, boolean eggs, boolean peanuts, boolean gluten) {
        super(Id, name, price);
        setCalories(calories);
        this.dairy = dairy;
        this.eggs = eggs;
        this.peanuts = peanuts;
        this.gluten = gluten;
    }


    public int getCalories() {
        return calories;
    }

    // throws InvalidAmountException if calories is negative
    public void setCalories(int calories) throws InvalidAmountException {
        if (calories < 0)
            throw new InvalidAmountException(calories);
        this.calories = calories;
    }

    public boolean containsDairy() {
        return this.dairy;
    }

    public boolean containsPeanuts() {
        return this.peanuts;
    }

    public boolean containsEggs() {
        return this.eggs;
    }

    public boolean containsGluten() {
        return this.gluten;
    }
}

class CleaningProduct extends Product {
    private boolean liquid;
    private String whereToUse;

    public CleaningProduct(Long Id, String name, double price,
                           boolean liquid, String whereToUse) {
        super(Id, name, price);
        this.liquid = liquid;
        setWhereToUse(whereToUse);
    }



    public String getWhereToUse() {
        return whereToUse;
    }

    public void setWhereToUse(String whereToUse) {
        this.whereToUse = whereToUse;
    }

    public boolean isLiquid() {
        return this.liquid;
    }
}

class Customer {
    private String name;
    private Map<Store, Map<Product, Integer>> cart;


    public Customer(String name) {
        setName(name);
        cart = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //Makes the purchase if an exception occurs displays error message.
    public void addToCart(Store store, Product product, int count) {
        try {

            if (count < 0 || count > store.getProductCount(product)) {
                throw new InvalidAmountException(count, store.getProductCount(product));
            }

            Map<Product, Integer> productMap = cart.computeIfAbsent(store, k -> new HashMap<>());
            cart.putIfAbsent(store, new HashMap<>());
            int currentCount = productMap.getOrDefault(product, 0);

            productMap.put(product, currentCount + count);
            cart.put(store, productMap);
        } catch (InvalidAmountException | ProductNotFoundException e) {
            System.out.println("ERROR" + e);
        }
    }

    public String receipt(Store store) throws StoreNotFoundException {
        if (cart.containsKey(store)) {

            StringBuilder receipt = new StringBuilder("Customer receipt for ");
            receipt.append(store.getName()).append("\n");

            Map<Product, Integer> productMap = cart.computeIfAbsent(store, k -> new HashMap<>());
            productMap.forEach((product, integer) -> {

                receipt.append(product).append(" X ").append(integer).append("...").append(product.getPrice() * integer)
                        .append("\n");
            });


            receipt.append("\n----------------------------\n Total Due - ");
            receipt.append(getTotalDue(store));

            return receipt.toString();

        } else {
            throw new StoreNotFoundException(store.getName());
        }

    }

    public double getTotalDue(Store store) throws StoreNotFoundException {
        if (cart.containsKey(store)) {
            double totalDue = 0;

            Map<Product, Integer> productMap = cart.get(store);

            for (Product product : productMap.keySet()) {//entry gives free access key and value   I need to use entry
                totalDue += product.getPrice() * productMap.get(product);
            }


            return totalDue;
        } else {
            throw new StoreNotFoundException(store.getName());
        }
    }

    public int getPoints(Store store) throws StoreNotFoundException {
        if (!cart.containsKey(store))
            throw new StoreNotFoundException(store.getName());

        try {
            return store.getCustomerPoints(this);
        } catch (CustomerNotFoundException e) {
            throw new StoreNotFoundException(store.getName());
        }
    }


    public double pay(Store store, double amount, boolean usePoints)
            throws InsufficientFundsException, StoreNotFoundException {

        if (!cart.containsKey(store))
            throw new StoreNotFoundException(store.getName());
        double totalDue = getTotalDue(store);

        Map<Product, Integer> products = cart.get(store);

        if (usePoints) {
            //init points
            int points;
            try {
                points = getPoints(store);
            } catch (StoreNotFoundException e) {
                points = 0;
            }

            double discount = points * 0.01;
            totalDue -= discount;

            if (amount >= totalDue) {
                System.out.println("Thank you for your business");

                for (Product product : products.keySet()) {
                    store.purchase(product, products.get(product));
                }

                cart.remove(store);

                if (totalDue > 0) {
                    store.setCustomerPoints(this, (int) (totalDue*100));
                    return amount - totalDue;
                }else {

                    store.setCustomerPoints(this,- (int) (totalDue*100));
                    return amount;
                }
            } else {
                throw new InsufficientFundsException(totalDue, amount);
            }
        } else {
            if (amount >= totalDue) {
                System.out.println("Thank you for your business");
                for (Product product : products.keySet()) {
                    store.purchase(product, products.get(product));
                }
                cart.remove(store);

                int currentPoints = getPoints(store);
                store.setCustomerPoints(this,currentPoints+(int)(totalDue*0.01));

                return amount - totalDue;
            } else {
                throw new InsufficientFundsException(totalDue, amount);
            }
        }


    }

    @Override
    public String toString() {
        return this.name;
    }
}


class Store {
    private String name;
    private String website;
    private Map<Product, Integer> productStock;
    private Map<Customer, Integer> customerPoints;

    public Store(String name, String website) {
        setName(name);
        setWebsite(website);
        customerPoints = new HashMap<>();
        productStock = new HashMap<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getInventorySize(){return productStock.size();}


    public void addCustomer(Customer customer) {
        customerPoints.put(customer, 0);
    }

    public int getCount() {
        return productStock.size();
    }

    public int getProductCount(Product product) throws ProductNotFoundException {
        if (productStock.containsKey(product)) {
            return productStock.get(product);
        } else {
            throw new ProductNotFoundException(product);
        }
    }

    public int getCustomerPoints(Customer customer) {
        if (customerPoints.containsKey(customer)) {
            return customerPoints.get(customer);
        } else {
            throw new CustomerNotFoundException(customer);
        }
    }

    public void setCustomerPoints(Customer customer, int point) {
        if (customerPoints.containsKey(customer))
            customerPoints.put(customer,point);
    }


    public void removeProduct(Product product) throws ProductNotFoundException {
        if (productStock.containsKey(product)) {
            productStock.remove(product);
        } else {
            throw new ProductNotFoundException(product);
        }
    }

    public void addToInventory(Product product, int amount) throws InvalidAmountException {
        if (amount < 0) {
            throw new InvalidAmountException(amount);
        } else {
            try {
                int total = getProductCount(product) + amount;

                productStock.put(product, total);

            } catch (ProductNotFoundException exception) {
                productStock.put(product, amount);
            }
        }
    }

    public double purchase(Product product, int amount) throws ProductNotFoundException, InvalidAmountException {

        int currentAmount = getProductCount(product);

        if (amount < 0)
            throw new InvalidAmountException(amount);
        else if (amount > currentAmount) {
            throw new InvalidAmountException(amount, currentAmount);
        } else {

            int updateAmount = currentAmount - amount;
            productStock.put(product, updateAmount);

            return product.getPrice() * amount;
        }
    }

}


//Costume Exceptions

class InsufficientFundsException extends RuntimeException {
    private double total;
    private double payment;

    public InsufficientFundsException(double total, double payment) {
        this.total = total;
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "InsufficientFundsException: " + total + " due, but only " + payment + " given";
    }
}

class InvalidAmountException extends RuntimeException {
    private int amount;
    private int quantity = -1;

    public InvalidAmountException(int amount) {
        this.amount = amount;
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

class InvalidPriceException extends RuntimeException {
    private double price;

    public InvalidPriceException(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "InvalidPriceException" + price;
    }

}

//NotFound Exceptions

class CustomerNotFoundException extends IllegalArgumentException {
    private Customer customer;

    public CustomerNotFoundException(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerNotFoundException: Name -" + customer.getName();
    }
}

class ProductNotFoundException extends IllegalArgumentException {


    private Product product;

    public ProductNotFoundException(Product product) {
        this.product = product;

    }

    @Override
    public String toString() {
        return "ProductNotFoundException: ID - " + product.getId() + " Name - " + product.getName();
    }
}

class StoreNotFoundException extends IllegalArgumentException {
    private String name;

    public StoreNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StoreNotFoundException: " + name;
    }
}
