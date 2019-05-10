package edu.mccc.cos210.fp.pupp;


import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import edu.mccc.cos210.ds.Array;
import edu.mccc.cos210.fp.pupp.Datatypes.Part;

public class Limb implements ILimb {
	
	double rotation;
	Joint topJoint = null;
	Joint bottomJoint = null;
	int centralX = 0;
	int centralY = 0;
	BufferedImage image;
	public Datatypes.Part curr = null;
	private double distance = 0.0;
	protected ILimb lowerLimb = null;
	public Limb(double rotation, Joint topJoint, Joint bottomJoint, BufferedImage image, Datatypes.Part curr, ILimb lowerLimb) {
		this(rotation, topJoint, bottomJoint, image, curr);
		this.lowerLimb = lowerLimb;
	}
	public Limb(double rotation, Joint topJoint, Joint bottomJoint, BufferedImage image, Datatypes.Part curr) {
		this.curr = curr;
		this.topJoint = topJoint;
		this.bottomJoint = bottomJoint;
		this.image = image;
		this.rotation = rotation;
	}
	public Limb(double rotation, Joint topJoint, Joint bottomJoint, BufferedImage image) {
		this.topJoint = topJoint;
		this.bottomJoint = bottomJoint;
		this.image = image;
		this.rotation = rotation;
	}

	public Limb(double d, Joint topJoint, BufferedImage image) {
		this(d,topJoint,null,image);
	}
	public Limb(float rotation) {
		this(rotation,null,null);
	}
	public Limb(BufferedImage bi) {
		this.image = bi;
	}

	@Override
	public Joint getTopJoint() {
		return topJoint;
	}

	@Override
	public Joint getBottomJoint() {
		return bottomJoint;
	}

	@Override
	public Limb setTopJoint(Joint joint) {
		topJoint = joint;
		return this;
	}

	@Override
	public void setBottomJoint(Joint joint) {
		bottomJoint = joint;
	}

	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	@Override
	public Joint rotateUpper(double rotation) {
		Joint close = this.getTopJoint();
		Joint far = this.getBottomJoint();
		return forwardRotation(close, far, rotation);
	}
	
	@Override
	public void rotateLower(double rotation) {
		Joint far = this.getBottomJoint();
		Joint close = this.getTopJoint();
		System.out.println(far);
		System.out.println(close);
		forwardRotation(close, far, rotation);		
	}

	@Override
	public void move(int x, int y, Joint joint) {
		joint.moveRelativeTo(x, y);
	}
	
	public double distance(int x1, int y1, int x2, int y2) {
		double total = ((x2-x1)*(x2-x1)) + ((y2-y1)*(y2-y1));
		System.out.println("total" + total);
		double distance = Math.sqrt(total);
		System.out.println("Distance is : " + x1 + "," + x2 + " : " + y1 + "," + y2 + " : " + distance);
		return distance;
	}
	public double distance(Joint close, Joint far) {
		if(close != null && far != null)
			return distance(close.x,close.y,far.x,far.y);
		return 0.0;
	}	
	public Array<Integer> reverseRotation(Joint close, Joint far) {
		Array<Integer> coords = new Array<>(2);
		int x = ((int) far.x + (int) Math.sin(rotation));
		int y = ((int) far.y + (int) Math.cos(rotation));
		coords.set(0, x);
		coords.set(1, y);
		return coords;
	}
	
	public Joint forwardRotation(Joint close, Joint far, double rotation) {
		this.rotation = this.rotation + Math.toRadians(rotation);
		//idk whats happening here but i got rid of all this and it now works like it did before
		//i suggest we keep it like this and come at this problem from a different angle
		return far;
	}
	@Override
	public String toString() {
		String string = "topJoint : " + (topJoint != null ? topJoint.toString() : "null") + "\n" +
						"bottomJoint : " + (bottomJoint != null ? bottomJoint.toString() : "null") + "\n" +
						"centralX : " + centralX + "\n" +
						"centralY : " + centralY + "\n" +
						"rotation : " + rotation;
		return string;
	}
	@Override
	public AffineTransform getAT() {
		AffineTransform at = new AffineTransform();
		if(topJoint == null) topJoint = new Joint(80,0,this);
		at.translate((double) topJoint.getX(), (double) topJoint.getY());
		at.rotate(rotation);
		at.scale(2.0,1.5);
		return at;
	}
	@Override
	public Part getCurr() {
		return curr;
	}
	@Override
	public ILimb getLowerLimb() {
		return lowerLimb;
	}
	@Override
	public void setLowerLimb(ILimb lowerLimb) {
		this.lowerLimb = lowerLimb;
	}
	public void calcDistance() {
		this.distance = distance(topJoint,bottomJoint);
	}
}
