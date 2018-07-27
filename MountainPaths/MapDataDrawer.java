import java.util.*;
import java.io.*;
import java.awt.*;
 
public class MapDataDrawer
{
 
  private int[][] grid;
 
  public MapDataDrawer(String filename, int rows, int cols){
  	int[][] data = new int[rows][cols];
  	In file = new In(filename);
  	for(int r = 0; r < rows; r++)
  	{
      	for(int c = 0; c < cols; c++)
      	{
          	if(file.hasNextInt())
          	{
              	data[r][c] = file.readInt();
          	}
          	//System.out.print(data[r][c] + " ");
      	}
      	//System.out.println();
  	}
  	grid = data;
  }
 
  /**
   * @return the min value in the entire grid
   */
  public int findMinValue(){
	int min = grid[0][0];
	for(int[] row : grid)
	{
    	for(int col : row)
    	{
        	if(col < min)
        	{
            	min = col;
        	}
    	}
	}
	return min;
  }
  /**
   * @return the max value in the entire grid
   */
  public int findMaxValue(){
	int max = Integer.MIN_VALUE;
	for(int[] row : grid)
	{
    	for(int col : row)
    	{
        	if(col > max)
        	{
            	max = col;
        	}
    	}
	}
	return max;
  }
 
  /**
   * @param col the column of the grid to check
   * @return the index of the row with the lowest value in the given col for the grid
   */
  public  int indexOfMinInCol(int col){
  	int min = grid[0][col];
  	int idx = 0;
  	for(int i = 1; i < grid.length; i++)
  	{
    	if(grid[i][col] < min)
    	{
      	min = grid[i][col];
      	idx = i;
    	}
     	 
    	}
  	return idx;
  }
 
 
  /**
   * Draws the grid using the given Graphics object.
   * Colors should be grayscale values 0-255, scaled based on min/max values in grid
   */
  public void drawMap(Graphics g){
  	int minElv = findMinValue();
  	int maxElv = findMaxValue();
  	int[][] grayArr = new int[grid.length][grid[0].length];
  	double scale = 255.0 / (maxElv - minElv);
  	for (int i = 0; i < grid.length; i++)
  	{
      	for (int j = 0; j < grid[0].length; j++)
      	{
          	grayArr[i][j] = (int) ((grid[i][j] - minElv) * scale);
      	}
  	}
 	 
  	for (int i = 0; i < grayArr.length; i++)
  	{
      	for (int j = 0; j < grayArr[0].length; j++)
      	{
          	int value = grayArr[i][j];
          	g.setColor(new Color(value, value, value));
          	g.fillRect(j, i, 1, 1);
      	}
  	}
  }
 
  public boolean atTop(int row)
  {
  	return row == 0;
  }
  public boolean atRight(int col)
  {
  	return col == grid[0].length - 1;
  }
  public boolean atLeft(int col)
  {
  	return col == 0;
  }
  public boolean atBottom(int row)
  {
  	return row == grid.length -1;
  }
 
   /**
   * Find a path from West-to-East starting at given row.
   * Choose a foward step out of 3 possible forward locations, using greedy method described in assignment.
   * @return the total change in elevation traveled from West-to-East
   */
  public int drawLowestElevPath(Graphics g, int row){
  	//check for the conditionals that check what part of the map youre on. Check for if youre at top, and what options you have.
  	//if youre at bottom, what options you have.
  	// if youre at top and bottom, what options, if neither is true, them what opyions
 	int max = findMaxValue();
 	int totalChange = 0;
	 
 	for (int col = 0; col < grid[0].length - 1; col++)
 	{
     	g.fillRect(col, row, 1, 1);
     	int fwd = grid[row][col + 1];
     	int fwdUp = -1;
     	int fwdDown = -1;
    	System.out.print(grid[row][col]+"  ");
     	if (!atTop(row))
     	{
         	fwdUp = grid[row - 1][col + 1];
     	}
    	 
     	if (!atBottom(row))
     	{
         	fwdDown = grid[row + 1][col + 1];
     	}
    	 
     	int fwdDiff = Math.abs(grid[row][col] - fwd);
     	int fwdUpDiff = max + 1;
     	int fwdDownDiff = max + 1;
     	if (fwdUp > -1)
     	{
         	fwdUpDiff = Math.abs(grid[row][col] - fwdUp);
     	}
     	if (fwdDown > -1)
     	{
         	fwdDownDiff = Math.abs(grid[row][col] - fwdDown);
     	}
         	 
     	int leastElev = fwdDiff;
    	 
     	if(fwdDiff > fwdUpDiff)
     	{
         	if(fwdUpDiff > fwdDownDiff)
         	{
             	leastElev = fwdDownDiff;
             	row++;
         	}
         	else
         	{
             	leastElev = fwdUpDiff;
             	row--;
         	}
     	}
     	else
     	{
         	if(fwdDiff > fwdDownDiff)
         	{
             	leastElev = fwdDownDiff;
             	row++;
         	}
         	else
         	{
             	leastElev = fwdDiff;
         	}
     	}
     	totalChange += leastElev;
 	}
 	return totalChange;
  }
 
 
 
  /**
   * @return the index of the starting row for the lowest-elevation-change path in the entire grid.
   */
  public int indexOfLowestElevPath(Graphics g){
 	int idx = 0;
 	int lowestElev = drawLowestElevPath(g, 0);
 	for(int i = 1; i < grid.length; i++)
 	{
     	int otherLowestElev = drawLowestElevPath(g, i);
     	if(otherLowestElev < lowestElev)
     	{
         	idx = i;
         	lowestElev = otherLowestElev;
     	}
 	}
 	return idx;
  }
  // draw down hill path: practitioner
  public void downHillPath(Graphics g, int row)
  {
 	int curElev = grid[row][0];
 	int fwd = 0;
 	int fwdDown = 0;
 	int fwdUp = 0;
 	int min = findMinValue();
 	for(int col = 0; col < grid[0].length - 1; col++)
 	{
    	g.fillRect(col, row, 1, 1);
    	fwd = min -1;
    	fwdDown = min - 1;
    	fwdUp = min - 1;
    	if(!atRight(col))
    	{
        	fwd = grid[row][col+1];
    	}
    	if(!atBottom(row))
    	{
       	fwdDown = grid[row+1][col+1];
    	}
    	if(!atTop(row))
    	{
        	fwdUp = grid[row-1][col+1];
    	}
    	//options of movement
    	int leastElev = fwd;
    	 
    	if(fwd > fwdUp)
    	{
         	if(fwdUp > fwdDown)
         	{
             	leastElev = fwdDown;
             	row++;
         	}
         	else
         	{
             	leastElev = fwdUp;
             	row--;
         	}
    	}
    	else
    	{
         	if(fwd > fwdDown)
         	{
             	leastElev = fwdDown;
             	row++;
         	}
         	else
         	{
             	leastElev = fwd;
         	}
    	}
  	}
  }
 
  // highlight lowest elevation in each column
  public void lowestElevInCol(Graphics g)
  {
  	for(int col = 0; col < grid[0].length; col++)
  	{
      	int row = indexOfMinInCol(col);
      	g.fillRect(col, row, 1, 1);
  	}
  }
 
  //professional
  // greedy walk east to west
  public void westToEast(Graphics g, int x, int y)
  {
 	int max = findMaxValue();
 	int totalChange = 0;
 	int row = y;
 	for (int col = x; col < grid[0].length-1; col++)
 	{
     	g.fillRect(col, row, 1, 1);
     	int fwd = grid[row][col + 1];
     	int fwdUp = -1;
     	int fwdDown = -1;
    	 
     	if (!atTop(row))
     	{
         	fwdUp = grid[row - 1][col + 1];
     	}
    	 
     	if (!atBottom(row))
     	{
         	fwdDown = grid[row + 1][col + 1];
     	}
    	 
     	int fwdDiff = Math.abs(grid[row][col] - fwd);
     	int fwdUpDiff = max + 1;
     	int fwdDownDiff = max + 1;
     	if (fwdUp > -1)
     	{
         	fwdUpDiff = Math.abs(grid[row][col] - fwdUp);
     	}
     	if (fwdDown > -1)
     	{
         	fwdDownDiff = Math.abs(grid[row][col] - fwdDown);
     	}
         	 
     	int leastElev = fwdDiff;
    	 
     	if(fwdDiff > fwdUpDiff)
     	{
         	if(fwdUpDiff > fwdDownDiff)
         	{
             	leastElev = fwdDownDiff;
             	row++;
         	}
         	else
         	{
             	leastElev = fwdUpDiff;
             	row--;
         	}
     	}
     	else
     	{
         	if(fwdDiff > fwdDownDiff)
         	{
             	leastElev = fwdDownDiff;
             	row++;
         	}
         	else
         	{
             	leastElev = fwdDiff;
         	}
     	}
 	}
  }
 
  //westToEast
  public void eastToWest(Graphics g, int x, int y)
  {
 	int max = findMaxValue();
 	int totalChange = 0;
 	int row = y;
 	for (int col = x; col > 1; col--)
 	{
     	g.fillRect(col, row, 1, 1);
     	int fwd = grid[row][col - 1];
     	int fwdUp = -1;
     	int fwdDown = -1;
    	 
     	if (!atTop(row))
     	{
         	fwdUp = grid[row - 1][col - 1];
     	}
    	 
     	if (!atBottom(row))
     	{
         	fwdDown = grid[row + 1][col - 1];
     	}
    	 
     	int fwdDiff = Math.abs(grid[row][col] - fwd);
     	int fwdUpDiff = max + 1;
     	int fwdDownDiff = max + 1;
     	if (fwdUp > -1)
     	{
         	fwdUpDiff = Math.abs(grid[row][col] - fwdUp);
     	}
     	if (fwdDown > -1)
     	{
         	fwdDownDiff = Math.abs(grid[row][col] - fwdDown);
     	}
         	 
     	int leastElev = fwdDiff;
    	 
     	if(fwdDiff > fwdUpDiff)
     	{
         	if(fwdUpDiff > fwdDownDiff)
         	{
             	leastElev = fwdDownDiff;
             	row++;
         	}
         	else
         	{
             	leastElev = fwdUpDiff;
             	row--;
         	}
     	}
     	else
     	{
         	if(fwdDiff > fwdDownDiff)
         	{
             	leastElev = fwdDownDiff;
             	row++;
         	}
         	else
         	{
             	leastElev = fwdDiff;
         	}
     	}
 	}
  }
 
  public void drawXY(Graphics g)
  {
  	int x = (int)(Math.random()*grid[0].length);
  	int y = (int)(Math.random()*grid.length);
  	g.setColor(Color.WHITE);
  	eastToWest(g, x, y);
  	g.setColor(Color.PINK);
  	westToEast(g, x, y);
  }
}
