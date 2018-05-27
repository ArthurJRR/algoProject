import java.io.IOException;

public class RandomImage{
	
  public static void main(String args[])throws IOException
  {
	 int[][] matrix = new int[80][80];
	 for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				matrix[i][j] = Math.random()< 0.5 ? 0:1;
			}
		}
	 
	 ClusterMatrix cluster = new ClusterMatrix(matrix);
	 
	/* cluster.saveMatrixOnImg(cluster.getImg(), ParentMatrix.IMG_FILE_NAME);
	 cluster.biClusterMatrix();
	 cluster.saveMatrixOnImg(cluster.getImg2(), ParentMatrix.IMG2_FILE_NAME);*/
	 
	 ChengChurch cheng = new ChengChurch(matrix, 0.21f);
	 cheng.saveMatrixOnImg(cheng.getImg(), "Initial_Cheng.png");
	 cheng.deletionPhase();
	 //cheng.additionPhase();
	 cheng.saveMatrixOnImg(cheng.getImg2(), "After_cheng.png");
	 System.out.println("Submatrix");
	 System.out.println("");
	 System.out.println("");
	 cheng.displaySubMatrix();
	 
  }
}