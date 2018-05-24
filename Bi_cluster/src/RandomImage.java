import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class RandomImage{
	
  public static void main(String args[])throws IOException{
	  
     //image dimension
     int width = 100;
     int height = 300;
     
     //create buffered image object img
     BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
     BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
     
     //file object
     File f = null;
     File f2 = null;
     
     //CREATE Matrice
     int [][][] data = new int[width][height][];
     
     //create random image pixel by pixel
     for(int y = 0; y < height; y++)
     {
       for(int x = 0; x < width; x++)
       {
    	 int a = (int)(255); //alpha On ne gère pas la transparence 
         int r = (int)(Math.random()*256); //red
         int g = (int)(Math.random()*256); //green
         int b = (int)(Math.random()*256); //blue
         
         //Generation Noir/Blanc
         if (Math.random() >= 0.5)
         {
        	 a = 255;
        	 r = 255;
        	 g = 255;
        	 b = 255;
         }
         else
         {
        	 a = 255;
        	 r = 0;
        	 g = 0;
        	 b = 0;
         }
 
         int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
 
         img.setRGB(x, y, p);

         //Set Matrice
         int MOYENNE = (int)( (r + g + b) / 3);
         int[] pixel = {r,g,b,a,MOYENNE};
         data[x][y] = pixel;
         
       }
     }
     
     //Sort Matrice
     for(int y = 0; y < height; y++)
     {
    	 int i, j;
    	 for (i = 1; i < width; ++i) 
    	 {
    	    int elem = data[i][y][4];
    	    for (j = i; j > 0 && data[i-1][y][4] > elem; j--)
    	    {
    	    	data[j][y] = data[j-1][y];
    	    }
    	    //data[j][y][4] = elem;
    	 }
     }
     
     //write random image
     try
     {
       f = new File("Matrix.png");
       ImageIO.write(img, "png", f);
     }
     catch(IOException e)
     {
       System.out.println("Error: " + e);
     }
     
     //Write sort image
     for(int y = 0; y < height; y++)
     {
       for(int x = 0; x < width; x++)
       {
    	   int p = (data[x][y][3]<<24) | (data[x][y][0]<<16) | (data[x][y][1]<<8) | data[x][y][2];
    	   img2.setRGB(x, y, p);
       }
     }
     
     try
     {
       f2 = new File("Result.png");
       ImageIO.write(img2, "png", f2);
     }
     catch(IOException e)
     {
       System.out.println("Error: " + e);
     }
     
  }
}