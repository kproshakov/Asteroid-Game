
import javax.swing.*;
import java.awt.*;

/**
 * <strong>Purpose:</strong><br>
 * This class was created as a part of the 'Asteroids!' game(See
 * <code>Game.java</code>).<br>
 * This class represents one laser beam shot by the player.<br>
 * Each object keeps track of its state.<br>
 * A laser beam needs x- and y-coordinates of the initial point to be
 * created.<br>
 * Each laser beam moves independently from other similar objects and from the
 * player(See <code>Spaceship.java</code>).<br>
 * <br>
 * <strong>Date of Creation:</strong><br>
 * 04.05.17<br>
 * <br>
 * <strong>Author:</strong><br>
 * Kirill Proshakov
 * 
 * @see Game
 * @see Spaceship
 * @see Asteroid
 */
public class LaserBeam
{
	public ImageIcon img;
	public int xPos, yPos, width, height;
	public boolean destroyedAsteroid = false;

	/**
	 * Sets all the variable to initial values.
	 * 
	 * @param X-coordinate
	 *            of the initial point.
	 * @param Y-coordinate
	 *            of the initial point.
	 */
	public LaserBeam(int x, int y)
	{
		img = new ImageIcon(getClass().getResource("beam.png"));

		xPos = x;
		yPos = y;

		width = img.getIconWidth();
		height = img.getIconHeight();
	}

	/**
	 * Draws a picture of the laser beam depending in its state.
	 */
	public void draw(Graphics2D g2)
	{

		if (!destroyedAsteroid)
		{
			g2.drawImage(img.getImage(), xPos, yPos, null);
		}

	}

	/**
	 * Changes the current state of the laser beam.<br>
	 * Called when the laser beam touches the asteroid.<br>
	 * When the state is changed, it is no longer capable of destroying other
	 * asteroids or being displayed.<br>
	 */
	public void destroyAsteroid()
	{
		destroyedAsteroid = true;
	}

	/**
	 * Moves the laser beam along its axis with a constant speed.<br>
	 * All laser beam have the same speed and destructive force.
	 */
	public void move()
	{
		xPos += 5;
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
		yPos = y;
	}

	/**
	 * 
	 * @return The state of the laser beam.
	 */
	public boolean isDestroyed()
	{
		return destroyedAsteroid;
	}

	/**
	 * 
	 * @return The current x-coordinate.
	 */
	public int getX()
	{
		return xPos;
	}

	/**
	 * 
	 * @return The current y-coordinate.
	 */

	public int getY()
	{
		return xPos;
	}

	/**
	 * 
	 * @return The shape of the laser beam.<br>
	 *         All laser beams have the same shape.
	 */
	public Rectangle getRec()
	{
		return new Rectangle(xPos, yPos, width, height);
	}

}
