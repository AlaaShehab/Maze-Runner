/**
 *
 */
package enemy_follow_player;

import java.awt.Point;
import java.util.Stack;

import drawables.Drawable;
import drawables.static_drawables.emptyMaze.Empty;
import gameEngine.controller.Controller;
import gameEngine.controller.ImageIconFactory;
import grid.cellComponent.Cell;
import grid.generation.GenerateGame;

/**
 * @author Personal
 *
 */
public class Movement implements Runnable {

	private GenerateGame game;
	private Point monsterPosition;
	Stack<Point> pathOfPoints;
	Cell[][] maze;
	private static int pathLength;

	public Movement (GenerateGame game, Point monsterPosition, Stack<Point> pathOfPoints) {
		this.game = game;
		this.monsterPosition = monsterPosition;
		this.maze = game.getMaze();
		this.pathOfPoints = pathOfPoints;
		pathLength = pathOfPoints.size();

	}

	private void moveObjectTo (Point startPoint, Point endPoint) {
		//((Drawable)(maze[endPoint.x][endPoint.y].getType())).moveTo(endPoint);
		maze[endPoint.x][endPoint.y].setType(maze[startPoint.x][startPoint.y].getType());
		String imgPath = ((Drawable)maze[startPoint.x][startPoint.y].getType()).getImageDirectory();
		System.out.println(imgPath);
		Controller.guiMaze[endPoint.x][endPoint.y].setIcon(ImageIconFactory.getImageIcon(imgPath));
		maze[startPoint.x][startPoint.y].setType(new Empty());
		Controller.guiMaze[startPoint.x][startPoint.y].setIcon(null);
		
	}
	@Override
	public void run() {

		int i = 0;
		while(!pathOfPoints.isEmpty()){
			Point transitionPoint = pathOfPoints.remove(0);
			System.out.println(transitionPoint.x + "  " + transitionPoint.y);
			moveObjectTo(monsterPosition, transitionPoint);
			monsterPosition = transitionPoint;
			pathOfPoints.push(transitionPoint);
			i++;
			if (i == pathLength) {
				pathOfPoints = reverseStack();
				i = 0;
			}


			try {
				Thread.sleep(50L);
			} catch (InterruptedException e) {
				System.out.println("thread Interrupted");
			}
		}


	}

	private Stack<Point> reverseStack() {
		Stack<Point> reversedStack = new Stack<Point>();
		while (!(pathOfPoints.isEmpty())) {
			reversedStack.push(pathOfPoints.pop());
		}
		return reversedStack;
	}

}
