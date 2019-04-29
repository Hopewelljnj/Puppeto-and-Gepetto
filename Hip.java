package edu.mccc.cos210.fp.pupp;

import java.awt.image.BufferedImage;

public class Hip extends Joint{
	private Limb leftLowerLimb = null;
	private Limb rLowerLimb = null;
	private Joint leftHip = null;
	private Joint rightHip = null;
	public BufferedImage image;
	public Hip(int x, int y, ILimb upperLimb, BufferedImage image) {
		super(x, y, upperLimb);
		this.setLeftHip(new Joint(x+10,y,null));
		this.setRightHip(new Joint(x+40,y,null));
		this.image = image;
	}
	public Limb getLeftLowerLimb() {
		return leftLowerLimb;
	}
	public void setLeftLowerLimb(Limb leftLowerLimb) {
		this.leftLowerLimb = leftLowerLimb;
	}
	public Limb getrLowerLimb() {
		return rLowerLimb;
	}
	public void setrLowerLimb(Limb rLowerLimb) {
		this.rLowerLimb = rLowerLimb;
	}
	public Joint getLeftHip() {
		return leftHip;
	}
	public void setLeftHip(Joint leftHip) {
		this.leftHip = leftHip;
	}
	public Joint getRightHip() {
		return rightHip;
	}
	public void setRightHip(Joint rightHip) {
		this.rightHip = rightHip;
	}
}
