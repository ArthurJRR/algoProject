import java.io.IOException;

public class RandomImage{
	
  public static void main(String args[])throws IOException
  {
	 int[][] matrix = new int[30][30];
	 for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				matrix[i][j] = Math.random()< 0.5 ? 0:1;
			}
		}
	 
	 ClusterMatrix cluster = new ClusterMatrix(matrix);
	 
	 cluster.saveMatrixOnImg(cluster.getImg(), ClusterMatrix.IMG_FILE_NAME);
	 cluster.biClusterMatrix();
	 cluster.saveMatrixOnImg(cluster.getImg2(), ClusterMatrix.IMG2_FILE_NAME);
	 
  }
}