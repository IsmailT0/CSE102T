import java.util.ArrayList;
import java.util.Collections;

public class Lecture_11_05_2023 {
    public static void main(String[] args) {
        ArrayList<Apple> apples = new ArrayList<Apple>();
        apples.add(new Apple(6));
        apples.add(new Apple(9));
        apples.add(new Apple(8));

        System.out.println(apples.get(2).howToEat());

        ArrayList<Fruit> fruit = new ArrayList<Fruit>();
        fruit.add(new StarFruit(0));
        fruit.add(new Apple(10));
        fruit.add(new Orange(7));

        Collections.sort(fruit);


        // Before I eat my apples and fruit, I want to only eat the tastiest ones first
        // Can you change my Fruit class or subclasses to be able to sort them largest
        // taste value first? The first ten (10) students to offer a correct solution
        // using java code to sort the ArrayList of fruit will receive a bonus 5 points
        // on the final exam.
        for(Apple a: apples)
            System.out.println(a.howToEat());

        for(Fruit f: fruit)
            System.out.println(f.howToEat());
    }
}

abstract class Fruit implements Comparable<Fruit>{
    private int taste;
    public abstract String howToEat();

    public Fruit(int taste) {
        this.taste = taste;
    }

    public void setTaste(int taste) {
        this.taste = taste;
    }

    public int getTaste() {
        return taste;
    }


    @Override
    public int compareTo(Fruit fruit){
        return Integer.compare(fruit.getTaste(),this.taste);
    }
}

class Apple extends Fruit {
    public Apple(int taste) {
        super(taste);
    }

    public String howToEat() {
        return "slice into pieces - quality = " + getTaste() + "/10";
    }
}

class Orange extends Fruit {
    public Orange(int taste) {
        super(taste);
    }

    public String howToEat() {
        return "peel it first - quality = " + getTaste() + "/10";
    }
}

class StarFruit extends Fruit {
    public StarFruit(int taste) {
        super(taste);
    }

    public String howToEat() {
        return "DON'T! IT'S POISONOUS! - quality = " + getTaste() + "/10";
    }
}
