import java.awt.image.BufferedImage;

public class ChengChurch extends ParentMatrix
{
	
	private int numberOfActiveColumns;
	private int numberOfActiveRows;
	private float threshold;
	
	public ChengChurch(int[][] matrix, float threshold)
	{
		this.matrix = matrix;
		this.threshold = threshold;
		this.numberOfActiveRows = matrix.length;
		this.numberOfActiveColumns = matrix[0].length;
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
		float columnSubAverage = calculateColumnSubsetAverage(j);
		float rowSubAverage = calculateRowSubsetAverage(i);
		float subMatrixAverage = calculateSubmatrixAverage();
		return matrix[i][j] - columnSubAverage - rowSubAverage + subMatrixAverage;
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
		return result;
	}
	
	public void deletionPhase()
	{
		float H = calculateResiduScoreMatrix();
		int maxRowIndex = 0;
		float maxRowScore = 0;
		
		int maxColumnIndex = 0;
		float maxColumnScore = 0;
		System.out.println(H);
		while(H > threshold)
		{
			for(int i = 0; i < numberOfActiveRows; i++)
			{
				float score = calculateRowResiduScore(i);
				if(score > maxRowScore)
				{
					maxRowScore = score;
					maxRowIndex = i;
				}
			}
			
			for(int j = 0; j < numberOfActiveColumns; j++)
			{
				float score = calculateColumnResiduScore(j);
				if(score > maxColumnScore)
				{
					maxColumnScore = score;
					maxColumnIndex = j;
				}
			}
			
			if(maxColumnScore > maxRowScore)
			{
				swapColumns(maxColumnIndex, numberOfActiveColumns - 1);
				numberOfActiveColumns -= 1;
			}
			else
			{
				swapRows(maxRowIndex, numberOfActiveRows - 1);
				numberOfActiveRows -= 1;
			}
			
			H = calculateResiduScoreMatrix();
			
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

}
