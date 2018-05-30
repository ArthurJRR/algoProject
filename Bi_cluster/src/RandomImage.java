import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RandomImage
{
	
	public static final int HEIGHT_BIT = 255;
	
	/**
	 * Register the subMatrix of ChengChurch Algorithm, use for Binary Matrices only
	 * @param sub
	 */
	public static void registerChengMatrixForBinaryMatrix(int[][] mat, String file)
	{
		BufferedImage img = new BufferedImage(mat[0].length*ParentMatrix.PIXEL_HEIGHT, mat.length*ParentMatrix.PIXEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		 for(int i = 1; i <= mat.length; i++)
		 {
			for(int j = 1; j <= mat[i-1].length; j++)
			{
				int a = HEIGHT_BIT;
				int r;
				int g;
				int b;
				if(mat[i-1][j-1] != 0)
				{
					r = 0;
					g = HEIGHT_BIT;
					b = 0;
				}
				else
				{
					r = HEIGHT_BIT;
					g = 0;
					b = 0;
				}
				
				int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
				for(int k = 0; k < ParentMatrix.PIXEL_HEIGHT; k++)
				{
					for(int l = k; l < ParentMatrix.PIXEL_HEIGHT; l++)
					{
						img.setRGB(ParentMatrix.PIXEL_HEIGHT*(j-1) + k, ParentMatrix.PIXEL_HEIGHT*(i-1) + l, p);
						img.setRGB(ParentMatrix.PIXEL_HEIGHT*(j-1) + l, ParentMatrix.PIXEL_HEIGHT*(i-1) + k, p);
					}
					
				}
				
			}
		}
		try
	    {
	       File f = new File(file);
	       ImageIO.write(img, "png", f);
	    }
	    catch(IOException e)
	    {
	       System.out.println("Error: " + e);
	    }
	}
	
	/**
	 * Register the subMatrix of ChengChurch Algorithm, use for Non Binary Matrices
	 * @param sub
	 */
	public static void registerChengMatrixForNonBinaryMatrix(int[][] mat, String file)
	{
		BufferedImage img = new BufferedImage(mat[0].length*ParentMatrix.PIXEL_HEIGHT, mat.length*ParentMatrix.PIXEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		 for(int i = 1; i <= mat.length; i++)
			{
				for(int j = 1; j <= mat[i-1].length; j++)
				{
					int a = HEIGHT_BIT;
					int r;
					int g;
					int b;
					if(mat[i-1][j-1] <= HEIGHT_BIT)
					{
						r = mat[i-1][j-1];
						g = 0;
						b = 0;
					}
					else if(mat[i-1][j-1] <= 2*HEIGHT_BIT)
					{
						r = HEIGHT_BIT;
						g = mat[i-1][j-1];
						b = 0;
					}
					else
					{
						r = HEIGHT_BIT;
						g = HEIGHT_BIT;
						b = mat[i-1][j-1];
					}
					int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
					for(int k = 0; k < ParentMatrix.PIXEL_HEIGHT; k++)
					{
						for(int l = k; l < ParentMatrix.PIXEL_HEIGHT; l++)
						{
							img.setRGB(ParentMatrix.PIXEL_HEIGHT*(j-1) + k, ParentMatrix.PIXEL_HEIGHT*(i-1) + l, p);
							img.setRGB(ParentMatrix.PIXEL_HEIGHT*(j-1) + l, ParentMatrix.PIXEL_HEIGHT*(i-1) + k, p);
						}
						
					}
					
				}
			}
			try
		     {
		       File f = new File(file);
		       ImageIO.write(img, "png", f);
		     }
		     catch(IOException e)
		     {
		       System.out.println("Error: " + e);
		     }
	}
	
	
  public static void main(String args[])throws IOException
  {
	  //Creates a random binary matrix
	 int[][] matrix = new int[100][100];
	 for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				matrix[i][j] = Math.random()< 0.5 ? 0:1;
			}
		}
	 
	 /* Applies the first basic algorithm to the matrix
	  * /!\ changes the matrix, so it's not wise to use chengChurch afterwards
	  * Registers the Initial matrix in Matrix.png
	  * Registers the result of the algorithm in Result.png
	  */
	 
	 /*ClusterMatrix cluster = new ClusterMatrix(matrix);
	 
	 cluster.saveMatrixOnImg(cluster.getImg(), ParentMatrix.IMG_FILE_NAME);
	 cluster.biClusterMatrix();
	 cluster.saveMatrixOnImg(cluster.getImg2(), ParentMatrix.IMG2_FILE_NAME);*/
	 
	 
	 //A matrix used to test the Cheng & Church Algorithm
	 int[][] matrix2 = {{1,0,1,0,0,1,0,1,1,0},
  			 {1,0,1,0,0,0,0,0,1,0},
  			 {1,0,1,0,0,1,0,1,0,0},
  			 {1,0,1,1,0,1,0,1,0,0},
  			 {0,1,1,0,0,1,0,1,0,0},
  			 {0,0,1,0,1,1,0,1,0,0},
  			 {1,1,1,0,1,0,0,0,1,1},
  			 {1,1,0,0,0,1,0,1,0,1},
  			 {1,0,0,1,0,1,0,0,0,0},
  			 {1,1,1,0,0,1,1,0,1,0},
  			 {1,0,1,1,0,1,0,1,0,0},
  			 {1,0,1,1,0,1,0,1,1,0},
  			 {1,1,1,0,0,1,0,1,0,0}};

	 //Loads the matrix from the file "bicluster_yeast" (not binary)
	 int[][] mat = MatrixFromTextFileCreator.createMatrixFromFile("bicluster_yeast");
 
	 ChengChurch cheng = new ChengChurch(mat, 50f);
	 registerChengMatrixForNonBinaryMatrix(cheng.getMatrix(), "Initial_Cheng.png");
	 cheng.deletionPhase();
	 cheng.additionPhase();
	 registerChengMatrixForNonBinaryMatrix(cheng.getMatrix(), "After_cheng.png");
	 
	 int[][] sub = cheng.getSubMatrix();
	 
	 registerChengMatrixForNonBinaryMatrix(sub, "SubMatrix.png");
	 
	 System.out.println("Done, check the images");
	 
	 
  }
}