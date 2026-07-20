package com.kf;

import java.io.*; 
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class finger
{
	static
    {
		System.loadLibrary("finger");
    }

	public native int MatchTemplateEx(String src, String dst);
    
    
    
    public String readFile(String path) 
    {
        BufferedReader br;
        String read = "";
        String readStr = "";
        try {
            File file = new File(path);
            FileReader fileread = new FileReader(file);
            br = new BufferedReader(fileread);
            while ((read = br.readLine()) != null) {
                readStr = readStr + read;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("###readStr:" + readStr);
        return readStr;
    } 
    
    public void Test()
    {
    	String src="AwFUIAAAgAKAAoACgAKAAoACgAKAAoACgAIAAoACgAKAAoAGwAbgDkQ4AAAAAAAAAAAAAAAAAAAiIZ1eYjHp3jm3Gr4ducT+LbxEHha9nL5YwFYeIEGFHiSbR/8anEe/XJ6nn1okqN8gJscfQyisf0xAbF9Yl+acSx+n/FAjqHwTsF18MDgDHBy03tJHER97MCcFWxoyBvM1JUTYNifdOE8VJDZDHGoSXIqgUFiN4LFJk2BRRRpe0QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==";
   	 	String dst="AwFWFwAAgD6AHoAOgA4ABgAGAAYABgAOAA4ADgAOAA4AHgAeAB6APkA8AAAAAAAAAAAAAAAAAABSl6X+Dx9H3kElKN5GKSleUqtpvle4qbYSQwYWGh2IP1Ge5383IUA/GaUen1MlKL8VKQefJisFfzktQB8uulsfDjsF/yG7g/8ev4UfXJCkPVGTIXpWkKEbPB0elgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMBWSEAAOAOwAbABoAGgAaABoAGgAaABoAGgAaABoAGgAaADsAO//5UIAAAAAAAAAAAAAAAAAAAZ5CmViQSx35YF6ieLJfePhYZX35am+leKBwGfkSqm143sES+P7fbvks6gj44vZveHD4E3i8RB99MFABfTx9AfxImhj9VNgE/OqyDfFy+lhwfJR06KCjdmjschXkjpUZTJKxFmUCbRTZAHR1WUz2ZFlIPH5cescWXVL8s91yM5NVXDeNyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";   	 	
		if (MatchTemplateEx(src, dst) >30 )
   	 	{
   	 		System.out.println("Match TRUE");
   	 	}
   	 	else
   	 	{
   	 		System.out.println("Math FALSE");
   	 	}   	 	
    }
    
     
    
    public static void main(String[] args)throws   IOException 
    {
    	finger jc = new finger();
    	 System.out.println("Fingerprint SDK Java Demo");
    	 System.out.println(System.getProperty("java.library.path")); 
    	 jc.Test();
    	 
    }
}