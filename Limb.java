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
		return Math.sqrt((x2-x1)^2 + (y2-y1)^2);
	}
	public double distance(Joint close, Joint far) {
		return distance(close.x,close.y,far.x,far.y);
	}
	public Array<Integer> reverseRotation(Joint close, Joint far) {
		double distance = distance(close,far);
		Array<Integer> coords = new Array<>(2);
		int x = (int) ((int) far.x + (int) distance*Math.sin(Math.toRadians((double) rotation)));
		int y = (int) ((int) far.y - (int) distance*Math.acos(Math.toRadians((double) rotation)));
		coords.set(0, x);
		coords.set(1, y);
		return coords;
	}
	
	public Joint forwardRotation(Joint close, Joint far, double rotation) {
		this.rotation = Math.toRadians(rotation) +this.rotation;
		if(true)return far;
		@SuppressWarnings("unused")
		double distance = distance(close,far);
		Array<Integer> oldCoords = reverseRotation(close,far);
		double angle = (double)this.rotation + Math.toRadians(rotation);
		double radAng = angle;
		int x = (int) ((int) oldCoords.get(0) - (int) distance*Math.sin(radAng));
		int y = (int) ((int) oldCoords.get(1) + (int) distance*Math.cos(radAng));
		far.setX(x);
		far.setY(y);
		far.getLowerLimb().setTopJoint(far);
		this.rotation = angle;
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
}
