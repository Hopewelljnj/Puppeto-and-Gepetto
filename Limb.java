package edu.mccc.cos210.fp.pupp;

import edu.mccc.cos210.ds.Array;

public class Limb implements ILimb {
	
	double rotation;
	Joint topJoint = null;
	Joint bottomJoint = null;
	int centralX = 0;
	int centralY = 0;
	
	public Limb(float rotation, Joint topJoint, Joint bottomJoint) {
		this.rotation = rotation;
		this.topJoint = topJoint;
		this.bottomJoint = bottomJoint;
	}
	public Limb(float rotation, Joint topJoint) {
		this(rotation,topJoint,null);
	}
	public Limb(float rotation) {
		this(rotation,null,null);
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
	public void setTopJoint(Joint joint) {
		topJoint = joint;
	}

	@Override
	public void setBottomJoint(Joint joint) {
		bottomJoint = joint;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	@Override
	public void rotateUpper(double rotation) {
		Joint close = this.getTopJoint();
		Joint far = this.getBottomJoint();
		forwardRotation(close, far, rotation);

	}
	//rotation in degrees
	@Override
	public void rotateLower(double rotation) {
		Joint far = this.getBottomJoint();
		Joint close = this.getTopJoint();
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
	
	public void forwardRotation(Joint close, Joint far, double rotation) {
		double distance = distance(close,far);
		Array<Integer> oldCoords = reverseRotation(close,far); //old coords are new far
		double angle = (double)this.rotation + rotation; // total angle from 0 angle
		double radAng = Math.toRadians(angle);
		int x = (int) ((int) oldCoords.get(0) - (int) distance*Math.sin(radAng));
		int y = (int) ((int) oldCoords.get(1) + (int) distance*Math.cos(radAng));
		far.setX(x);
		far.setY(y);
		this.rotation = angle;
	}
}
