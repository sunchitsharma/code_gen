import java.io.*;

public class caller {
    public static void main(String[] args) throws Exception
    {
	servclass obj = new servclass();
	obj.add(5,6,7);
	obj.mul(5.7,8);
    }
}
