import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import java.util.Random;

/**
 * <strong>Purpose:</strong><br>
 * This class was created as a part of the 'Asteroids!' game(See
 * <code>Game.java</code>).<br>
 * This class represents one asteroid. An asteroid can has three different
 * levels of durability.<br>
 * Each level has its own icon. Each asteroid moves with a different speed,
 * randomized in the constructor.<br>
 * Also, each object keeps track of damage it gains from the player(See
 * <code>Spaceship.java</code>).<br>
 * Depending on the durability level, each asteroid requires different amounts
 * of laser beams(See <code>LaseBeam.java</code>) to get destroyed.<br>
 * <br>
 * <strong>Date of Creation:</strong><br>
 * 04.05.17<br>
 * <br>
 * 
 * 
 * <br>
 * <strong>Author:</strong><br>
 * Kirill Proshakov
 * 
 * @see Game
 * @see Spaceship
 * @see LaserBeam
 */
public class Asteroid
{

	public ImageIcon img, imgExp;
	public int xPos, yPos, speed, width, height, durability, damage, counter;
	public Random rnd;
	public boolean exploded;

	/**
	 * Sets all the variable to initial values, randomizes the durability level
	 * and speed.
	 */
	public Asteroid()
	{
		counter = 0;

		rnd = new Random();

		durability = rnd.nextInt(3) + 1;

		switch (durability)
		{
			case 1:
				img = new ImageIcon(getClass().getResource("asteroid0.png"));
				imgExp = new ImageIcon(getClass().getResource("ex1.png"));
				break;
			case 2:
				img = new ImageIcon(getClass().getResource("asteroid1.png"));
				imgExp = new ImageIcon(getClass().getResource("ex2.png"));
				break;
			case 3:
				img = new ImageIcon(getClass().getResource("asteroid2.png"));
				imgExp = new ImageIcon(getClass().getResource("ex3.png"));
				break;
		}

		width = img.getIconWidth();
		height = img.getIconHeight();

		xPos = Game.imgBackground.getIconWidth();
		yPos = rnd.nextInt(Game.imgBackground.getIconHeight() - height);

		speed = rnd.nextInt(3) + 1;

		exploded = false;
	}

	/**
	 * Draws a picture of the asteroid depending in its state.
	 * 
	 */
	public void draw(Graphics2D g2)
	{

		if (!exploded)
		{
			g2.drawImage(img.getImage(), xPos, yPos, null);
		}
		else
		{
			counter++;
			g2.drawImage(imgExp.getImage(), xPos, yPos, null);
		}
	}

	/**
	 * Moves the asteroid along its axis with its unique speed.
	 */
	public void move()
	{
		if (!exploded)
		{
			xPos -= speed;
		}
	}

	/**
	 * Damages the asteroid.<br>
	 * Called when a laser beam(See <code>LaserBeam.java</code>) touches the
	 * asteroid.
	 */
	public void hit()
	{
		damage++;
		if (damage == durability)
		{
			exploded = true;
		}
	}

	/**
	 * @return The current state of the asteroid.(Destroyed or not)
	 */
	public boolean isDestroyed()
	{
		return exploded;
	}

	/**
	 * 
	 * @return The score, which should be gained by the player, when he destroys
	 *         the asteroid. The value varies depending on the durability level.
	 * 
	 */
	public int getScore()
	{
		switch (durability)
		{
			case 1:
				return 20;
			case 2:
				return 30;
			case 3:
				return 40;
		}
		return 0;
	}

	/**
	 * 
	 * @return The width of the icon.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * 
	 * @return The height of the icon.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * 
	 * @return The time it remained destroyed, while being desplayed on the
	 *         field.
	 */
	public int getCounter()
	{
		return counter;
	}

	/**
	 * 
	 * @return The current x-coordinate of the asteroid.
	 */
	public int getX()
	{

		return xPos;
	}

	/**
	 * 
	 * @return The current y-coordinate of the asteroid.
	 */
	public int getY()
	{

		return yPos;
	}

	/**
	 * 
	 * @return The shape of the asteroid, represented by an
	 *         <code>Ellipse2D</code> object.
	 */
	public Ellipse2D getRec()
	{
		return new Ellipse2D.Double(xPos, yPos, width, height);
	}

	/**
	 * 
	 * @param The
	 *            x-coordinate to be set.
	 */
	public void setX(int x)
	{
		xPos = x;

	}

	/**
	 * 
	 * @param The
	 *            y-coordinate to be set.
	 */
	public void setY(int y)
	{
		xPos = y;

	}

	/**
	 * Re-generates the y-coordinate of the asteroid.<br>
	 * Needed to avoid overlapping.
	 */
	public void respawn()
	{
		xPos = Game.imgBackground.getIconWidth();
		yPos = rnd.nextInt(Game.imgBackground.getIconHeight() - width);

	}
}
