import ij.plugin.PlugIn;
import ij.ImagePlus;
import ij.IJ;
import ij.process.ImageProcessor;

public class JoinChannels_ implements PlugIn{
	private ImagePlus image;
	
	public void run(String arg) {
		this.image = IJ.getImage();
		if (this.image.getType() != ImagePlus.GRAY8) {
			IJ.error("The image must be Type GRAY8 in order to run this plugin");
		}
		else if (this.image.getImageStackSize() != 3){
			IJ.error("The image must be a stack of three Type GRAY8 images in order to run this plugin");
		}
		else {
			joinChannels();
		}	
	}
	
	private void joinChannels(){		
		int x, y, valorPixel[] = {0,0,0};
		
		Object[] rgbImage = image.getImageStack().getImageArray();	
		
		ImagePlus redChannel = (ImagePlus) rgbImage[0];
		ImagePlus greenChannel = (ImagePlus) rgbImage[1];
		ImagePlus blueChannel = (ImagePlus) rgbImage[2];
		
		int width = redChannel.getWidth(), height=redChannel.getHeight();
		
		ImagePlus newOutput = IJ.createImage("RGB Joined image", "RGB", width, height, 1);
		
		ImageProcessor processador = newOutput.getProcessor();
		ImageProcessor processadorR = redChannel.getProcessor();
		ImageProcessor processadorG = greenChannel.getProcessor();
		ImageProcessor processadorB = blueChannel.getProcessor();
		
		for (x = 0; x < this.image.getWidth(); x++) {
			for (y = 0; y < this.image.getHeight(); y++) {
				valorPixel[0] = processadorR.getPixel(x, y);
				valorPixel[1] = processadorG.getPixel(x, y);
				valorPixel[2] = processadorB.getPixel(x, y);
				processador.putPixel(x, y, valorPixel);
			}
		}
		this.image.close();
		newOutput.show();		
	}

}