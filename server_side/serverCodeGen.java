// IMPORTS /////////////////////
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.Scanner;
////////////////////////////////

public class serverCodeGen {

    public static void main(String args[]) throws Exception{

      // INPUTS /////////////////////////////////////////
      Scanner sc=new Scanner(System.in);

      System.out.println("Enter the class name : ");
      String class_name = sc.nextLine();

      System.out.println("Enter the interface name : ");
      String intf_name = sc.nextLine();

      System.out.println("Enter the Port No. : ");
      String port_of_server = sc.nextLine();

      PrintWriter pr = new PrintWriter(class_name+".java");

      ///////WRITING IMPORTS ///////////////////////////////

      pr.println("import java.io.*;");
      pr.println("import java.net.*;");
      pr.println("import java.lang.reflect.*;");
      pr.println("import java.util.*;");

      //////END OF IMPORTS /////////////////////////////////


      pr.println("public class "+class_name+" {\n");
      pr.println("public static void main(String args[]) \n{\n");
      String to_write="try {";
      pr.println(to_write);
      to_write = "ServerSocket ss = new ServerSocket("+port_of_server+");";
      pr.println(to_write);
      to_write = "while(true){";
      pr.println(to_write);

      /////// SOCKET CODE ////////////////////////////////////

      to_write = "Socket s = ss.accept();\n"
                  +"DataInputStream dis = new DataInputStream(s.getInputStream());\n"
                  +"String receivedParameter=(String) dis.readUTF();";

      pr.println(to_write);

      /////// END OF SOCKET CODE //////////////////////////////

      pr.println("String marsh[]=receivedParameter.split(\" \");");

      /////// GOT THE MARSHALLED STRING SEPRATED /////////////

      pr.println("Class c1 = Class.forName(\""+intf_name+"\");");  // FETCHING THE INTERFACE

      pr.println("Method[] method = c1.getDeclaredMethods();");
      pr.println("Class c=Class.forName(marsh[0]);"); // FETCHING THE CALLED CLASS
      pr.println("Object obj=c.newInstance();"); //MAKING OBJECT OF CALLED CLASS

      pr.println("Class parameterTypes[]=new Class[Integer.parseInt(marsh[3])];"); //CH
      pr.println("Object param[]=new Object[Integer.parseInt(marsh[3])];"); //CH
      pr.println("  HashMap<String,Vector<String>> h=new HashMap<String,Vector<String>>();");

      // CHECKING REORDERING
      pr.println("for(int i=0;i<method.length;i++){");
      pr.println("Vector temp=new Vector();");
      pr.println("Parameter[] parameter = method[i].getParameters();");
      pr.println("for(int j=0;j<parameter.length;j++)");
      pr.println("temp.add(parameter[j].getType().toString());");
      pr.println("h.put(method[i].getName(),temp);");
      pr.println("}");
      //END OF FOR

      pr.println("Vector<String> parameterVector=new Vector<String>();");

      pr.println("if(h.containsKey(marsh[2])){"); //CH
      pr.println("parameterVector=h.get(marsh[2]);"); //CH
      pr.println("}"); // END OF IF

      pr.println("for(int i=0;i<Integer.parseInt(marsh[3]);i++){"); //CH
      pr.println(" int p=0;");

      pr.println("for(int j=0;j<parameterVector.size();j++)");
      pr.println("{");
      pr.println("  p= parameterVector.indexOf(marsh[4+i*3]);"); //CH
      pr.println("parameterVector.set(p,\"\");");
      pr.println("break;");
      pr.println("}");

      // THE SWITCH CASE
      pr.println("switch(marsh[4+i*3]){"); //CH

      //CASE INT
      pr.println("case \"int\":");
      pr.println("parameterTypes[p] = int.class;");
      pr.println("param[p] = Integer.parseInt(marsh[6+i*3]);"); //CH
			pr.println("break;");

      //CASE CHAR
			pr.println("case \"char\":");
			pr.println("parameterTypes[p] = char.class;");
			pr.println("param[p] = marsh[6+i*3];"); //CH
			pr.println("break;");

      //CASE FLOAT
			pr.println("case \"float\":");
			pr.println("parameterTypes[p] = float.class;");
			pr.println("param[p] = Float.parseFloat(marsh[6+i*3]);"); //CH
			pr.println("break;");

      //CASE DOUBLE
			pr.println("case \"double\":");
			pr.println("parameterTypes[p] = double.class;");
			pr.println("param[p] = Double.parseDouble(marsh[6+i*3]);"); //CH
			pr.println("break;");

      //ELSE PARSE IN BTYE STREAM
      pr.println("default:");
      pr.println("parameterTypes[p] = Class.forName(marsh[4+i*3]);");//CH
      pr.println("byte b[] = marsh[5+i*3].getBytes();");//CH
      pr.println("ByteArrayInputStream BIS = new ByteArrayInputStream(b);");
      pr.println("ObjectInputStream ois = new ObjectInputStream(BIS);");
      pr.println("param[p] = ois.readObject();");

      pr.println("}"); // END OF SWITCH-CASE


      pr.println("}"); // END OF FOR
      pr.println("Method m=c.getDeclaredMethod(marsh[2], parameterTypes);"); //CH
      pr.println("Object answer = m.invoke(obj,param);\n");

      /////////////////RETURNING THE ANSWER ////////////////////////////

      pr.println("try{\n");
        pr.println("Socket sc=new Socket(\"localhost\",8000);\n");
        pr.println("DataOutputStream dout=new DataOutputStream(sc.getOutputStream());\n");
        pr.println("dout.writeUTF("+"(answer).toString()"+");\n");
        pr.println("dout.flush();\n");
        pr.println("dout.close();\n");
        pr.println("sc.close();\n");
      pr.println("}catch(Exception e){System.out.println(e);}");

      //////////////////////////////////////////////////////////////////
      pr.println("}");
      pr.println("} catch (Exception e) {");
      pr.println("System.out.println(e);");
      pr.println("}");
      pr.println("}"); // END OF MAIN
      pr.println("}"); // END OF CLASS
      pr.close();
    }

}
