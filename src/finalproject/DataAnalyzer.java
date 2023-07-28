package finalproject;

import java.util.ArrayList;

public abstract class DataAnalyzer {

    Parser parser; // store the parsed data waiting to be processed further
    
    public DataAnalyzer(Parser p) {
        parser = p;
        extractInformation();
    }

    public abstract MyHashTable<String, Integer> getDistByKeyword(String keyword);
    public abstract void extractInformation();

    private String getRatingCategory(double rating) {
        return String.valueOf((int) rating);
    }
}
