package edu.mccc.cos210.fp.pupp;

public class Hip extends Joint{
	private Limb leftLowerLimb = null;
	private Limb rLowerLimb = null;
	public Hip(int x, int y, ILimb upperLimb) {
		super(x, y, upperLimb);
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
