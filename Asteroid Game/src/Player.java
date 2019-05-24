
import javax.swing.*;
import java.awt.*;

/**
 * <strong>Purpose:</strong><br>
 * This class was created as a part of the 'Asteroids!' game(See
 * <code>Game.java</code>).<br>
 * This class represents the player.<br>
 * The player has dimensions, x- and y-coordinates along with shape. <br>
 * <strong>Date of Creation:</strong><br>
 * 04.05.17<br>
 * <br>
 * <strong>Author:</strong><br>
 * Kirill Proshakov
 * 
 * @see Game
 * @see LaserBeam
 * @see Asteroid
 * @see Spaceship
 */
public class Player
{
	private ImageIcon img, imgDead;
	private boolean isDead;
	private int xPos, yPos, width, height, direction, movement, counter;
	public static final int EAST = 1, WEST = 0, NORTH = 3, SOUTH = 4, NORTHWEST = 5, NORTHEAST = 6, SOUTHWEST = 7,
			SOUTHEAST = 8, STOP = -1;

	/**
	 * Sets all the variable to initial values.
	 * 
	 * @param X-coordinate
	 *            of the initial point.
	 * @param Y-coordinate
	 *            of the initial point.
	 */
	public Player(int x, int y)
	{
		isDead = false;
		direction = EAST;

		img = new ImageIcon(getClass().getResource("ship.png"));
		imgDead = new ImageIcon(getClass().getResource("ex.png"));

		xPos = x;
		yPos = y;

		width = img.getIconWidth();
		height = img.getIconHeight();
		counter = 0;
	}

	/**
	 * Draws a picture of the laser beam depending in its state.
	 */
	public void draw(Graphics2D g2)
	{
		if (!isDead)
		{

			g2.drawImage(img.getImage(), xPos, yPos, null);

		}
		else
		{
			counter++;
			g2.drawImage(imgDead.getImage(), xPos, yPos, null);
		}
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
	 * @return The direction the player faces.
	 */
	public int getDirection()
	{
		return direction;
	}

	/**
	 * 
	 * @return The direction of the player's movement.
	 */
	public int getMovement()
	{
		return movement;
	}

	/**
	 * 
	 * @return The height of the player(player's icon).
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * 
	 * @return The width of the player(player's icon).
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * 
	 * @return The current x-coordinate if the player.
	 */
	public int getX()
	{
		return xPos;
	}

	/**
	 * 
	 * @return The current y-coordinate of the player.
	 */
	public Integer getY()
	{
		return yPos;
	}

	/**
	 * 
	 * @return The current x-coordinate of the center of the player's icon.
	 */
	public int getCX()
	{
		return xPos + width / 2;
	}

	/**
	 * 
	 * @return The y-coordinate of the center of the player's icon.
	 */
	public int getCY()
	{
		return yPos + height / 2;
	}

	/**
	 * 
	 * @return The current state of the player.
	 */
	public boolean isDead()
	{
		return isDead;
	}

	/**
	 * 
	 * @return The shape of the player.(player's icon).
	 */
	public Rectangle getRec()
	{
		return new Rectangle(xPos, yPos, width, height);
	}

	/**
	 * Kills the player.<br>
	 * When the player is killed, it can no longer destroy any asteroids(See
	 * <code>Asteroid.java</code>) or shoot laser beams(See
	 * <code>LaserBeam.java</code>).<br>
	 */
	public void killPlayer()
	{
		isDead = true;
	}

	/**
	 * Moves the player in the direction of its movement.<br>
	 * This method moves the player on a fixed amount of units.<br>
	 */
	public void move()
	{
		if (!isDead)
		{
			if (movement == EAST)
			{
				xPos += 3;
			}
			else if (movement == WEST)
			{
				xPos -= 3;
			}
			else if (movement == NORTH)
			{
				yPos -= 3;
			}
			else if (movement == SOUTH)
			{
				yPos += 3;
			}
			else if (movement == NORTHWEST)
			{
				xPos -= 2;
				yPos -= 2;
			}
			else if (movement == NORTHEAST)
			{
				xPos += 2;
				yPos -= 2;
			}
			else if (movement == SOUTHWEST)
			{
				xPos -= 2;
				yPos += 2;
			}
			else if (movement == SOUTHEAST)
			{
				xPos += 2;
				yPos += 2;
			}
		}
	}

	/**
	 * 
	 * @param The
	 *            distance at which the player is to be moved.<br>
	 *            The player gets moved in the direction of its movement
	 *            regardless of the value of the parameters.
	 */
	public void move(int... x)
	{
		if (!isDead)
		{
			if (x.length == 1)
			{
				if (movement == EAST)
				{
					xPos += x[0];
				}
				else if (movement == WEST)
				{
					xPos -= x[0];
				}
				else if (movement == NORTH)
				{
					yPos -= x[0];
				}
				else if (movement == SOUTH)
				{
					yPos += x[0];
				}
			}
			else if (x.length == 2)
			{
				if (movement == NORTHWEST)
				{
					xPos -= x[0];
					yPos += x[1];
				}
				else if (movement == NORTHEAST)
				{
					xPos += x[0];
					yPos += x[1];
				}
				else if (movement == SOUTHWEST)
				{
					xPos -= x[0];
					yPos -= x[1];
				}
				else if (movement == SOUTHEAST)
				{
					xPos += x[0];
					yPos -= x[1];
				}
			}
		}
	}

	/**
	 * 
	 * @param The
	 *            direction the player is to face.
	 */
	public void setDirection(int dir)
	{
		direction = dir;
	}

	/**
	 * 
	 * @param The
	 *            direction of movement to be set.
	 */
	public void setMovement(int mov)
	{
		movement = mov;
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
	 * @param The
	 *            x-coordinate to be set.
	 * @param The
	 *            y-coordinate to be set.
	 */
	public void setLocation(int x, int y)
	{
		xPos = x;
		yPos = y;
	}
}
