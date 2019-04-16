package edu.mccc.cos210.fp.pupp;

import java.awt.Graphics2D;

public interface ILimb {
	public Joint getTopJoint();
	public Joint getBottomJoint();
	
	public void setTopJoint(Joint joint);
	public void setBottomJoint(Joint joint);
	
	public Graphics2D getGraphics();
	public void setGraphics(Graphics2D graphics);
	
	public float getRotation();
	public void setRotation(float rotation);
	
	public void rotateUpper(float rotation);
	public void rotateLower(float rotation);
	public void move(int x, int y , Joint joint);	
}