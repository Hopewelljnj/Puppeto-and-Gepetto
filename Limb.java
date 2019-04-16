package edu.mccc.cos210.fp.pupp;

import java.awt.Graphics2D;

public class Limb implements ILimb {
	
	Graphics2D graphics;
	float rotation;
	Joint topJoint = null;
	Joint bottomJoint = null;
	int centralX = 0;
	int centralY = 0;
	
	public Limb(Graphics2D graphics, float rotation, Joint topJoint, Joint bottomJoint) {
		this.graphics = graphics;
		this.rotation = rotation;
		this.topJoint = topJoint;
		this.bottomJoint = bottomJoint;
	}
	public Limb(Graphics2D graphics, float rotation, Joint topJoint) {
		this(graphics,rotation,topJoint,null);
	}
	public Limb(Graphics2D graphics, float rotation) {
		this(graphics,rotation,null,null);
	}
	
	public Limb(Graphics2D graphics) {
		this(graphics, 0.0f);
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
	public Graphics2D getGraphics() {
		return graphics;
	}

	@Override
	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
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
	public void rotateUpper(float rotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateLower(float rotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(int x, int y, Joint joint) {
		// TODO Auto-generated method stub
	}

}
