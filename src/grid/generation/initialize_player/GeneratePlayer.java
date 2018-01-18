/**
 *
 */
package grid.generation.initialize_player;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import drawables.dynamic_drawables.bullet.Bullet;
import drawables.dynamic_drawables.bullet.derivatives.FireBullet;
import drawables.dynamic_drawables.bullet.derivatives.IceBullet;
import drawables.dynamic_drawables.character.player.Player;
import grid.Levels.Level;
import grid.cellComponent.Cell;

/**
 * @author Personal
 *
 */
public class GeneratePlayer {

	final static Logger log = Logger.getLogger(GeneratePlayer.class.getName());

	Player player;
	public GeneratePlayer (Player player) {
		this.player = player;
	}

	public void generatePlayer(Cell[][] maze, Level level){
		log.info("Generating Player.....");

		log.info("Setting Player position");
		maze[1][1] = new Cell<Player>(player);
		generateBullets(level);
		log.info("Player Generating Successfully!");

	}
	private void generateBullets (Level level) {
		log.info("Generating player bullets");
		int iceBullets = level.getIceBulletNumber();
		int fireBullets = level.getFireBulletNumber();

		List<Bullet> bullets = new ArrayList<Bullet>();
		for (int i = 0; i < iceBullets; i++) {
			bullets.add(new IceBullet());
		}
		for (int i = 0; i < fireBullets; i++) {
			bullets.add(new FireBullet());
		}
		player.addBullets(bullets);
	}
}
