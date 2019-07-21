import java.util.Scanner;
import java.io.File;

public class Main
{
    public static void main(String[] args)
    {

       Menu();
    // TestMethod();
    }//Main Method

    private static void TestMethod()
    {
       int data = 8;
       int R = 240;
       int G = 130;
       int B = 189;

       int []newRGB = ByteProcessor.MergeByte(data,R,G,B,1);
       int newData = ByteProcessor.GetByte(newRGB,1);
       System.out.println(data+" == " +newData);
    }

    private static void Menu()
    {
        char ch;
        Scanner in = new Scanner(System.in);
        do
        {
            System.out.println("1.ENCRYPT");
            System.out.println("2.DECRYPT");
            System.out.println("3.EXIT");

            ch = in.next().charAt(0);

            switch (ch)
            {
                case '1':
                    Encrypt();
                    break;
                case '2':
                    Decrypt();
                    break;
                case '3':
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        } while (ch != '3');
        in.close();
    }//Menu

    //Encrypt
    private static void Encrypt()
    {
        Scanner in = new Scanner(System.in);
        String ch;
        String mainFile;
        String secretFile;
        String outputFile;
        String password;
        do
        {
            System.out.println("+==========================================================+ ");
            System.out.println("\n#1. Select Face File(Which will be visible to all)");
            System.out.print("  Enter File Name : ");
            mainFile = in.next();
            
            //testing purpose only
            while(!(new File(mainFile).exists()))
            {
            	System.out.println("File not Found :");
            	System.out.print("Re-Enter : ");
            	mainFile = in.next();
            }
            
            System.out.println("\n#2. Select the Secret File(File Which you want to hide)");
            System.out.print("  Enter File Name : ");
            secretFile = in.next();

            //testing purpose only
            while(!(new File(secretFile).exists()))
            {
            	System.out.println("File not Found :");
            	System.out.print("Re-Enter : ");
            	secretFile = in.next();
            }
            
            System.out.println("\n#3. Select the Output File(File Which you want to hide)");
            System.out.print("  Enter File Name : ");
            outputFile = in.next();

            System.out.println("\n#4. Select Password For Encryption (Don't Forget This)");
            System.out.print("  Enter Password : ");
            password = in.next();

            System.out.println("\n---------------------------------");
            System.out.println("\nNOTE:");
            System.out.println("You are Going to Hide \"" + secretFile + "\" in \"" + mainFile + "\"");
            System.out.println("Your Password is : " + password);
            System.out.print("Want to Continue(Y/n)");
            ch = in.next();

        } while (ch.equalsIgnoreCase("n") || ch.equalsIgnoreCase("no"));

        try
        {
            Embedder em = new Embedder(mainFile,secretFile,outputFile,password);
            em.Embed();
            System.out.println("EMBEDDING SUCCESSFULL");
        }
        catch (Exception ex)
        {
        	System.out.println("EMBEDDING FAILED");
        	System.out.println(ex.getMessage());
        }
    }//encrypt

    //Decrypt
    private static void Decrypt()
    {
    	Scanner in = new Scanner(System.in);
    	String ch;
    	String encryptedFile;
    	String outputFile;
    	String password;

	    System.out.println("+==========================================================+ ");
	    System.out.println("\n#1. Select Encrypted File");
	    System.out.print("  Enter File Name : ");
	    encryptedFile = in.next();
	    
	    //testing purpose only
	    while(!(new File(encryptedFile).exists()))
	    {
	    	System.out.println("File not Found :");
	    	System.out.print("Re-Enter : ");
	    	encryptedFile = in.next();
	    }
	    
	    System.out.print("\n  Enter Password : ");
	    password = in.next();

    	try
    	{
    		Extractor et = new Extractor(encryptedFile,password);
    		et.Extract();
    	}
    	catch (Exception ex)
    	{
    		System.out.println("EXTRACTION FAILED");
    		System.out.println(ex.getMessage());
    	}
    	System.out.println("ok bye");
    }//decrypt
}
