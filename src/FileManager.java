import theme.Theme;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

public class FileManager
{
    public static File getResourceFile(String fileName) throws IOException
    {
        return new File("res/" + fileName);
    }

    /**
    * affiche une boite de dialogue pour ouvrir un fichier 
    *	(exemple : File file =  loadFile("Compila source files", "Compila");  ) 
    * @param desc : Description du fichier 
    * @param ext  : extensions acceptées 
    * @return File : un objet File du fichier lu 
    */

    public static File loadFile(String desc, String ext)
    {
        File file = null;

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(desc, ext);
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(null);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            file = fileChooser.getSelectedFile();
        }

        return file;
    }

    public static ImageIcon loadImage(String fileName)
    {
        try
        {
            File spriteFile = FileManager.getResourceFile(fileName + ".png");
            BufferedImage img = ImageIO.read(spriteFile);

            // resize
            int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();

            return new ImageIcon(
                    resizeImage(img, type, Theme.ICON_WIDTH, Theme.ICON_HEIGHT)
            );
        } catch (IOException e) {
            return null;
        }

    }
    /**
     * Resize image
     * @param originalImage (BufferedImage) image to resize
     * @param type          type of image, use ARGB for tranparancy
     * @param IMG_WIDTH     (int)
     * @param IMG_HEIGHT    (int)
     * @return (BufferedImage) the resize image
     */
    static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT)
    {
        Image resizedImage = originalImage.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(resizedImage, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    public static String getLoadedFileText() {
        File file = loadFile("Compila source files", "Compila");

        if(file == null)
            return "";

        String text = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)
            {
                text += line+"\n";
            }
        } catch (IOException e) {

        }

        return text;
    }
}
