package edu.mccc.cos210.fp.pupp;

public class Datatypes {
	
	public enum Part {
		LEFT_ARM,
		RIGHT_ARM,
		HEAD,
		LEFT_LEG,
		RIGHT_LEG,
		LEFT_HAND,
		RIGHT_HAND,
		LEFT_FOOT,
		RIGHT_FOOT;
	}
	
	public enum Joint {
		LEFT_SHOULDER,
		RIGHT_SHOULDER,
		NECK,
		LEFT,ELBOW,
		RIGHT_ELBOW,
		LEFT_KNEE,
		RIGHT_KNEE,
		LEFT_WRIST,
		RIGHT_WRIST,
		LEFT_ANKLE,
		RIGHT_ANKLE,
		HIP
	}
	
	public enum Action {
		ROTATE,
		MOVE,
		MOVE_TO
	}
}
