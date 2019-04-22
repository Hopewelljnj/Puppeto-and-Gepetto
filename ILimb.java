package edu.mccc.cos210.fp.pupp;

import java.awt.Graphics2D;

public interface ILimb {
	public Joint getTopJoint();
	public Joint getBottomJoint();
	
	public void setTopJoint(Joint joint);
	public void setBottomJoint(Joint joint);
	
	public double getRotation();
	public void setRotation(double rotation);
	
	public void rotateUpper(double rotation);
	public void rotateLower(double rotation);
	public void move(int x, int y , Joint joint);	
}