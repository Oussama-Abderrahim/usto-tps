package irdm;

import irdm.indexers.IndexedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
    public static File getResourceFile(String fileName) throws IOException {
        return new File("res/" + fileName);
    }

    public static File loadFileFromChooser(String desc, String ext) {
        File file = null;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./res"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(desc, ext);
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }

        return file;
    }

    public static BufferedImage loadImage(String fileName, int iWidth, int iHeight) {
        try {
            File spriteFile = FileManager.getResourceFile(fileName + ".png");
            BufferedImage img = ImageIO.read(spriteFile);

            // resize
            int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();

            return resizeImage(img, type, iWidth, iHeight);

        } catch (IOException e) {
            System.err.println("Can't load irdm.image" + fileName);
            return null;
        }

    }

    /**
     * Resize irdm.image
     *
     * @param originalImage (BufferedImage) irdm.image to resize
     * @param type          type of irdm.image, use ARGB for tranparancy
     * @param IMG_WIDTH     (int)
     * @param IMG_HEIGHT    (int)
     * @return (BufferedImage) the resize irdm.image
     */
    static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        Image resizedImage = originalImage.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
        // Create a buffered irdm.image with transparency
        BufferedImage bimage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        // Draw the irdm.image on to the buffered irdm.image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(resizedImage, 0, 0, null);
        bGr.dispose();
        // Return the buffered irdm.image
        return bimage;
    }

    public static String getLoadedFileText() {
        File file = loadFileFromChooser("Compila source files", "Compila");

        if (file == null)
            return "";

        String text = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                text += line + "\n";
            }
        } catch (IOException e) {

        }

        return text;
    }

    public static BufferedImage loadImageFromChooser(int width, int height, IndexedImage indexedImage) {
        File file = loadFileFromChooser("Select your irdm.image", "png");
        if (indexedImage != null)
            indexedImage.setFilePath(file.getPath());
        try {
            BufferedImage img = ImageIO.read(file);
            return resizeImage(img, img.getType(), width, height);
        } catch (Exception e) {
            return null;
        }
    }

    public static BufferedImage fileTOImage(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            return img;
        } catch (Exception e) {
            return null;
        }
    }
}
