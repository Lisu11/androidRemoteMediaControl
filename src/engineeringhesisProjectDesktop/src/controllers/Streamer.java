package controllers;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class Streamer {

	BufferedImage bi;
	ByteArrayOutputStream os;
	Robot robot;
	volatile static Rectangle screenSize ;
	BufferedImage cursor;

	public Streamer() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		screenSize = getscreeSize(false);
		try {
			cursor = ImageIO.read(new File("./edit-select.png"));
			System.out.println("wczytalem obrazek kursora");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("rozmiar to= "+screenSize.toString());
	}
	
	public byte[] getScreenShot(){
		//bi = createResizedCopy(robot.createScreenCapture(screenSize), 506, 900, false);
		//bi = robot.createScreenCapture(screenSize);
//		bi = scale(robot.createScreenCapture(screenSize), 900, 506);
//
//		os = new ByteArrayOutputStream();
//		try {
//			ImageIO.write(bi,  "png",  os);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//os.flush();
//	      return os.toByteArray();
		bi = robot.createScreenCapture(screenSize);
		drawCursor();
		return convertToJPEG(bi);
	}

	private void drawCursor(){
		Point currentPosition = MouseInfo.getPointerInfo().getLocation();
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.drawImage(cursor, currentPosition.x, currentPosition.y, null);
		g2d.setColor(Color.RED);
		g2d.fillRect(currentPosition.x, currentPosition.y, 1, 1);
		g2d.dispose();
	}
	
	BufferedImage createResizedCopy(Image originalImage, 
    		int scaledWidth, int scaledHeight, 
    		boolean preserveAlpha)
    {
    	System.out.println("resizing...");
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) {
    		g.setComposite(AlphaComposite.Src);
    	}
    	g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    	g.dispose();
    	return scaledBI;
    }

	private static Rectangle getscreeSize(boolean rotate){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		int x=1,y=1;
		for (GraphicsDevice dev : gs) {
			//GraphicsConfiguration[] gc = dev.getConfigurations();
			// for(GraphicsConfiguration curGc : gc)
			// {
			// Rectangle bounds = curGc.getBounds();
			// ColorModel cm = curGc.getColorModel();
			//
			// System.out.println("" + bounds.getX() + "," + bounds.getY() + " "
			// + bounds.getWidth() + "x" + bounds.getHeight() + " " + cm);
			// }
			DisplayMode dm = dev.getDisplayMode();
			System.out.println(dm.getWidth() + " x " + dm.getHeight());
			x = dm.getWidth();
			y = dm.getHeight();
			break;
		}
		if(rotate){
			int t = x;
			x = y;
			y = t;
		}
		return new Rectangle(y,x);
	}

	
	 public static void setScreenSize(boolean rotate) {
		Streamer.screenSize = getscreeSize(rotate);
	}

	static public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

	    int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	    BufferedImage ret = img;
	    BufferedImage scratchImage = null;
	    Graphics2D g2 = null;

	    int w = img.getWidth();
	    int h = img.getHeight();

	    int prevW = w;
	    int prevH = h;

	    do {
	        if (w > targetWidth) {
	            w /= 2;
	            w = (w < targetWidth) ? targetWidth : w;
	        }

	        if (h > targetHeight) {
	            h /= 2;
	            h = (h < targetHeight) ? targetHeight : h;
	        }

	        if (scratchImage == null) {
	            scratchImage = new BufferedImage(w, h, type);
	            g2 = scratchImage.createGraphics();
	        }

	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

	        prevW = w;
	        prevH = h;
	        ret = scratchImage;
	    } while (w != targetWidth || h != targetHeight);

	    if (g2 != null) {
	        g2.dispose();
	    }

	    if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
	        scratchImage = new BufferedImage(targetWidth, targetHeight, type);
	        g2 = scratchImage.createGraphics();
	        g2.drawImage(ret, 0, 0, null);
	        g2.dispose();
	        ret = scratchImage;
	    }

	    return ret;

	}
	 

	 public byte[] convertToJPEG(BufferedImage buffi)
     {     
        try{          
              
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
          
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();
            
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(.35f);

            writer.setOutput(ios);
            writer.write(null, new IIOImage(buffi, null, null), param);
               
               byte[] data = baos.toByteArray();

               writer.dispose(); 
               
               return data;          
          }
          catch(Exception e)
          {
               e.printStackTrace();
          }
     
          return null;

     }
	 
}
