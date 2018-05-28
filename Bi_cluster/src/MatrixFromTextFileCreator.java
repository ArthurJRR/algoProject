import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class MatrixFromTextFileCreator 
{
	
	public static int[][] createMatrixFromFile(String path)
	{
		int[][] mat = new int[2884][17];
		try(Scanner scan = new Scanner(FileSystems.getDefault().getPath(path)))
		{
			int i = 0;
			while(scan.hasNextInt())
			{
				for(int j = 0; j < 17; j++)
				{
					mat[i][j] = scan.nextInt();
				}
				i++;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return mat;
	}

}
