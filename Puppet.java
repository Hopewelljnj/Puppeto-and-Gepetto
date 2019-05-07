package edu.mccc.cos210.fp.pupp;

import edu.mccc.cos210.ds.IMap;
import edu.mccc.cos210.ds.Map;

public class Puppet {
	String puppetName = "";
	IMap<Datatypes.Joint, Joint> joints = new Map<>();
	IMap<Datatypes.Part, ILimb> limbs = new Map<>();

	public Puppet(String puppetName, IMap<Datatypes.Part, ILimb> limbs, IMap<Datatypes.Joint, Joint> joints) {
		this.puppetName = puppetName;
		this.joints = joints;
		this.limbs = limbs;
	}
	public Puppet(String puppetName, IMap<Datatypes.Part, ILimb> limbs) {
		this.puppetName = puppetName;
		this.limbs = limbs;
	}
	public Puppet(String puppetName) {
		this.puppetName = puppetName;
	}

	public IMap<Datatypes.Part, ILimb> getLimbs() {
		return limbs;
	}
	public IMap<Datatypes.Joint, Joint> getJoints() {
		return joints;
	}

	public ILimb getLimbByName(Datatypes.Part partName) {
		return limbs.get(partName);
	}
	public Joint getJointByName(Datatypes.Joint jointName) {
		return joints.get(jointName);
	}
	public Puppet doRotate(Datatypes.Joint joint, double rotate) {
		Joint curJoint = getJointByName(joint);
		rotateJoint(curJoint, rotate);
		return this;
	}
	public Joint rotateJoint(Joint joint, double rotation) {
		@SuppressWarnings("unused")
		ILimb upper = joint.getUpperLimb();
		ILimb lower = joint.getLowerLimb();
		Joint lowerJ = lower.rotateUpper(rotation);
		return lowerJ;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Datatypes.Part part : limbs.keySet()) {
			sb.append(part + " : " + limbs.get(part) + "\n");
		}
		String returnString = sb.toString();
		return returnString;
	}

}
