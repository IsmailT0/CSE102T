import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //addBirthMonthYear(bdMap,"İsmail",2004,"January" );

    }

    public static void addBirthMonthYear(Map<Integer,Map<String ,ArrayList<String>>> bdMap, String name, Integer year, String month){
        Map<String , ArrayList<String>> secondMap = bdMap.getOrDefault(year,new HashMap<>());
        ArrayList<String > nameList = secondMap.getOrDefault(month,new ArrayList<>());
        nameList.add(name);
    }

    public static int[] addItem(int[] arr , int value ){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value)
                return arr;
        }
        arr= Arrays.copyOf(arr,arr.length+1);
        arr[arr.length-1] = value;
        return arr;
    }

    public static int countItems(int[] arr){
        return arr.length;
    }

    public static String listItems(int [] arr){
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            stb.append(arr[i]).append(" ");
        }
        return stb.toString();
    }


}



