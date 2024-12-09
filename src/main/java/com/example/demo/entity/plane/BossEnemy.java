package com.example.demo.entity.plane;

import com.example.demo.Controller;
import com.example.demo.util.Vector;

public class BossEnemy extends EnemyPlane {

	public BossEnemy(Controller controller, double initialX, Vector finalPosition) {
		super(controller, new BossEnemyData(initialX, finalPosition));
	}
}
