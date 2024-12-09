package com.example.demo.entity.plane;

import com.example.demo.Controller;
import com.example.demo.util.Vector;

public class AdvancedEnemy extends EnemyPlane {

	public AdvancedEnemy(Controller controller, double initialX, Vector finalPosition) {
		super(controller, new AdvancedEnemyData(initialX, finalPosition));
	}
}
