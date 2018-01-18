/**
 *
 */
package grid.generation.initialize_components;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import grid.Levels.Level;
import grid.cellComponent.Cell;
import grid.generation.initialize_maze.Maze;

/**
 * @author Personal
 *
 */
public class GenerateComponent {

	private Level level;
	private Maze maze;
	private int giftBlockage;
	private byte[][] grid;
	private JLabel[][] guiMaze = new JLabel[31][31];
	private List <Point> giftsPlaces;
	private int height;
	private int width;
	Random random = new Random(System.currentTimeMillis());
	List<Point> monstersPositions;

	final static Logger log = Logger.getLogger(GenerateComponent.class.getName());


	public GenerateComponent (Level level, Maze maze) {
		this.level = level;
		this.maze = maze;

		grid = this.maze.getGrid();
		height = level.getMazeSize().x;
		width = level.getMazeSize().y;

		monstersPositions = new ArrayList<Point>();

	}


	public void generateGridComponents () {
		log.info("Generating Maze Components....");

		giftBlockage = this.level.getBlockedGiftNumber();
		giftsPlaces = new ArrayList<Point>();
		log.info("Generating Gifts....");
		generateGift(level.getGiftNumber());
		log.info("Gifts Generated Successfully");
		log.info("Generating Trees....");
		generateTrees(level.getTreesNumber());
		log.info("Trees Generated Successfully");
		log.info("Generating Bombs....");
		generateStrongBomb(level.getStrongBombNumber());
		log.info("Bombs Generated Successfully");
		generateWeakBomb(level.getWeakBombNumber());
		log.info("Generating Monsters....");
		generateIceMonster(level.getIceMonsterNumber());
		generateFireMonster(level.getFireMonsterNumber());
		log.info("Monsters Generated Successfully");
		log.info("Generating Coins....");
		generateBronzeCoin(level.getBronzeCoinNumber());
		generateGoldCoin(level.getGoldCoinNumber());
		generateSilverCoin(level.getSilverCoinNumber());
		log.info("Coins Generated Successfully");

	}

	private void generateStrongBomb(int number) {
		for (int i = 0; i < number; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			if (grid [randomX][randomY] == 1 && !StartOrEnd(randomX, randomY) && notSameComponent(randomX, randomY, 2)) {
				grid [randomX][randomY] = 2;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
	}

	private void generateWeakBomb (int number) {
		for (int i = 0; i < number; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			if (grid [randomX][randomY] == 1 && !StartOrEnd(randomX, randomY) && notSameComponent(randomX, randomY, 3)) {
				grid [randomX][randomY] = 3;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
	}

	private void generateIceMonster (int number) {
		for (int i = 0; i < number; i++) {
			int randomX = (int)(Math.random()* width);
			int randomY = (height/3)*2 + (int)(Math.random()*(height/3)+1);
			if (grid [randomX][randomY] == 1 && !StartOrEnd(randomX, randomY) && notSameComponent(randomX, randomY, 4)) {
				grid [randomX][randomY] = 4;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
	}

	private void generateFireMonster(int number) {
		for (int i = 0; i < number; i++) {
			int randomX = (width/3)*2 + (int)(Math.random()*(width/3)+1);
			int randomY = (int)(Math.random()*(height));
			if (grid [randomX][randomY] == 1 && !StartOrEnd(randomX, randomY) && notSameComponent(randomX, randomY, 10)) {
				grid [randomX][randomY] = 10;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
	}

	private void generateTrees (int number) {
		int pathTrees = number - giftBlockage;
		for (int i = 0; i < pathTrees; i++) {
			int randomPoint = random.nextInt(maze.getPath().size()/2);
			Point treePlace = maze.getPath().get(randomPoint);
			if (grid [treePlace.x][treePlace.y] == 1 && !StartOrEnd(treePlace.x, treePlace.y)
					 && notSameComponent(treePlace.x, treePlace.y, 5)) {
				grid [treePlace.x][treePlace.y] = 5;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
		for (int i = 0, j =0; i < giftBlockage && j < giftsPlaces.size(); i ++) {
			Point treePlace = findTreePlace(giftsPlaces.get(j));
			if (grid [treePlace.x][treePlace.y] == 1 && !StartOrEnd(treePlace.x, treePlace.y)) {
				grid [treePlace.x][treePlace.y] = 5;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				j++;
				i--;
			}
		}
	}

	private void generateGift (int number) {
		for (int i = 0; i < number; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			if (grid [randomX][randomY] == 1 && !StartOrEnd(randomX, randomY) && notSameComponent(randomX, randomY, 6)) {
				if (i == 0){
					grid [randomX][randomY] = 11;
				} else {
					grid [randomX][randomY] = 6;
				}
				Point point = new Point(randomX, randomY);
				giftsPlaces.add(point);
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
	}

	private void generateBronzeCoin (int number) {
		for (int i = 0; i < number; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			if (grid [randomX][randomY] == 1 && !StartOrEnd(randomX, randomY) && notSameComponent(randomX, randomY, 7)) {
				grid [randomX][randomY] = 7;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
	}

	private void generateGoldCoin (int number) {
		Point middle = maze.getPathMiddlePoint();
		for (int i = 0; i < number; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			if (grid [randomX][randomY] == 1 && !StartOrEnd(randomX, randomY) && notSameComponent(randomX, randomY, 8)) {
				grid [randomX][randomY] = 8;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
	}

	private void generateSilverCoin (int number) {
		Point middle = maze.getPathMiddlePoint();
		for (int i = 0; i < number; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			if (grid [randomX][randomY] == 1 && !StartOrEnd(randomX, randomY) && notSameComponent(randomX, randomY, 9)) {
				grid [randomX][randomY] = 9;
			} else {
				log.warn("Couldn't generate in component in an invalid cell");
				i--;
			}
		}
	}

	private boolean StartOrEnd (int x, int y) {
		if ((x == 0 && y == 1) ||
				(x == 1 && y == 1) || (x == height - 1 && y == grid.length - 2)
				|| (x == height - 2 && y == grid.length - 2)) {
			return true;
		}
		return false;
	}
    public void print() {
        for(int x = 0; x < height; x++) {
           for(int y = 0; y < width; y++) {
              if(grid[x][y] == 0) {
                 System.out.print("[]");
              } else if (grid[x][y] == 4 || grid[x][y] == 10){
                 System.out.print("dr");
              } else {
                   System.out.print("  ");
              }
           }
           System.out.println();
        }
     }

	private Point findTreePlace (Point giftPlace) {
		byte[][] grid = maze.getGrid();
		grid[1][1] = 9;
		grid[giftPlace.x][giftPlace.y] = 0;
		if (pathFound(new Point(giftPlace.x +1, giftPlace.y), grid)) {
			return new Point(giftPlace.x +1, giftPlace.y);
		} else if (pathFound(new Point(giftPlace.x -1, giftPlace.y), grid)) {
			return new Point(giftPlace.x -1, giftPlace.y);
		} else if (pathFound(new Point(giftPlace.x, giftPlace.y + 1), grid)) {
			return new Point(giftPlace.x, giftPlace.y + 1);
		} else {
			return new Point(giftPlace.x, giftPlace.y - 1);
		}

	}

	private boolean pathFound (Point start, byte[][] grid) {
		if (grid[start.x][start.y] == 0) {
			return false;
		}
		Stack<Point> stack = new Stack<Point>();
		stack.push(start);
		boolean[][] visited = new boolean[height][width];

		while (!(stack.isEmpty())) {
			start = stack.peek();
			visited[start.x][start.y] = true;
			int[] y = {0, 0, -1, 1};
			int[] x = {1, -1, 0, 0};
			if (grid[start.x][start.y] == 9) {
				return true;
			}
			int i = 0;
			for (i = 0; i < x.length; i++) {
				int rowNum = start.x + x[i];
				int colNum = start.y + y[i];
				if (rowNum < height && colNum < width
				&& rowNum >= 0 && colNum >= 0
				&& !visited[rowNum][colNum]
				&& grid[rowNum][colNum] != 0) {
					Point p = new Point(rowNum, colNum);
					stack.push(p);
						break;
				}
			}
			if (i == x.length) {
				stack.pop();
			}
		}

		return false;
	}

	public Cell[][] getCellMaze () {
		Cell[][] theMaze = new Cell[height][width];
		CellFactory factory = new Factory();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				theMaze[i][j] = factory.getCellClass(grid[i][j]);
				if (grid[i][j] == 4 || grid[i][j] == 10) {
					monstersPositions.add(new Point(i, j));
				}
			}
		}
		return theMaze;

	}

	public JLabel[][] getGUImaze(){
		return guiMaze;
	}

	private boolean notSameComponent (int x, int y, int type) {
		for (int m = x - 1; m < x + 2; m++) {
			for (int n = y - 1; n < y + 2; n++) {
				if (grid[m][n] == type) {
					return false;
				}
			}
		}
		return true;
	}

	public List<Point> getMonstersPositions () {
		return monstersPositions;
	}
}
