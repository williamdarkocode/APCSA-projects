import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  public void keepOnlyBlue()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] row : pixels)
      {
          for(Pixel col : row)
          {
              col.setRed(0);
              col.setGreen(0);
          }
      }
  }
  
  public void negate()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] row : pixels)
      {
          for(Pixel col : row)
          {
              col.setRed(255-col.getRed());
              col.setBlue(255-col.getBlue());
              col.setGreen(255-col.getGreen());
          }
      }
  }
  
  public void grayScale()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] row : pixels)
      {
          for(Pixel col : row)
          {
              int avg = (col.getRed()+col.getGreen()+col.getBlue())/3;
              col.setRed(avg);
              col.setBlue(avg);
              col.setGreen(avg);
          }
      }
  }
  
  public void fixUnderwater()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] row : pixels)
    {
      for (Pixel col : row)
      {
        int red = Math.abs(col.getRed() - 21);
        int green = Math.abs(col.getGreen() - 165);
        int blue = Math.abs(col.getBlue() - 173);
        
        int difference = red + green + blue;
        if (difference > 21) {
          col.setRed(difference);
          col.setGreen(difference);
          col.setBlue(difference);
        }
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVerticalRightToLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = width / 2; col > 0; col--)
      {
        rightPixel = pixels[row][width - 1 - col];
        leftPixel = pixels[row][col];
        leftPixel.setColor(rightPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel bottomPixel = null;
    Pixel topPixel = null;
    int hight = pixels.length;
    for (int row = 0; row < hight/2; row++)
    {
      for (int col = 0; col < hight; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[hight-1-row][col];
        topPixel.setColor(bottomPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontalBotToTop()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel bottomPixel = null;
    Pixel topPixel = null;
    int hight = pixels.length;
    for (int row = hight/2; row > 0; row--)
    {
      for (int col = 0; col < pixels[0].length; col++)
      {
        bottomPixel = pixels[hight-1-row][col];
        topPixel = pixels[row][col];
        topPixel.setColor(bottomPixel.getColor());
      }
    } 
  }
  
  public void mirrorDiagonal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel bottomPixel = null;
    Pixel topPixel = null;
    int length = pixels[0].length;
    if (pixels.length < length)
    { 
      length = pixels.length; 
    }
   
    
    for(int row = 0; row < length; row++)
    {
      for(int col = row; col < length; col++)
      {
        topPixel = pixels[col][row];
        bottomPixel = pixels[row][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    }
  }
  
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
        // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
    System.out.println("count should be " + ((97-27)+1) * ((mirrorPoint - 13)+1)
                        +"and count equals : " + count + "!");
  }
  
  public void mirrorArms()
  {
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    int mirrorPoint1 = 195;
    int mirrorPoint2 = 193;
    for (int row = 168; row < mirrorPoint1; row++)
    {
      for (int col = 238; col < 295; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[mirrorPoint1 - row + mirrorPoint1][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    } 
    
    for (int row = 161; row < 193; row++)
    {
      for (int col = 106; col < 171; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[mirrorPoint2 - row + mirrorPoint2][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    } 
  }
  
  public void mirrorGull()
  {
    int mirrorPoint = 350;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    for (int row = 230; row < 324; row++)
    {
      for (int col = 234; col < mirrorPoint; col++)
      {
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col+  mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }
  
  public void copyOtherParam(Picture fromPic, 
                 int tostartRow, int tostartCol, int fromStartRow, int fromStartCol, int fromEndRow, int fromEndCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = fromStartRow, toRow = tostartRow; 
         fromRow < fromEndRow &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = fromStartCol, toCol = tostartCol; 
           fromCol <  fromEndCol &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }
  
  public void myCollage()
  {
      
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this