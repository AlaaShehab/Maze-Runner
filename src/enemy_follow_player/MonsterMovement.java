/**
 *
 */
package enemy_follow_player;

import java.awt.Point;
import java.util.Stack;

import grid.cellComponent.Cell;
import grid.generation.GenerateGame;

/**
 * @author Personal
 *
 */
public class MonsterMovement {

	private Cell[][] maze;
	private GenerateGame game;
	private Point monsterCenterPoint;
	private Point playerPosition;

	public MonsterMovement(GenerateGame game, Point monsterCenterPoint, Point playerPosition) {
		this.maze = game.getMaze();
		this.game = game;
		this.monsterCenterPoint = monsterCenterPoint;
		this.playerPosition = playerPosition;

	}

	public void moveMonster() {
			FindPath path = new FindPath(game.getMaze(), game.getLevel().getMazeSize());
			Stack<Point> pathOfPoints = path.getValidPath(monsterCenterPoint, playerPosition);

			Thread move = new Thread(new Movement(game, monsterCenterPoint, pathOfPoints), "Moving monster");
			move.start();
	}
}
