import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class ChengChurch extends ParentMatrix
{
	
	private int numberOfActiveColumns;
	private int numberOfActiveRows;
	private float threshold;
	private float residuScoreMatrix;
	private float subMatrixAverage;
	private float[] rowSubsetAverage;
	private float[] columnSubsetAverage;
	
	public ChengChurch(int[][] matrix, float threshold)
	{
		this.matrix = matrix;
		this.threshold = threshold;
		this.numberOfActiveRows = matrix.length;
		this.numberOfActiveColumns = matrix[0].length;
		rowSubsetAverage = new float[numberOfActiveRows];
		columnSubsetAverage = new float[numberOfActiveColumns];
		img = new BufferedImage(matrix[0].length*PIXEL_HEIGHT, matrix.length*PIXEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	    img2 = new BufferedImage(matrix[0].length*PIXEL_HEIGHT, matrix.length*PIXEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	}
	
	
	//Correspond à eIj dans tuto
	private float calculateRowSubsetAverage(int i)
	{
		float result = 0;
		for(int j = 0; j < numberOfActiveColumns; j++)
		{
			result += matrix[i][j];
		}
		
		result = result / numberOfActiveColumns;
		
		return result;
	}
	
	//Correspond à eiJ
	private float calculateColumnSubsetAverage(int j)
	{
		float result = 0;
		for(int i = 0; i < numberOfActiveRows; i++)
		{
			result += matrix[i][j];
		}
		
		result = result / numberOfActiveRows;
		
		return result;
	}
	
	//correspond à eIJ
	private float calculateSubmatrixAverage()
	{
		float result = 0;
		for(int i = 0; i < numberOfActiveRows; i++)
		{
			for(int j = 0; j < numberOfActiveColumns; j++)
			{
				result += matrix[i][j];
			}
		}
		result = result / (numberOfActiveColumns * numberOfActiveRows);
		return result;
	}
	
	//Correspond à RS(i,j)
	private float calculateResiduScoreElement(int i, int j)
	{
		int element = matrix[i][j];
		float columnSubAverage = columnSubsetAverage[j];
		float rowSubAverage = rowSubsetAverage[i];
		float subMatrixAverage = this.subMatrixAverage;
		return element - columnSubAverage - rowSubAverage + subMatrixAverage;
	}
	
	//correspond à H(I,J)
	private float calculateResiduScoreMatrix()
	{
		float result = 0;
		for(int i = 0; i < numberOfActiveRows; i++)
		{
			for(int j = 0; j < numberOfActiveColumns; j++)
			{
				result += Math.pow(calculateResiduScoreElement(i, j), 2);
			}
		}
		
		result = result / (numberOfActiveColumns * numberOfActiveRows);
		return result;
	}
	
	//Correspond à e(j)
	private float calculateColumnResiduScore(int j)
	{
		float result = 0;
		for(int i = 0; i < numberOfActiveRows; i++)
		{
			result += calculateResiduScoreElement(i, j);
		}
		result = result / numberOfActiveRows;
		//System.out.println("Score résidu de la colonne " + j + " : " + result);
		return result;
	}
	
	//Correspond à d(i)
	private float calculateRowResiduScore(int i)
	{
		float result = 0;
		for(int j = 0; j < numberOfActiveColumns; j++)
		{
			result += calculateResiduScoreElement(i, j);
		}
		result = result / numberOfActiveColumns;
		//System.out.println("Score résidu de la ligne " + i + " : " + result);
		return result;
	}
	
	public void deletionPhase()
	{
		precalculateUsefulValues();
		residuScoreMatrix = calculateResiduScoreMatrix();
		
		System.out.println(residuScoreMatrix);
		while(residuScoreMatrix > threshold)
		{
			
			int maxRowIndex = 0;
			float maxRowScore = -10;
			
			int maxColumnIndex = 0;
			float maxColumnScore = -10;
			for(int i = 0; i < numberOfActiveRows; i++)
			{
				float score = calculateRowResiduScore(i);
				if((score) >(maxRowScore))
				{
					maxRowScore = score;
					maxRowIndex = i;
				}
			}
			
			for(int j = 0; j < numberOfActiveColumns; j++)
			{
				float score = calculateColumnResiduScore(j);
				if(score > (maxColumnScore))
				{
					maxColumnScore = score;
					maxColumnIndex = j;
				}
			}
			
			if((maxColumnScore) >= (maxRowScore))
			{
				swapColumns(maxColumnIndex, numberOfActiveColumns - 1);
				numberOfActiveColumns -= 1;
			}
			else
			{
				swapRows(maxRowIndex, numberOfActiveRows - 1);
				numberOfActiveRows -= 1;
			}
			
			precalculateUsefulValues();
			residuScoreMatrix = calculateResiduScoreMatrix();
			System.out.println("H " + residuScoreMatrix);
			
		}
	}
	
	public void additionPhase() 
	{
		precalculateUsefulValues();
		residuScoreMatrix = calculateResiduScoreMatrix();
		
		for(int j = numberOfActiveColumns; j < matrix[0].length; j++)
		{
			if(calculateColumnSquareScoreElement(j) < residuScoreMatrix)
			{
				swapRows(j, numberOfActiveColumns);
				numberOfActiveColumns += 1;
				System.out.println("Addition columns ! : swapped " + j + " with " + numberOfActiveColumns);
			}
		}
		
		precalculateUsefulValues();
		residuScoreMatrix = calculateResiduScoreMatrix();
		
		for(int i = numberOfActiveRows; i < matrix.length; i++)
		{
			if(calculateRowSquareScoreElement(i) < residuScoreMatrix)
			{
				swapColumns(i, numberOfActiveRows);
				numberOfActiveRows += 1;
				System.out.println("Addition rows ! : swapped " + i + " with " + numberOfActiveRows);
			}
		}
		
		
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
	
	public void displayMatrix()
	{
		for(int i = 1; i <= matrix.length; i++)
		{
			for(int j = 1; j <= matrix[i-1].length; j++)
			{
				System.out.print(matrix[i-1][j-1]);
			}
			System.out.println("");
		}
	}
	
	public void displaySubMatrix()
	{
		for(int i = 1; i <= numberOfActiveRows; i++)
		{
			for(int j = 1; j <= numberOfActiveColumns; j++)
			{
				System.out.print(matrix[i-1][j-1]);
			}
			System.out.println("");
		}
	}

	
	public int[][] getSubMatrix()
	{
		int[][] sub = new int[numberOfActiveRows][numberOfActiveColumns];
		for(int i = 0; i < numberOfActiveRows; i++)
		{
			for(int j = 0; j < numberOfActiveColumns; j++)
			{
				sub[i][j] = matrix[i][j];
			}
		}
		
		return sub;
	}
	
	/**
	 * Used to precalculate the subMatrixAverage, the rowSubsetAverages and the columnSubsetAverages
	 * They are then stored to avoid calculating them more than once
	 */
	private void precalculateUsefulValues()
	{
		subMatrixAverage = calculateSubmatrixAverage();
		for(int i = 0; i < numberOfActiveRows; i++)
		{
			rowSubsetAverage[i] = calculateRowSubsetAverage(i);
		}
		for(int j = 0; j < numberOfActiveColumns; j++)
		{
			columnSubsetAverage[j] = calculateColumnSubsetAverage(j);
		}
	}
	
	
	/**
	 * Used in the addition phase only
	 * @param i
	 * @return
	 */
	private float calculateRowSquareScoreElement(int i)
	{
		float result = 0;
		for(int j = 0; j < numberOfActiveColumns; j++)
		{
			result += Math.pow(matrix[i][j] - rowSubsetAverage[i] - columnSubsetAverage[j] + subMatrixAverage, 2);
		}
		
		return result / numberOfActiveColumns;
	}
	/**
	 * Used in the Addition phase only
	 * @param j
	 * @return
	 */
	private float calculateColumnSquareScoreElement(int j)
	{
		float result = 0;
		for(int i = 0; i < numberOfActiveRows; i++)
		{
			result += Math.pow(matrix[i][j] - rowSubsetAverage[i] - columnSubsetAverage[j] + subMatrixAverage, 2);
		}
		return result / numberOfActiveRows;
	}
	
	
}
