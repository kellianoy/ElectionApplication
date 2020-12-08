/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Keke
 */
public class FileManager {
    
    private final File file;
    private final Scanner in;
    private final String FILE_NAME="userPreferences.stg";
    
    public FileManager() throws FileNotFoundException{
        
        file = new File(FILE_NAME);
        in = new Scanner(file);
      
    }

    public void saveColor(Color c) {
        PrintWriter outputFile;
        try {
        outputFile = new PrintWriter(FILE_NAME);
        outputFile.println(c.getRed());
        outputFile.println(c.getBlue());
        outputFile.println(c.getGreen());
        outputFile.close();
        }
        catch (FileNotFoundException e)
        {
            
        }
    }
    
    public Color retrieveColor(){
      
        String[] color = new String[3];
        int i = 0;
        while (in.hasNext())
        {
            if (i<color.length)
                color[i]=in.nextLine(); 
            ++i;
        }
        if (i!=0)
            return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
        else
            return null;
    }
}
