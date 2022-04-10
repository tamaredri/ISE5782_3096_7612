package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;

class ImageWriterTest {
    @Test
    void yellowAndRedImage() {
        ImageWriter imageWriter = new ImageWriter("Yellow and red", 800, 500 );
        for (int i = 0; i< imageWriter.getNy(); i++)
            for(int j = 0; j< imageWriter.getNx(); j++)
                if(i%50 == 0 || j%50 == 0)
                    imageWriter.writePixel(j,i,new Color(255,0,0));
                else
                    imageWriter.writePixel(j,i,new Color(255,255,0));
    imageWriter.writeToImage();
    }
}