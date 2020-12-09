/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Keke
 */
public class ImageFilter extends FileFilter{
    @Override
            public boolean accept(File file) {
                // Allow only directories, or files with ".txt" extension
                return file.isDirectory() || file.getAbsolutePath().endsWith(".jpg") || file.getAbsolutePath().endsWith(".png") ;
        }

    /**
     * override, get "Images (*.jpg, *.png)
     * @return
     */
    @Override
            public String getDescription() {
                return "Images (*.jpg, *.png)";
            }
}
