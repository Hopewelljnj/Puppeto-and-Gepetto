package edu.mccc.cos210.fp.pupp;

public class Joint {
	protected int x;
	protected int y;
	protected ILimb upperLimb;
	protected ILimb lowerLimb;
	
	public Joint(int x, int y, ILimb upperLimb, ILimb lowerLimb) {
		this.x = x;
		this.y = y;
		this.upperLimb = upperLimb;
		this.lowerLimb = lowerLimb;
	}
	
	public Joint(int x, int y, ILimb upperLimb) {
		this(x,y,upperLimb,null);
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public ILimb getUpperLimb() {
		return upperLimb;
	}
	public ILimb getLowerLimb() {
		return lowerLimb;
	}
	public void setUpperLimb(ILimb limb) {
		upperLimb = limb;
	}
	public void setLowerLimb(ILimb limb) {
		lowerLimb = limb;
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
		upperLimb.move(x, y, this);
		lowerLimb.move(x, y, this);
	}
	public void moveRelativeTo(int x, int y) {
		this.x = this.x + x;
		this.y = this.y + y;
		upperLimb.move(x, y, this);
		lowerLimb.move(x, y, this);
	}
}