package com.example.demo.entity.plane;

import com.example.demo.Controller;
import com.example.demo.util.Vector;

public class BasicEnemy extends EnemyPlane {

	public BasicEnemy(Controller controller, double initialX, Vector finalPosition) {
		super(controller, new BasicEnemyData(initialX, finalPosition));
	}
}
