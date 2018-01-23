import java.io.*;

public class caller {
    public static void main(String[] args) throws Exception
    {
        servclass obj = new servclass();
	      System.out.println(obj.add(5,6,7));
	      System.out.println(obj.mul(5.7,8));
    }
}
