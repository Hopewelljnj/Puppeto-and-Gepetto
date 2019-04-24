package edu.mccc.cos210.fp.pupp;

import java.awt.geom.AffineTransform;

public interface ILimb {
	public Joint getTopJoint();
	public Joint getBottomJoint();
	
	public void setTopJoint(Joint joint);
	public void setBottomJoint(Joint joint);
	
	public double getRotation();
	public void setRotation(double rotation);
	
	public AffineTransform getAT();
	
	public void rotateUpper(double rotation);
	public void rotateLower(double rotation);
	public void move(int x, int y , Joint joint);	
}