import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class MatrixFromTextFileCreator 
{
	
	public static int[][] createMatrixFromFile(String path)
	{		
		int rowNumber=0;
		int columnsNumber=0;
		
		try(Scanner scan = new Scanner(FileSystems.getDefault().getPath(path))) {
			String line= scan.nextLine();
			/*for (String i : line.split(" ")) {
				System.out.print("a"+i+"b"+" ");
			}*/
			rowNumber+=1;
			columnsNumber=line.split(" ").length;
			while(scan.hasNextLine()) {
				scan.nextLine();
				rowNumber+=1;
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int[][] mat = new int[rowNumber][columnsNumber];
		
		//System.out.println(rowNumber+" "+ columnsNumber);
		
		try(Scanner scan = new Scanner(FileSystems.getDefault().getPath(path)))
		{
			int i = 0;
			while(scan.hasNextInt())
			{
				for(int j = 0; j < columnsNumber; j++)
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
