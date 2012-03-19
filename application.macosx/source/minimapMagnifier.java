import processing.core.*; 
import processing.xml.*; 

import java.awt.image.BufferedImage; 
import java.awt.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class minimapMagnifier extends PApplet {

// Written by Swiz0r on 4/28/2011 in Processing
// released into the public domain

// Use this program to magnify the minimap in starcraft 2
// Use 
//     w - a - s - d 
// to adjust where the window magnifies, and hold shift for more precise movements
// Use plus (+) and minus (-) to zoom in and out
// the default is 4x zoom, more or less



PImage screenShot;
GraphicsEnvironment ge;
GraphicsDevice[] gs;
DisplayMode mode;
Rectangle bounds;
BufferedImage desktop;
Robot robbie;


boolean verbose = true;  // do you want those dimensions and coordinates in the console?

// These numbers work for me. 
// For your own numbers, watch what comes out of the console as your move around
int rectX = 1287;
int rectY = 703;
int rectWidth = 300;
int rectHeight = 300;


// found automatically
int maxWidth;
int maxHeight;


// Initializes all the variables and whatnot that we are going to use.  This is run once.
// We are using Java's Robots class to get the screen captures
public void setup() {
  size(screen.width, screen.height);
  frameRate(60);
  frame.setResizable(true);
  
  ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
  gs = ge.getScreenDevices();
  mode = gs[0].getDisplayMode();  
  
  maxWidth = mode.getWidth();
  maxHeight = mode.getHeight();
  
  bounds = new Rectangle(rectX, rectY, rectWidth, rectHeight);
  desktop = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);

  rectWidth = maxWidth / 4;
  rectHeight = maxHeight / 4;
  try{
    robbie = new Robot(gs[0]);
  }catch(Exception e){
    System.err.println("Unable to make a new robot.");
    
  }
}

// get the screen capture and display it
// This is run continuously at the frame rate specified in setup()
public void draw () {
  screenShot = getScreen();
  image(screenShot,0,0, width, height);
}


public PImage getScreen() {
  desktop = robbie.createScreenCapture(bounds);
  return (new PImage(desktop));
}


public void keyPressed(){
  switch( key ){
    // coarses
    case 'w':
      rectY -= 50; break;
    case 's':
      rectY += 50; break;
    case 'a':
      rectX -= 50; break;
    case 'd':
      rectX += 50;
    
    // FINE (hold shift)  
    case 'W':
      rectY -= 1; break;
    case 'S':
      rectY += 1; break;
    case 'A':
      rectX -= 1; break;
    case 'D':   
      rectX += 1;      
      
    // zoom
    case '+':
      rectWidth += 1; rectHeight += 5; break;
    case '-':
      rectWidth -= 1; rectHeight -= 5; break;  
      
    default:
  }
  
  boundsCheck();
  if(verbose){
    println("x" + rectX);  println("y" + rectY);  println("w" + rectWidth);   println("h" + rectHeight);
  }
  bounds = new Rectangle(rectX, rectY, rectWidth, rectHeight);
  
}


public void boundsCheck(){
  // lower bounds
  rectX = rectX < 0? 0: rectX;  
  rectY = rectY < 0? 0: rectY;
  rectWidth = rectWidth < 25 ? 25 : rectWidth;
  rectHeight = rectHeight < 25 ? 25 : rectHeight;
  
  // upper bounds
  rectX = rectX > maxWidth + rectWidth ? maxWidth + rectWidth: rectX;  
  rectY = rectY > maxHeight - rectHeight? maxHeight - rectHeight: rectY;
  
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "minimapMagnifier" });
  }
}
