import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Guillaume_2
 * In this class, we assume that the matrix is only filled with 0s and 1s
 */
public class ClusterMatrix extends ParentMatrix
{
	
	private int[] occInRows;//The Key represents the number of the row, the value represents the number of 1 in this row
	private int[] occInColumns;//The Key represents the number of the column, the value represents the number of 1 in this column
    
    
	
	public ClusterMatrix(int[][] matrix)
	{
		this.matrix = matrix;
		occInRows = new int[matrix.length];
		occInColumns = new int[matrix[0].length];
		img = new BufferedImage(matrix[0].length*PIXEL_HEIGHT, matrix.length*PIXEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	    img2 = new BufferedImage(matrix[0].length*PIXEL_HEIGHT, matrix.length*PIXEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	}
	
	
	/**
	 * Applies a simple biclustering method to the matrix :
	 * The method is as follow : every single row and column gets a score depending on how many 1 they have.
	 * Once the score is settled, the rows and columns are reorganized, ordered by their score in a descending way
	 */
	public void biClusterMatrix()
	{
		for(int i = 1; i <= matrix.length; i++)
		{
			for(int j = 1; j <= matrix[i-1].length; j++)
			{
				//Fill the occInRows and occInColumns TreeMaps
				if(matrix[i-1][j-1] != 0)
				{
					occInRows[i-1] += 1;
					occInColumns[j-1] += 1;
				}
			}
			System.out.println("");
		}
		orderColumns();
		orderRows();
	}
	
	/**
	 * A method to swap two rows of the matrix, meant to be private after tests are done
	 * @param i, the ith row
	 * @param j, the jth row
	 */
	private void swapRows(int i, int j)
	{
		int[] rowI = matrix[i];
		int[] rowJ = matrix[j];
		
		matrix[i] = rowJ;
		matrix[j] = rowI;
	}
	
	/**
	 * A method to swap two rows of the matrix, meant to be private after tests are done
	 * @param i, the ith column
	 * @param j, the jth column
	 */
	private void swapColumns(int i, int j)
	{
		int numberInColumnI;
		int numberInColumnJ;
		
		for(int k =0; k < matrix.length; k++)//For on the number on rows
		{
			numberInColumnI = matrix[k][i];
			numberInColumnJ = matrix[k][j];
			
			matrix[k][i] = numberInColumnJ;
			matrix[k][j] = numberInColumnI;
		}
	}
	
	public void printOccInLists()
	{
		System.out.println("occInRows");
		for(int i = 0; i < occInRows.length; i++)
		{
			System.out.println(occInRows[i]);
		}
		System.out.println("occInColumns");
		for(int j = 0; j < occInColumns.length; j++)
		{
			System.out.println(occInColumns[j]);
		}
		
	}
	
	private void orderRows()
	{
		//order the rows depending on the score of each row
		int passage = 0;
		boolean hasSwap = true;
		while(hasSwap)
		{
			hasSwap = false;
			for(int i = 0; i < occInRows.length - 1 - passage; i++)
			{
				if(occInRows[i] < occInRows[i+1])
				{
					int temp = occInRows[i];
					occInRows[i] = occInRows[i+1];
					occInRows[i+1] = temp;
					
					swapRows(i, i+1);
					
					hasSwap = true;
				}
			}
			passage++;
		}
		//In case the rows have the same score :
		
	}
	
	private void orderColumns()
	{
		int passage = 0;
		boolean hasSwap = true;
		while(hasSwap)
		{
			hasSwap = false;
			for(int i = 0; i < occInColumns.length - 1 - passage; i++)
			{
				if(occInColumns[i] < occInColumns[i+1])
				{
					int temp = occInColumns[i];
					occInColumns[i] = occInColumns[i+1];
					occInColumns[i+1] = temp;
					
					swapColumns(i, i+1);
					
					hasSwap = true;
				}
			}
			passage++;
		}
	}
	

	
}
