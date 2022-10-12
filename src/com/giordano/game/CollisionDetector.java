package com.giordano.game;

import java.util.ArrayList;

import com.giordano.engine.Updateable;

public class CollisionDetector implements Updateable, Constants {
	
	private ArrayList<PhysicalObject[]> collisionEffects = new ArrayList<PhysicalObject[]>();
	private ArrayList<String> collisionEffectDirs = new ArrayList<String>();
	
	public CollisionDetector() {
		updateables.add(this);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		detectCollisions();
		resolvePhysicalCollisions();
		executeCollisionEffects();
 	}
	
	private void detectCollisions() {
		//cycle through all of the gameObjects and detect collisions, add colliding GOs to each others collisionObjects array 
		for (GameObject go1 : gm.getObjects()) {
			GameObject[] collides = new GameObject[gm.getObjects().size()];
			int i = 0;
			for (GameObject go2 : gm.getObjects()) {
				if (go1 == go2) continue;
				if (checkCollision(go1,go2)) {
					collides[i] = go2;
					i++;
				}
			}
			if (i == 0) {go1.setCollisionObjects(new GameObject[] {}); continue;}
			GameObject[] temp = new GameObject[i];
			for (int j = 0; j < i; j++) {
				temp[j] = collides[j];
			}
			go1.setCollisionObjects(temp);
		}
	}
	
	private void resolvePhysicalCollisions() {
		/**
		 * cycle through all the physicalObjects and adjust their positions so that they are no longer colliding
		 * excludes transparent physicalObjects
		 * does not adjust the position or velocity of fixed objects, but adjusts the positions and velocities of any objects they are colliding with
		 * 
		 * 1. cycle through all collisions with all truly fixed objects
		 * 		for each one, resolve the collision and then check for new collisions with truly fixed objects and resolve those
		 * 		once the object is resolved of collisions with truly fixed objects, temporarily fix it
		 * 		now no objects are colliding with fixed objects
		 * 2. cycle through all of the temp fixed objects and resolve any collisions between them
		 * 		resolve the collision as if neither object is fixed
		 * 		now no objects are colliding with fixed objects and no temporarily fixed objects are colliding with other temp fixed objects
		 * 3. repeat 1 and 2 but with fixation += 1 until there are no more fixed collisions
		 * 4. cycle through all of the non fixed objects and resolve collisions between them
		 * 5. check for cramping (any extraneous collisions)
		 * 
		 */
		
		ArrayList<PhysicalObject> pos = new ArrayList<PhysicalObject>();
		
		//reset fixations
		for (GameObject go : gm.getObjects()) {
			if (!(go instanceof PhysicalObject)) continue;
			pos.add((PhysicalObject)go);
			if (((PhysicalObject)go).isFixed()) {
				((PhysicalObject)go).setFixation(0);
				((PhysicalObject)go).fixedX = false;
				((PhysicalObject)go).fixedY = false;
			} else {
				((PhysicalObject)go).setFixation(-1);
			}
			if (((PhysicalObject)go).getSquaredVelocityMagnitude() > Math.pow(WARNING_SPEED,2)) {
				System.out.println("Warning: PhysicalObject " + go.getTag() + " had a speed of " + ((PhysicalObject)go).getVelocityMagnitude() + " which exceeds safe speeds");
			}
		}
		
		//*** pos abbreviates PhysicalObjects, not position
		
		//this loop repeats the below process for each successive fixation level until
		//there are no more objects that need to be temporarily fixed
		//all the comments in the below loop assume that it is the first cycle
		//to interpret this in the context of additional cycles, fixed objects means
		//objects with fixation f, fixation 1 means fixation f+1, and non fixed always means fixation -1
		int f = 0;
		int numFixedCollides = -1;
		while (numFixedCollides != 0) {
			numFixedCollides = 0;
			//1. clear all non fixed objects from fixed objects
			for (PhysicalObject po1 : pos) {
				if (po1.getFixation() != -1 || po1.isTransparent()) continue;
				String dir = "";
				boolean collisionFound = true;
				while (collisionFound) { //continue going around this loop until no more collisions with fixed objects
					collisionFound = false;
					for (int i = 0; i < pos.size(); i++) {
						PhysicalObject po2 = pos.get(i);
						if (po2 == po1 || po2.isTransparent()) continue;
						if (po2.getFixation() == f && checkCollision(po1, po2)) {
							dir = adjustPosition(po1, false, po2, true);	//the last direction found will be the direction of the collision, unless a collision with another fixation 1 object is found
							po1.setFixation(f+1);
							collisionFound = true;
							if (dir == "x") {po1.collidedX = po2; po1.fixedX = true;}
							if (dir == "y") {po1.collidedY = po2; po1.fixedY = true;}
						}
					}
					
				}
				if (dir == "x") {numFixedCollides++;}
				if (dir == "y") {numFixedCollides++;}
			}
			
			//2. clear all objects that are fixation 1 from each other
			for (PhysicalObject po1 : pos) {
				if (po1.getFixation() != f+1 || po1.isTransparent()) continue;
				int lastCollisionIndex = pos.size();
				String dir = "";
				boolean collisionFound = true;
				while (collisionFound) { //continue going around this loop until no more collisions with fixed objects
					collisionFound = false;
					for (int i = 0; i < pos.size(); i++) {
						PhysicalObject po2 = pos.get(i);
						if (po2 == po1 || po2.isTransparent()) continue;
						if (po2.getFixation() == f+1 && checkCollision(po1, po2)) {
							dir = adjustPosition(po1, false, po2, true);	//the last direction found will be the direction of the collision, unless a collision with another fixation 1 object is found
							collisionFound = true;
							if (dir == "x") {po1.collidedX = po2; po2.collidedX = po1;}
							if (dir == "y") {po1.collidedY = po2; po2.collidedY = po1;}
						}
					}
					
				}
			}
			//use the collidedX and collidedY values to set speeds to 0, reset collidedX and collidedY, momentumObjects will change this
			for (PhysicalObject po : pos) {
				if (po.getFixation() == f+1) {
					if (po.collidedX != null) {addCollisionEffect(po, po.collidedX, "x");}
					if (po.collidedY != null) {addCollisionEffect(po, po.collidedY, "y");}
					po.collidedX = null;
					po.collidedY = null;
				}
			}
			
			//3. increase fixation and repeat
			f++;
		}
		
		//4. resolve collisions between non fixed objects
		for (PhysicalObject po1 : pos) {
			if (po1.getFixation() != -1 || po1.isTransparent()) continue;
			for (PhysicalObject po2 : pos) {
				if (po2.getFixation() != -1 || po1 == po2 || po2.isTransparent()) continue;
				String dir = adjustPosition(po1, false, po2, false);
				//if (dir == "x") {po1.setVelX(0); po2.setVelX(0);}
				//if (dir == "y") {po1.setVelY(0); po2.setVelY(0);}
				if (dir != "alreadyResolved") {
					addCollisionEffect(po1, po2, dir);
				}
			}
		}
		
		//5. check for cramping
		for (PhysicalObject po : pos) {
			if (po.isTransparent() || po.isFixed()) continue;
			GameObject[] poCollides = checkCollisionFiltered(po, "PhysicalObject");
			if (poCollides.length > 0) {
				String collides = "";
				for (int i = 0; i < poCollides.length-1; i++) {collides = collides + poCollides[i].getTag() + ", ";}
				collides = collides + poCollides[poCollides.length-1].getTag();
				System.out.println("Failure to resolve collisions: cramping between " + po.getTag() + " and " + collides);
			}
		}
	}
	
	/* maybe come back to this later
	private String adjustPosition2(PhysicalObject po1, boolean fix1, PhysicalObject po2, boolean fix2) {
		if (!checkCollision(po1, po2)) return "alreadyResolved";
		
		//right now the code assumes that 1 is not moving
		
		//find the quadrant of the relative velocity (relative to object 1)
		double[] relv1 = new double[] {po2.getVelX() - po1.getVelX(), po2.getVelY() - po1.getVelY()};
		String q = getQuadrant(relv1);
		if (q == "x" || q == "y") return q;
		if (q == "v is not a 2d vector" || q == "v is the 0 vector") {System.out.println("bad case from getQuadrant(): " + q); return "";}
		
		//find the corner of object 2 that had to cross into object 1 first or simultaneously with another corner
		double[] c2;
		if (q == "I") c2 = new double[] {po2.getPosX() + po2.getWidth(), po2.getPosY()};
		else if (q == "II") c2 = new double[] {po2.getPosX(), po2.getPosY()};
		else if (q == "III") c2 = new double[] {po2.getPosX(), po2.getPosY() + po2.getHeight()};
		else c2 = new double[] {po2.getPosX() + po2.getWidth(), po2.getPosY() + po2.getHeight()};
		
		//find the corner of object 1 that object 2 either hit, or hit one of the two sides touching that corner 
		double[] c1;
		if (q == "I") c1 = new double[] {po1.getPosX(), po1.getPosY() + po1.getHeight()};
		else if (q == "II") c1 = new double[] {po1.getPosX() + po1.getWidth(), po1.getPosY() + po1.getHeight()};
		else if (q == "III") c1 = new double[] {po1.getPosX() + po1.getWidth(), po1.getPosY()};
		else c1 = new double[] {po1.getPosX(), po1.getPosY()};
		
		//draw the line in the backwards direction of velocity ( y = -(vy/vx)(x-c2x)+c2y ) until it collides with either side
		//touching c1 (x = c1x and y = c1y). Find what the other variable is and compare it to c1
		//the if statements have presolved those equations
		// y = -(vy/vx)(x-c2x)+c2y AND x = c1x  =>  y = -(vy/vx)(c1x-c2x)+c2y
		// y = -(vy/vx)(x-c2x)+c2y AND y = c1y  =>  x = -(vx/vy)(c1y-c2y)+c2x
		double collY = -(relv1[1]/relv1[0])*(c1[0]-c2[0])+c2[1];
		double collX = -(relv1[0]/relv1[1])*(c1[1]-c2[1])+c2[0];
		
		
		if (collX == c1[0] && collY == c1[1]) {
			System.out.println("Corner Collision Occured");
			//po2.setPosXY();
			return null; //TODO
		} else if (collX > c1[0] && q == "I" || q == "IV") {
			//po2.setPosXY();
			return "x";
		} else if (collX < c1[0] && q == "II" || q == "III") {
			//po2.setPosXY();
			return "x";
		} else {
			//po2.setPosXY();
			return "y";
		}
		
	}*/
	
	private void executeCollisionEffects() {
		for (int i = 0; i < collisionEffects.size(); i++) {
			PhysicalObject po1 = collisionEffects.get(i)[0];
			PhysicalObject po2 = collisionEffects.get(i)[1];
			String dir = collisionEffectDirs.get(i);
			boolean fix1 = po1.getFixation() != -1 || po1.getFixation() != po2.getFixation();
			boolean fix2 = po2.getFixation() != -1 || po1.getFixation() != po2.getFixation();
			bounce(po1, fix1, po2, fix2, dir);
			//friction();
		}
		collisionEffects.clear();
		collisionEffectDirs.clear();
	}
	
	private void bounce(PhysicalObject p1, boolean fix1, PhysicalObject p2, boolean fix2, String dir) {
		
		double m1 = p1.getMass();
		double m2 = p2.getMass();
		double e = p1.getElasticity() * p2.getElasticity();
		
		if (dir == "x") {
			
			double v1i = p1.getVelX();
			double v2i = p2.getVelX();
			
			double v1f = (2*v2i + (m1/m2 - 1)*v1i) / (1 + m1/m2);
			double v2f = (2*v1i + (m2/m1 - 1)*v2i) / (1 + m2/m1);
			
			if (fix1) {v2f = 2*v1i - v2i; v1f = 0;}
			if (fix2) {v1f = 2*v2i - v1i; v2f = 0;}
			
			if (Math.abs(v1f) < MIN_BOUNCE_VEL) v1f = 0;
			if (Math.abs(v2f) < MIN_BOUNCE_VEL) v2f = 0;
			
			p1.setVelX(v1f*e);
			p2.setVelX(v2f*e);
			
			return;
		}
		
		if (dir == "y") {
			
			double v1i = p1.getVelY();
			double v2i = p2.getVelY();
			
			double v1f = (2*v2i + (m1/m2 - 1)*v1i) / (1 + m1/m2);
			double v2f = (2*v1i + (m2/m1 - 1)*v2i) / (1 + m2/m1);
			
			if (fix1) {v2f = 2*v1i - v2i; v1f = 0;}
			if (fix2) {v1f = 2*v2i - v1i; v2f = 0;}
			
			if (Math.abs(v1f) < MIN_BOUNCE_VEL) v1f = 0;
			if (Math.abs(v2f) < MIN_BOUNCE_VEL) v2f = 0;
			
			p1.setVelY(v1f*e);
			p2.setVelY(v2f*e);
			
		}
		
	}
	
	/* to be implemented later
	private void friction() {
		
	}*/
	
	private String adjustPosition(PhysicalObject po1, boolean fix1, PhysicalObject po2, boolean fix2) {
		if (!checkCollision(po1, po2)) return "alreadyResolved";
		
		double[] v1 = po1.getVelocityVector();
		double[] v2 = po2.getVelocityVector();
		
		//SPECIAL CASE: falling object collides horizontally with sliding object
		//because the falling object would have to correct in the direction of motion it would have to move upwards
		//even though it should be pushed horizontally
		/*if (fix1 &&
				v1[1] == 0 && (isBetween(po1.posY, po2.posY, po1.posY + po1.height) || isBetween(po2.posY, po1.posY, po2.posY + po2.height))
				&& getCollisionDirection(po1, po2) == "x" ) {
			System.out.println("here");
			v2 = new double[] {-1 * v1[0], 0};
		}
		if (fix2 && v2[1] == 0 && (isBetween(po1.posY, po2.posY, po1.posY + po1.height) || isBetween(po2.posY, po1.posY, po2.posY + po2.height))
				&& getCollisionDirection(po1, po2) == "x" ) {
			System.out.println("here");
			v1 = new double[] {-1 * v2[0], 0};
		}*/
		
		//Account for fixed objects
		if (fix1 && po1.fixedX) {v1 = new double[] {0,v1[1]};}
		if (fix1 && po1.fixedY) {v1 = new double[] {v1[0],0};}
		if (fix1 && po1.fixedX && po1.fixedY) {v1 = new double[] {0,0};}
		if (fix2 && po2.fixedX) {v2 = new double[] {0,v2[1]};}
		if (fix2 && po2.fixedY) {v2 = new double[] {v2[0],0};}
		if (fix2 && po2.fixedX && po2.fixedY) {v2 = new double[] {0,0};}
		
		double dx1=0, dy1=0, dx2=0, dy2=0;
		
		//move it back in the direction of motion until its no longer colliding
		while (checkCollision(po1, po2)) {
			po1.changePosXYBy(-0.1 * v1[0], -0.1 * v1[1]);
			po2.changePosXYBy(-0.1 * v2[0], -0.1 * v2[1]);
			dx1 += -0.1 * v1[0];
			dy1 += -0.1 * v1[1];
			dx2 += -0.1 * v2[0];
			dy2 += -0.1 * v2[1];
		}
		//move it forward in the direction of motion until its colliding again
		while (!checkCollision(po1, po2)) {
			po1.changePosXYBy(0.01 * v1[0], 0.01 * v1[1]);
			po2.changePosXYBy(0.01 * v2[0], 0.01 * v2[1]);
			dx1 += 0.01 * v1[0];
			dy1 += 0.01 * v1[1];
			dx2 += 0.01 * v2[0];
			dy2 += 0.01 * v2[1];
		}
		//move it backward in the direction of motion until its no longer colliding
		while (checkCollision(po1, po2)) {
			po1.changePosXYBy(-0.001 * v1[0], -0.001 * v1[1]);
			po2.changePosXYBy(-0.001 * v2[0], -0.001 * v2[1]);
			dx1 += -0.001 * v1[0];
			dy1 += -0.001 * v1[1];
			dx2 += -0.001 * v2[0];
			dy2 += -0.001 * v2[1];
		}
		
		//check to see which direction the collision was actually in
		boolean inX, inY;
		po1.changePosXBy(v1[0] * 0.001);
		po2.changePosXBy(v2[0] * 0.001);
		inX = checkCollision(po1, po2);
		po1.changePosXBy(v1[0] * -0.001);
		po2.changePosXBy(v2[0] * -0.001);
		
		po1.changePosYBy(v1[1] * 0.001);
		po2.changePosYBy(v2[1] * 0.001);
		inY = checkCollision(po1, po2);
		po1.changePosYBy(v1[1] * -0.001);
		po2.changePosYBy(v2[1] * -0.001);
		
		//move the object so that it has only been adjusted in the direction of the collision and return the collision direction 
		if (!inX && !inY) {	//I don't think this is ever going to happen
			System.out.println("Corner Collision Occured");
			inY = true;		//this solution is not amazing but it will work for most stuff, to actually fix you're going to have to implement the algebraic adjustment solution
			/*if (Math.random() > 0.5) inX = true;
			else inY = true;*/
		}
		if (inX) {
			po1.changePosYBy(-1*dy1);
			po2.changePosYBy(-1*dy2);
			return "x";
		}
		if (inY) {
			po1.changePosXBy(-1*dx1);
			po2.changePosXBy(-1*dx2);
			return "y";
		}
		System.out.println("Contradicting collision: direction not found");
		return "";
	}
	
	public GameObject[] checkCollisionFiltered(GameObject go1, String className) {
		GameObject[] collides = new GameObject[gm.getObjects().size()];
		int i = 0;
		for (GameObject go2 : gm.getObjects()) {
			if (go2 == go1) continue;
			if (Updateable.instanceOf(go2, className) && checkCollision(go1, go2)) {
				collides[i] = go2;
				i++;
			}
		}
		if (i == 0) return new GameObject[] {};
		GameObject[] temp = new GameObject[i];
		for (int j = 0; j < i; j++) {
			temp[j] = collides[j];
		}
		return temp;
	}
	
	public boolean checkCollision(GameObject go1, GameObject go2) {
		boolean go1IsIO = go1 instanceof ImageObject;
		boolean go2IsIO = go2 instanceof ImageObject;
		if (!go1IsIO && !go2IsIO) {
			return false;
		} else if (!go1IsIO && go2IsIO) {
			return checkCollision((ImageObject)go2, go1);
		} else if (go1IsIO && !go2IsIO) {
			return checkCollision((ImageObject)go1, go2);
		} else {
			return checkCollision((ImageObject)go1, (ImageObject)go2);
		}
	}
	
	public boolean checkCollision(ImageObject io1, ImageObject io2) {
		return checkCollisionBoxOnBox(io1.getPosX(), io1.getPosY(), io1.getWidth(), io1.getHeight(), io2.getPosX(), io2.getPosY(), io2.getWidth(), io2.getHeight());
	}
	
	public boolean checkCollision(ImageObject io, GameObject go) {
		return checkCollisionPointInBox(io.getPosX(), io.getPosY(), io.getWidth(), io.getHeight(), go.getPosX(), go.getPosY());
	}
	
	public boolean checkCollisionBoxOnBox(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
		if ((isBetween(x1, x2, x1+w1) || isBetween(x1, x2+w2, x1+w1)) &&	//check if box2 overlaps with or is contained in box1
				(isBetween(y1, y2, y1+h1) || isBetween(y1, y2+h2, y1+h1))) {
			return true;
		}
		if ((isBetween(x2, x1, x2+w2) || isBetween(x2, x1+w1, x2+w2)) &&	//check if box1 overlaps with or is contained in box2
				(isBetween(y2, y1, y2+h2) || isBetween(y2, y1+h1, y2+h2))) {
			return true;
		}
		return false;
	}
	
	public boolean checkCollisionPointInBox(double x1, double y1, double w1, double h1, double x2, double y2) {
		if (isBetween(x1, x2, x1+w1) && isBetween(y1, y2, y1+h1)) {
			return true;
		}
		return false;
	}
	
	public boolean isBetween(double lower, double between, double upper) {
		if (lower <= between && between <= upper) return true;
		return false;
	}
	
	public String getQuadrant(double[] v) {
		if (v.length != 2) return "v is not a 2d vector";
		//point case
		if (v[0] == 0 && v[1] == 0) return "v is the 0 vector";
		//axis cases
		if (v[0] == 0) return "y";
		if (v[1] == 0) return "x";
		//quadrant cases
		if (v[0] > 0) {
			if (v[1] > 0) return "I";
			else return "IV";
		} else {
			if (v[1] > 0) return "II";
			else return "III";
		}
	}
	
	private String getCollisionDirection(PhysicalObject po1, PhysicalObject po2) {
		//very similar to adjust but must be slightly different, doesn't change position, doesn't fix objects
		if (!checkCollision(po1, po2)) return "alreadyResolved";
		
		double[] v1 = po1.getVelocityVector();
		double[] v2 = po2.getVelocityVector();
		
		double po1x = po1.getPosX(), po1y = po1.getPosY(), po2x = po2.getPosX(), po2y = po2.getPosY();
		
		//move it back in the direction of motion until its no longer colliding
		while (checkCollision(po1, po2)) {
			po1.changePosXYBy(-0.1 * v1[0], -0.1 * v1[1]);
			po2.changePosXYBy(-0.1 * v2[0], -0.1 * v2[1]);
		}
		//move it forward in the direction of motion until its colliding again
		while (!checkCollision(po1, po2)) {
			po1.changePosXYBy(0.01 * v1[0], 0.01 * v1[1]);
			po2.changePosXYBy(0.01 * v2[0], 0.01 * v2[1]);
		}
		//move it backward in the direction of motion until its no longer colliding
		while (checkCollision(po1, po2)) {
			po1.changePosXYBy(-0.001 * v1[0], -0.001 * v1[1]);
			po2.changePosXYBy(-0.001 * v2[0], -0.001 * v2[1]);
		}
		
		//check to see which direction the collision was actually in
		boolean inX, inY;
		po1.changePosXBy(v1[0] * 0.001);
		po2.changePosXBy(v2[0] * 0.001);
		inX = checkCollision(po1, po2);
		po1.changePosXBy(v1[0] * -0.001);
		po2.changePosXBy(v2[0] * -0.001);
		
		po1.changePosYBy(v1[1] * 0.001);
		po2.changePosYBy(v2[1] * 0.001);
		inY = checkCollision(po1, po2);
		po1.changePosYBy(v1[1] * -0.001);
		po2.changePosYBy(v2[1] * -0.001);
		
		//move the object back to its original position
		po1.setPosX(po1x);
		po1.setPosY(po1y);
		po2.setPosX(po2x);
		po2.setPosY(po2y);
		
		if (!inX && !inY) {
			System.out.println("Corner Collision Occured");
			inY = true;
		}
		if (inX) return "x";
		if (inY) return "y";
		System.out.println("Contradicting collision: direction not found");
		return "";
	}
	
	private void addCollisionEffect(PhysicalObject po1, PhysicalObject po2, String dir) {
		collisionEffects.add(new PhysicalObject[] {po1, po2});
		collisionEffectDirs.add(dir);
	}
	
}
