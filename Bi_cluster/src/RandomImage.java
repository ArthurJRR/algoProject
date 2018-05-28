import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RandomImage{
	
  public static void main(String args[])throws IOException
  {
	 /*int[][] matrix = new int[500][500];
	 for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				matrix[i][j] = Math.random()< 0.5 ? 0:1;
			}
		}
	 
	 ClusterMatrix cluster = new ClusterMatrix(matrix);*/
	 
	/* cluster.saveMatrixOnImg(cluster.getImg(), ParentMatrix.IMG_FILE_NAME);
	 cluster.biClusterMatrix();
	 cluster.saveMatrixOnImg(cluster.getImg2(), ParentMatrix.IMG2_FILE_NAME);*/
	 
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

	 int[][] mat = MatrixFromTextFileCreator.createMatrixFromFile("bicluster_yeast");
 
	 ChengChurch cheng = new ChengChurch(mat, 610f);
	 cheng.saveMatrixOnImg(cheng.getImg(), "Initial_Cheng.png");
	 cheng.deletionPhase();
	 //cheng.additionPhase();
	 cheng.saveMatrixOnImg(cheng.getImg2(), "After_cheng.png");
	 System.out.println("Submatrix");
	 System.out.println("");
	 System.out.println("");
	 cheng.displaySubMatrix();
	 
	 int[][] sub = cheng.getSubMatrix();
	 System.out.println("lignes " +  sub.length + " colonne " + sub[0].length);
	 BufferedImage img = new BufferedImage(sub[0].length*ParentMatrix.PIXEL_HEIGHT, sub.length*ParentMatrix.PIXEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	 for(int i = 1; i <= sub.length; i++)
		{
			for(int j = 1; j <= sub[i-1].length; j++)
			{
				int a = 255;
				int r;
				int g;
				int b;
				if(sub[i-1][j-1] < 255)
				{
					r = sub[i-1][j-1];
					g = 0;
					b = 0;
				}
				else
				{
					r = 255;
					g = sub[i-1][j-1] - 255;
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
	       File f = new File("SubMatrix.png");
	       ImageIO.write(img, "png", f);
	     }
	     catch(IOException e)
	     {
	       System.out.println("Error: " + e);
	     }
	 
  }
}