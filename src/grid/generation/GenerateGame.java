/**
 *
 */
package grid.generation;

import java.awt.Point;
import java.util.List;

import org.apache.log4j.Logger;

import drawables.dynamic_drawables.character.player.Player;
import enemy_follow_player.MonsterMovement;
import grid.Levels.Level;
import grid.Levels.builder.ILevelBuilder;
import grid.Levels.builder.generate.GenerateLevel;
import grid.cellComponent.Cell;
import grid.generation.initialize_components.GenerateComponent;
import grid.generation.initialize_maze.Maze;
import grid.generation.initialize_player.GeneratePlayer;

/**
 * @author Personal
 *
 */
public class GenerateGame implements IGameGeneration{
	private Player player;
	private Level level;
	private GenerateComponent g;

	private Cell[][] maze;
	final static Logger log = Logger.getLogger(GenerateGame.class.getName());

	public GenerateGame (ILevelBuilder levelBuilder, Player player) {
		this.player = player;
		setILevelBuilder(levelBuilder);
	}


	@Override
	public Player getPlayer() {
		return player;
	}


	private void setILevelBuilder(ILevelBuilder levelBuilder) {
		GenerateLevel g = new GenerateLevel(levelBuilder);
		g.generate();
		level = g.getLevel();

	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public void generateMaze() {
		log.info("Generating Game ......");
		Maze m = new Maze(level.getMazeSize());
		m.generate();

		g = new GenerateComponent(level, m);
		g.generateGridComponents();


		maze = g.getCellMaze();
		GeneratePlayer p = new GeneratePlayer(player);
		p.generatePlayer(maze, level);

		//setMonsterMovements(g);

	}

	@Override
	public Cell[][] getMaze() {
		return maze;
	}
	public GenerateComponent getGenerateComponent () {
		return g;
	}
	public void setMonsterMovements (GenerateComponent g) {
		log.info("Preparing Multithread Monster Movement");
		List<Point> monsterPosistions = g.getMonstersPositions();
		for (int i = 0; i < monsterPosistions.size(); i++) {
			MonsterMovement move = new MonsterMovement(this, monsterPosistions.get(i), new Point(1,1));
			move.moveMonster();
		}
	}
}
