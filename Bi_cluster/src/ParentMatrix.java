import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ParentMatrix 
{
	protected int[][] matrix;
	
	protected BufferedImage img;
    protected BufferedImage img2;
    
    protected static final int PIXEL_HEIGHT = 10;
    public static final String IMG_FILE_NAME = "Matrix.png";
    public static final String IMG2_FILE_NAME = "Result.png";
    
    public void saveMatrixOnImg(BufferedImage img, String fileName)
	{
		for(int i = 1; i <= matrix.length; i++)
		{
			for(int j = 1; j <= matrix[i-1].length; j++)
			{
				int a = 255;
				int r = 255;
				int g = 0;
				int b = 0;
				if(matrix[i-1][j-1] != 0)
				{
					r = 0;
					g = 255;
					b = 0;
				}
				int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
				for(int k = 0; k < PIXEL_HEIGHT; k++)
				{
					for(int l = k; l < PIXEL_HEIGHT; l++)
					{
						img.setRGB(PIXEL_HEIGHT*(j-1) + k, PIXEL_HEIGHT*(i-1) + l, p);
						img.setRGB(PIXEL_HEIGHT*(j-1) + l, PIXEL_HEIGHT*(i-1) + k, p);
					}
					
				}
				
			}
		}
		try
	     {
	       File f = new File(fileName);
	       ImageIO.write(img, "png", f);
	     }
	     catch(IOException e)
	     {
	       System.out.println("Error: " + e);
	     }
	
	}
    
    public BufferedImage getImg()
	{
		return img;
	}
	public BufferedImage getImg2()
	{
		return img2;
	}

}
