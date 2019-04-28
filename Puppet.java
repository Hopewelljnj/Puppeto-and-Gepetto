package edu.mccc.cos210.fp.pupp;

import edu.mccc.cos210.ds.IMap;

public class Puppet {
	String puppetName = "";
	IMap<Datatypes.Joint, Joint> joints = new edu.mccc.cos210.ds.Map<>();
	IMap<Datatypes.Part, ILimb> limbs = new edu.mccc.cos210.ds.Map<>();

	public Puppet(String puppetName, IMap<Datatypes.Part, ILimb> limbs, IMap<Datatypes.Joint, Joint> joints) {
		this.puppetName = puppetName;
		this.joints = joints;
		this.limbs = limbs;
	}
	public Puppet(String puppetName, IMap<Datatypes.Part, ILimb> limbs) {
		this.puppetName = puppetName;
		this.limbs = limbs;
		//code to find the joints maybe?//
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
		Joint tempJoint = rotateJoint(curJoint, rotate);
		this.joints.put(joint, tempJoint);
		return this;
	}
	public Joint rotateJoint(Joint joint, double rotation) {
		ILimb upper = joint.getUpperLimb();
		ILimb lower = joint.getLowerLimb();
		Joint lowerJ = lower.rotateUpper(rotation);
	//	lower.setTopJoint(lowerJ);
	//	upper.setBottomJoint(lowerJ);
		return lowerJ;
	}

}
