import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public class Embedder
{
    private File vessel;
    private File secretFile;
    private File outputFile;
    private SecurityManager passwordMan;
    
    public Embedder(String vessel,String secretFile,String outputFile,String password) throws Exception
    {
        this.vessel = new File(vessel);
        if(!this.vessel.exists() || !this.vessel.isFile())
        	throw new Exception("File not Found : "+vessel);

        this.secretFile = new File(secretFile);
        if(!this.secretFile.exists() || !this.secretFile.exists())
        	throw new Exception("File not Found : "+secretFile);

        this.outputFile = new File(outputFile);
        this.passwordMan = new SecurityManager(password);
    }

    //Embed
    public boolean Embed()
    {
        try
        {
        	//reading srcImage and vessel
            BufferedImage srcImage = ImageIO.read(vessel);
            WritableRaster wRaster = srcImage.getRaster();
            FileInputStream toEmbed = new FileInputStream(secretFile);

            int width = srcImage.getWidth();
            int height = srcImage.getHeight();
            
            //forming header of secretFile
            int hdrLength = HeaderManager.HEADER_LENGTH;
            String Header = HeaderManager.FormHeader(secretFile);

            //calculate embedding capacity of file
            long embeddingCapacity = width*height - hdrLength;
            long filetoEmbedSize = secretFile.length();
            if(embeddingCapacity < filetoEmbedSize)
            	throw new Exception("Embedding capacity exceeded");

            int index = 0;
            int data;
            int flag = passwordMan.GetPermutation();
        	boolean keepEmbedding = true;

            //accessing raster of vessel
            for (int i = 0; i < width && keepEmbedding; ++i)
            {
                for (int j = 0; j < height; ++j)
                {
                    if(index < hdrLength) //insert header
                        data = Header.charAt(index);
                    else //insert data
                        data = toEmbed.read();

                    if(data == -1)
                    {
                    	keepEmbedding =false;
                    	break;
                    }
                    //encrypt data
                    data = passwordMan.PrimaryCrypto(data);
                    
                    //get Bands
                    int R = wRaster.getSample(i, j, 0);
                    int G = wRaster.getSample(i, j, 1);
                    int B = wRaster.getSample(i, j, 2);

                    int newRGB[] = ByteProcessor.MergeByte(data,R,G,B,flag);

                    //setBands
                    wRaster.setSample(i,j,0,newRGB[0]);
                    wRaster.setSample(i,j,1,newRGB[1]);
                    wRaster.setSample(i,j,2,newRGB[2]);

                    ++index;
                    
                }//inner for

            }//outer for


            ImageIO.write(srcImage, "PNG", outputFile);
            
            toEmbed.close();
            return true;
        }//try
        catch (Exception e)
        {
            System.out.println("Embedding Failed due to Some Reasons Try Again!");
            System.out.println("Error: "+ e.getMessage());
            return false;
        }//catch
    }//Embedd
}
