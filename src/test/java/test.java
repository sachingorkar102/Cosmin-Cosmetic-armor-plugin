import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

public class test {


    public static void main(String[] args) {
        String version = "org.bukkit.craftbukkit.v1_15_R1";
        String v = version.substring(version.lastIndexOf("v")+1);
        System.out.println(v);
    }

    
}
