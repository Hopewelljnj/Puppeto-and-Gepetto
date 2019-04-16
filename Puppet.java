package edu.mccc.cos210.fp.pupp;

import edu.mccc.cos210.ds.IMap;

public class Puppet {
	String puppetName = "";
	IMap<Datatypes.Joint, Joint> joints = new edu.mccc.cos210.ds.Map<>();
	IMap<Datatypes.Part, ILimb> limbs = new edu.mccc.cos210.ds.Map<>();
	
	public Puppet(String puppetName, IMap limbs, IMap joints) {
		this.puppetName = puppetName;
		this.joints = joints;
		this.limbs = limbs;
	}
	public Puppet(String puppetName, IMap limbs) {
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
}