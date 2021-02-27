import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class test {


    public static void main(String[] args) {
        List<String> list = Arrays.asList("help1","help2","help3","help1");
        Collections.replaceAll(list,"help1","help8");
        System.out.println(list);
    }
    
}
