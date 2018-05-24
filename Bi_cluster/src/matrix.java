
public class matrix {

	public int width;
	public int height;
	public int [][] data;
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int[][] getData() {
		return data;
	}
	public void setData(int[][] data) {
		this.data = data;
	}
	
	public matrix(int width, int height) 
	{
		this.width = width;
		this.height = height;
		this.data =  new int[width][height];
		
	}
	
	
}
