import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

class Extractor
{
	private File encryptedFile;
	private SecurityManager passwordMan;
	
	public Extractor(String encryptedFile,String password) throws Exception
	{
	    this.encryptedFile = new File(encryptedFile);
	    if(!this.encryptedFile.exists() || !this.encryptedFile.isFile())
	    	throw new Exception("File not Found : "+encryptedFile);

	    this.passwordMan = new SecurityManager(password);
	}

	//Extract
	public void Extract()
	{
	    try
	    {
	    	//reading srcImage and vessel
	        BufferedImage srcImage = ImageIO.read(encryptedFile);
	        WritableRaster wRaster = srcImage.getRaster();

	        int width = srcImage.getWidth();
	        int height = srcImage.getHeight();
	        
	        int index = 0;
	        int data;
	        int flag = passwordMan.GetPermutation();
	    	boolean keepExtracting = true;

	    	//acess metadata of file to be extracted
	    	int hdrLength = HeaderManager.HEADER_LENGTH;
	    	String hdr = "";
	    	FileOutputStream toExtract = null;
	    	int fSize=0;

	        //accessing raster of vessel
	        for (int i = 0; i < width && keepExtracting; ++i)
	        {
	            for (int j = 0; j < height; ++j)
	            {

	            	//get Bands
                    int R = wRaster.getSample(i, j, 0);
                    int G = wRaster.getSample(i, j, 1);
                    int B = wRaster.getSample(i, j, 2);

                    //getByte
                    data = ByteProcessor.GetByte(new int[]{R,G,B},flag);
                    data = passwordMan.PrimaryCrypto(data);

	            	if(index < hdrLength)//extract header
	            	{
	            		hdr += (char)data;

	            		if(index == hdrLength -1)
	            		{//we have the full header now extract the meta data
	            			toExtract = new FileOutputStream(HeaderManager.GetName(hdr));
	            			fSize = (int)HeaderManager.GetSize(hdr);
	            		}
	            	}//if
	            	else
	            	{
	            		toExtract.write(data);

	            		if(index == HeaderManager.HEADER_LENGTH + fSize -1)
	            		{//EOF
	            			toExtract.close();
	            			keepExtracting = false;
	            			break;
	            		}
	            	}//else
	            	++index;
	            }//inner for

	        }//outer for

	    }//try
	    catch (Exception e)
	    {
	        System.out.println("Extraction Failed due to Some Reasons Try Again!");
	        System.out.println("Error: "+ e.getMessage());
	    }//catch
	}//Embedd

}