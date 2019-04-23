package edu.mccc.cos210.fp.pupp;

import java.awt.image.BufferedImage;

public class Hip extends Joint{
	private Limb leftLowerLimb = null;
	private Limb rLowerLimb = null;
	public BufferedImage image;
	public Hip(int x, int y, ILimb upperLimb, BufferedImage image) {
		super(x, y, upperLimb);
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
}
