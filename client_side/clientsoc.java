// IMPORTS //////////////////
import java.io.*;
import java.lang.reflect.*;
import java.util.Scanner;
/////////////////////////////

public class clientsoc {
    public static void main(String[] args) throws Exception
    {
	    try
	    {

        // INPUTS /////////////////////////////////////
	    	Scanner sc=new Scanner(System.in);

	    	System.out.println("Enter the class name : ");
	    	String class_name = sc.nextLine();

	    	System.out.println("Enter the interface name : ");
	    	String intf_name = sc.nextLine();

	    	System.out.println("Enter the ip of server : ");
	    	String ip_of_server = sc.nextLine();

	    	System.out.println("Enter the port of server : ");
	    	String port_of_server = sc.nextLine();

	    	sc.close();

	    	PrintWriter pr = new PrintWriter(class_name+".java");

	    	///////WRITING IMPORTS IN THE FILE /////////////

	    	pr.println("import java.io.*;");
	    	pr.println("import java.net.*;");
	    	pr.println("import java.util.*;");

	    	/////END OF IMPORTS ////////////////////////////

	    	pr.println("class " + class_name + " implements " + intf_name);
	    	pr.println("{");

	    	Class cl = Class.forName(intf_name);

	    	/////FETCHING AND WRITING FUNCTIONS ////////////

	    	Method functions[] = cl.getDeclaredMethods();

	    	int no_of_func = functions.length;

	    	for(int i=0; i< no_of_func; i++)
	    	{
	    		String to_write = "" ;

	    		//// WRITING FUNCTION SIGNATURE /////////////////////

	    		to_write = "public "+functions[1].getReturnType()+" "+functions[i].getName()+"(";

	    		int no_of_para = functions[i].getParameterCount();
	    		Parameter[] para = functions[i].getParameters();

	    		// WRITING ITS PARAMETERS //

	    		for( int j=0; j<no_of_para; ++j)
	    		{
	    			to_write += " " + para[j].getType()+" "+para[j].getName()+" ";
	    			if(j!=no_of_para-1)
	    				to_write += " ,";
	    			else
	    				to_write += " )";
	    		}
	    		pr.println(to_write);

	    		//// END OF FUNCTION SIGNATURE ////////////////////

	    		to_write = "";

	    		to_write = "\n{\n"; // STARTING THE FUNCTION

	    		pr.println(to_write);

	    		to_write = "";

	    		//// WRITING FUNCTION BODY ////////////////////////

	    		to_write= " try \n{ "; // TRY BLOCK START

	    		pr.println(to_write);

	    		to_write = "";

	    		// WRITING SOCKET CODE

	    		to_write = "Socket s = new Socket(\""+ip_of_server+"\", "+port_of_server+");\n";
	    		to_write += "DataOutputStream DOUT = new DataOutputStream(s.getOutputStream());\n";

	    		//// MARSHALLING THE STRING TO SEND ///// FORMAT : class_name+" "+func_name+" "+func_ret_type+" "+func_no_of_args+" "+name_of_var1+" "+type_of_args1+" "+val_of_var1...

	    		String marshall = "\""+class_name+" "+functions[i].getReturnType()+" "+functions[i].getName()+" "+functions[i].getParameterCount()+"\"";

	    		for(int j=0;j<functions[i].getParameterCount();j++)
	    		{
	    			marshall += " +\" "+para[j].getType()+ " " +para[j].getName()+" \"+" + para[j].getName()+" ";
	    		}

	    		to_write += "DOUT.writeUTF("+marshall+");\n";
	    		to_write += "DOUT.flush();\nDOUT.close();\ns.close();";

	    		// END OF SOCKET CODE

	    		pr.println(to_write);

	    		to_write = "}\n catch(Exception e)\n{\nSystem.out.println(e);\n}";

	    		pr.println(to_write);

	    		pr.println("\n}"); // End of Function

	    	}

	    	pr.println("\n}"); // End of Class

	    	pr.close();
	    }

	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
    }
}


//
//
//
//
//
//
//
//		    Socket s=new Socket("localhost",10000);
//		    DataOutputStream DOS=new DataOutputStream(s.getOutputStream());
//		    ///////////////////////////////////////////////////////////////////////////////////////////////////////
//		    int a=20;
//		    int b=40;
//		    int c=60;
//
//		    String class_name = "Clientsoc";
//		    String func_name = "add";
//		    String func_ret_type = "void";
//		    String func_no_of_args = "3";
//		    String name_of_var1 = "a";
//		    String type_of_args1 = "int";
//		    String val_of_var1 = a+"";
//		    String name_of_var2 = "b";
//		    String type_of_args2 = "int";
//		    String val_of_var2 = b+"";
//		    String name_of_var3 = "c";
//		    String type_of_args3 = "int";
//		    String val_of_var3 = c+"";
//
//
//
//		    String str = class_name+" "+func_name+" "+func_ret_type+" "+func_no_of_args+" "+name_of_var1+" "+type_of_args1+" "+val_of_var1+" "+name_of_var2+" "+type_of_args2+" "+val_of_var2+" "+name_of_var3+" "+type_of_args3+" "+val_of_var3;
//
//		    ///////////////////////////////////////////////////////////////////////////////////////////////////////
//		    DOS.writeUTF(str);
//		    DOS.flush();
//		    DOS.close();
//		    s.close();
//
//		    ////////////////////////////////GETTING RESULT
//
//		    ServerSocket ss=new ServerSocket(8000);
//		    Socket s1=ss.accept();
//		    DataInputStream DIS=new DataInputStream(s1.getInputStream());
//		    String  str1=(String)DIS.readUTF();
//		    System.out.println("message= "+str1);
//
//		    ////////////////////////////////////////////////
//	    }
//    	catch(Exception e)
//   		{
//    		System.out.println(e);
//   		}
//    }
//}
