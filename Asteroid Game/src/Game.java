
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

/**
 * <strong>Purpose:</strong><br>
 * This class is a GUI shell for the 'Asteroids' game<br>
 * This class combines <code>Spaceship</code>, <code>Bullet</code> and
 * <code>Asteroid</code> classes.<br>
 * <br>
 * <strong>Date of Creation:</strong><br>
 * 01.05.17<br>
 * <br>
 * <strong>Author:</strong><br>
 * Kirill Proshakov
 * @see Spaceship
 * @see LaserBeam
 * @see Asteroid
 */
public class Game extends JPanel
{
	/**
	 * Variables :
	 * 
	 * ImageIcons : 
	 * 'Background' contains the image of the space. 
	 * 'Title' contains the image with the game title. 
	 * 'Edition' contains the image with the edition of the game(Star Wars). 
	 * 'Life' contains the image with a single picture of an icon that represents one life(R2-D2).
	 * 
	 * Timers : 'Title' is used for the opening animation. Though, after the
	 * animation is done, it keeps shooting, when the user is holding 'space'.
	 * 'Key' listens to the keys pressed, and redirects the player if it reaches
	 * either of frame's edges. 
	 * 'Bullet' moves all shot bullets along with asteroids. It also spawns new asteroids and reloads the millenium falcon's guns.
	 * 
	 * Spaceship : 
	 * 'player' is an object that represents millenium falcon. (See Player.java & Spaceship.java)
	 * 
	 * LaserBeam[] : 
	 * 'round' represents one round of bullets with a size of 20 units
	 * 
	 * Integer : 'life' contains the number of lifes left 
	 * 'yTitle' contains the y-coordinate of the title during the opening animation. 
	 * 'begin' contains the stage number of the game(0-1.Opening animation. 2.The acual game).
	 * 'bulletCounter' contains the number of bullets left in the round.
	 * 'asteroidCounter' contains the number of asteroids can be added to the field. 
	 * 'time' is used to coordinate the movements of Graphics2D
	 * components during the opening, as well as the time delay while reloading.
	 * 'score' contains the score gained through destroying asteroids.
	 * 
	 * Boolean : 
	 * 'spacePressed' indicates if the 'space' button is being
	 * pressed. 
	 * 'reloading' indicates if the weapons are currently being
	 * reloaded.
	 * 
	 * Asteroid[] : 
	 * 'field' contains 'Asteroid' objects, which represent all the asteroids on the game field.
	 * 
	 * Random : 'rnd' - utility.
	 */

	private JFrame frame;
	public static ImageIcon imgBackground, imgTitle, imgEdition, imgLife;
	private Timer tmrTitle, tmrKey, tmrBullet;
	private Spaceship player;
	private LaserBeam[] round;
	private Integer life, yTitle, begin, bulletCounter, asteroidCounter, time, score;
	private boolean spacePressed, reloading;
	private Asteroid[] field;
	private Random rnd;

	/**
	 * Builds the frame, sets all the variable to initial values.
	 */
	public Game()
	{

		rnd = new Random();

		time = 0;
		life = 3;
		yTitle = 200;
		begin = 0;
		bulletCounter = 20;
		asteroidCounter = 15;

		reloading = false;
		spacePressed = false;
		score = 0;

		tmrTitle = new Timer(80, new TimerListener());
		tmrKey = new Timer(50, new TimerListener());
		tmrBullet = new Timer(30, new TimerListener());

		imgBackground = new ImageIcon(getClass().getResource("backgroung.png"));

		imgTitle = new ImageIcon(getClass().getResource("asteroids_title.png"));

		imgEdition = new ImageIcon(getClass().getResource("star-wars.png"));

		imgLife = new ImageIcon(getClass().getResource("r2.png"));

		addKeyListener(new KeyboardListener());
		setLayout(null);
		setFocusable(true);

		frame = new JFrame();
		frame.setTitle("Asteroids");
		frame.setSize(imgBackground.getIconWidth(), imgBackground.getIconHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		frame.add(this);

		player = new Spaceship(30, imgBackground.getIconHeight() / 2 - 30);

		round = new LaserBeam[bulletCounter];

		field = new Asteroid[asteroidCounter];

		tmrTitle.start();

	}

	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.drawImage(imgBackground.getImage(), 0, 0, null);

		if (begin == 1)// Moves the game title up.
		{
			g2.drawImage(imgTitle.getImage(), frame.getWidth() / 2 - imgTitle.getIconWidth() / 2, yTitle, null);
			g2.drawImage(imgEdition.getImage(), frame.getWidth() / 2 - imgTitle.getIconWidth() / 2 + 270,
					yTitle + imgTitle.getIconHeight() - 90, null);
		}
		else if (begin == 0)// Shows the opening phrase from Star Wars
		{
			g2.setColor(Color.BLACK);
			g2.fill(new Rectangle(0, 0, frame.getWidth(), frame.getHeight()));
			g2.setColor(new Color(102, 204, 255));
			FontMetrics fm = getFontMetrics(new Font("Times New Roman", Font.PLAIN, 30));

			int str = fm.stringWidth("A long time ago in a galaxy far,\nfar away....");

			g2.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			g2.drawString("A long time ago in a galaxy far, far away....", frame.getWidth() / 2 - str / 2,
					frame.getHeight() / 2 - fm.getAscent() / 2);
		}
		else
		{

			player.draw(g2);// Updates the millenium falcon icon on the current
							// player's position

			if (reloading)// Updates the countdown of the reloading process.
			{
				g2.setFont(new Font("Times New Roman", Font.PLAIN, 17));
				g2.setColor(new Color(102, 204, 255));
				g2.drawString("Reloading: " + "" + (8 - (time * 30) / 1000) + "." + (1000 - (time * 30) % 1000),
						player.getX() + player.getWidth(), player.getY());
			}

			for (int i = 0; i < life; i++)// Updats the icons in the top left
											// corner that stand for lifes left.
			{
				g2.drawImage(imgLife.getImage(), frame.getWidth() + (imgLife.getIconWidth() * (i + 1)) + (i * 4) - 185,
						3, null);
			}

			g2.setFont(new Font("Times New Roman", Font.PLAIN, 23));
			g2.setColor(Color.WHITE);// Updates the bullet indicator, which show
										// the current amount of bullets left
			g2.drawString("You have: " + bulletCounter + " bullets", 10, frame.getHeight() - 50);
			
			// Updates the score indicator, which show the score gained through destroying asteroids.
			g2.drawString("Score: " + score, 10, 17);

			for (int i = 0; i < field.length; i++)// Updates the icons of all the asteroids within the field.
			{
				try
				{

					field[i].draw(g2);

				}
				catch (NullPointerException f)
				{
				}
			}

			for (int i = 0; i < round.length - bulletCounter; i++)
			{// Updates the position of each shot bullet.
				round[i].draw(g2);
			}
			
			// Makes time delay, needed for player to realize it's failure.
			if (player.isDead() && player.getCounter() == 15)
			{

				life--;
				JOptionPane.showMessageDialog(null, "You are dead!\nYou have " + life + " lifes left!");

				clearField(g2);
				
				// Puts the player's model to the initial position.
				player = new Spaceship(30, imgBackground.getIconHeight() / 2 - 30);

				tmrBullet.start();
				tmrTitle.start();
			}
		}
	}

	/**
	 * Clears the field after the player dies.<br>
	 * Reloads the weapons.<br>
	 */
	public void clearField(Graphics2D g2)
	{
		asteroidCounter = 15;
		field = new Asteroid[asteroidCounter];

		bulletCounter = 20;
		round = new LaserBeam[bulletCounter];
	}

	private class KeyboardListener implements KeyListener
	{
		// This 'HashSet' object is needed, in order to listen to two keys at a time and make player capable of moving diagonally.
		Set<Character> keys = new HashSet<Character>();

		public void keyPressed(KeyEvent e)
		{
			if (begin == 2)
			{// Keys can be read only after the opening animation is done.
				keys.add(e.getKeyChar());

				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					keys.remove(e.getKeyChar());
					spacePressed = true;
				}

				if (keys.size() == 1)
				{

					if (e.getKeyCode() == KeyEvent.VK_W)
					{
						player.setMovement(Player.NORTH);
					}
					else if (e.getKeyCode() == KeyEvent.VK_S)
					{
						player.setMovement(Player.SOUTH);
					}
					else if (e.getKeyCode() == KeyEvent.VK_A)
					{
						player.setMovement(Player.WEST);
					}
					else if (e.getKeyCode() == KeyEvent.VK_D)
					{
						player.setMovement(Player.EAST);
					}
					tmrKey.start();
				}
				else
				{

					Character c = e.getKeyChar();
					if (c == 'w')
					{

						if (keys.contains('d'))
						{

							player.setMovement(Player.NORTHEAST);
						}
						else if (keys.contains('a'))
						{
							player.setMovement(Player.NORTHWEST);
						}
					}
					else if (c == 'a')
					{
						if (keys.contains('w'))
						{
							player.setMovement(Player.NORTHWEST);
						}
						else if (keys.contains('s'))
						{
							player.setMovement(Player.SOUTHWEST);
						}
					}
					else if (c == 's')
					{
						if (keys.contains('a'))
						{
							player.setMovement(Player.SOUTHWEST);
						}
						else if (keys.contains('d'))
						{
							player.setMovement(Player.SOUTHEAST);
						}
					}
					else if (c == 'd')
					{
						if (keys.contains('s'))
						{
							player.setMovement(Player.SOUTHEAST);
						}
						else if (keys.contains('w'))
						{
							player.setMovement(Player.NORTHEAST);
						}
					}

				}

			}
		}

		public void keyReleased(KeyEvent e)
		{
			if (begin == 2)
			{
				Character c = e.getKeyChar();
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{

					spacePressed = false;
					// tmrTitle.stop();
				}
				else
				{
					keys.remove(c);
				}

				if (keys.size() == 0)
				{
					player.setMovement(Player.STOP);
					tmrKey.stop();
				}
				else
				{
					if (c == 'w')
					{
						if (keys.contains('a'))
						{
							player.setMovement(Player.WEST);
						}
						else if (keys.contains('s'))
						{
							player.setMovement(Player.SOUTH);
						}
						else if (keys.contains('d'))
						{
							player.setMovement(Player.EAST);
						}
					}
					else if (c == 'a')
					{
						if (keys.contains('w'))
						{
							player.setMovement(Player.NORTH);
						}
						else if (keys.contains('s'))
						{
							player.setMovement(Player.SOUTH);
						}
						else if (keys.contains('d'))
						{
							player.setMovement(Player.EAST);
						}
					}
					else if (c == 's')
					{
						if (keys.contains('w'))
						{
							player.setMovement(Player.NORTH);
						}
						else if (keys.contains('a'))
						{
							player.setMovement(Player.WEST);
						}
						else if (keys.contains('d'))
						{
							player.setMovement(Player.EAST);
						}
					}
					else if (c == 'd')
					{
						if (keys.contains('w'))
						{
							player.setMovement(Player.NORTH);
						}
						else if (keys.contains('a'))
						{
							player.setMovement(Player.WEST);
						}
						else if (keys.contains('s'))
						{
							player.setMovement(Player.SOUTH);
						}
					}

				}
			}

		}

		public void keyTyped(KeyEvent e)
		{

		}
	}

	private class TimerListener implements ActionListener
	{

		Boolean side = true;// This boolean switch indicates from which side of
							// the ship the next laserBeam should be shot.

		public void actionPerformed(ActionEvent e)
		{

			if (e.getSource() == tmrTitle)
			{
				if (begin < 2)
				{
					if (time == 30)
					{
						yTitle -= 5;
						repaint();
						if (yTitle < (imgTitle.getIconHeight() + 40) * -1)
						{
							begin++;
							tmrTitle.stop();
							tmrTitle.setDelay(300);
							tmrTitle.start();
							tmrBullet.start();
							time = 0;
						}
					}
					else
					{
						time++;
						if (time == 30)
						{
							begin++;
						}
					}
				}
				else
				{
					if (spacePressed)
					{
						if (bulletCounter != 0 && !player.isDead())
						{// A laser beam will be shot only if the player is alive.
							if (side)
							{
								round[round.length - bulletCounter] = new LaserBeam(player.getX() + player.getWidth(),
										player.getCY() - 6);
								bulletCounter--;
								side = false;
							}
							else
							{
								round[round.length - bulletCounter] = new LaserBeam(player.getX() + player.getWidth(),
										player.getCY() + 6);
								bulletCounter--;
								side = true;
							}
						}

					}
					else if (player.isDead())
					{// Draws the explosion after the player's death.
						repaint();
					}

				}
			}
			else if (e.getSource() == tmrKey)
			{
				if (player.getX() >= imgBackground.getIconWidth() - player.getWidth())
				{// This huge set of 'if' statemets 'build borders' for the player.

					if (player.getY() <= 0)
					{
						if (player.getMovement() == Player.SOUTHEAST)
						{
							player.setMovement(Player.SOUTH);
						}
						else if (player.getMovement() == Player.NORTHEAST)
						{
							player.setMovement(Player.STOP);
						}
						else if (player.getMovement() == Player.NORTH)
						{
							player.setMovement(Player.STOP);
						}
					}
					else if (player.getY() >= imgBackground.getIconHeight() - player.getHeight())
					{

						if (player.getMovement() == Player.NORTHEAST)
						{
							player.setMovement(Player.NORTH);
						}
						else if (player.getMovement() == Player.SOUTHEAST)
						{
							player.setMovement(Player.STOP);
						}
						else if (player.getMovement() == Player.SOUTH)
						{
							player.setMovement(Player.STOP);
						}
					}
					else
					{

						if (player.getMovement() == Player.SOUTHEAST)
						{
							player.setMovement(Player.SOUTH);
						}
						else if (player.getMovement() == Player.NORTHEAST)
						{
							player.setMovement(Player.NORTH);
						}
					}

					if (player.getMovement() == Player.EAST)
					{
						player.setMovement(Player.STOP);
					}

				}
				else if (player.getX() <= 0)
				{

					if (player.getY() <= 0)
					{
						if (player.getMovement() == Player.NORTHWEST)
						{
							player.setMovement(Player.STOP);
						}
						else if (player.getMovement() == Player.SOUTHWEST)
						{
							player.setMovement(Player.SOUTH);
						}
						else if (player.getMovement() == Player.NORTH)
						{
							player.setMovement(Player.STOP);
						}
					}
					else if (player.getY() >= imgBackground.getIconHeight() - player.getHeight())
					{
						if (player.getMovement() == Player.SOUTHWEST)
						{
							player.setMovement(Player.STOP);
						}
						else if (player.getMovement() == Player.NORTHWEST)
						{
							player.setMovement(Player.NORTH);
						}
						else if (player.getMovement() == Player.SOUTH)
						{
							player.setMovement(Player.STOP);
						}
					}
					else
					{

						if (player.getMovement() == Player.NORTHWEST)
						{
							player.setMovement(Player.NORTH);
						}
						else if (player.getMovement() == Player.SOUTHWEST)
						{
							player.setMovement(Player.SOUTH);
						}
					}

					if (player.getMovement() == Player.WEST)
					{
						player.setMovement(Player.STOP);
					}
				}
				else if (player.getY() >= imgBackground.getIconHeight() - player.getHeight())
				{

					if (player.getX() >= imgBackground.getIconWidth() - player.getWidth())
					{
						if (player.getMovement() == Player.SOUTHEAST)
						{
							player.setMovement(Player.STOP);
						}
						else if (player.getMovement() == Player.SOUTHWEST)
						{
							player.setMovement(Player.WEST);
						}
					}
					else if (player.getX() <= 0)
					{
						if (player.getMovement() == Player.SOUTHWEST)
						{
							player.setMovement(Player.STOP);
						}
						else if (player.getMovement() == Player.SOUTHEAST)
						{
							player.setMovement(Player.EAST);
						}
					}
					else
					{
						if (player.getMovement() == Player.SOUTHEAST)
						{
							player.setMovement(Player.EAST);
						}
						else if (player.getMovement() == Player.SOUTHWEST)
						{
							player.setMovement(Player.WEST);
						}
					}

					if (player.getMovement() == Player.SOUTH)
					{
						player.setMovement(Player.STOP);
					}
				}
				else if (player.getY() <= 0)
				{

					if (player.getX() >= imgBackground.getIconWidth() - player.getWidth())
					{
						if (player.getMovement() == Player.NORTHWEST)
						{
							player.setDirection(Player.STOP);
						}
						else if (player.getMovement() == Player.NORTHWEST)
						{
							player.setDirection(Player.NORTHWEST);
						}
					}
					else if (player.getX() <= 0)
					{
						if (player.getMovement() == Player.NORTHWEST)
						{
							player.setDirection(Player.WEST);
						}
						else if (player.getMovement() == Player.NORTHWEST)
						{
							player.setDirection(Player.STOP);
						}
					}
					else
					{
						if (player.getMovement() == Player.NORTHEAST)
						{
							player.setMovement(Player.EAST);
						}
						else if (player.getMovement() == Player.NORTHWEST)
						{
							player.setMovement(Player.WEST);
						}
					}

					if (player.getMovement() == Player.NORTH)
					{
						player.setMovement(Player.STOP);
					}
				}
				player.move();

				repaint();

			}
			else if (e.getSource() == tmrBullet)
			{
				for (int i = 0; i < round.length - bulletCounter; i++)
				{// Moves every single laser beam
					round[i].move();
				}

				if (bulletCounter == 0)
				{
					reloading = true;
					time++;
					if (time == 300)
					{// This 'if' statement reloads the weapons only after a
						// time delay.
						bulletCounter = 20;
						reloading = false;
						time = 0;
					}
				}

				if (rnd.nextInt(100) > 98 && asteroidCounter != 0)
				{// A new asteroid will be made with a chance of 1%.

					if (asteroidCounter == 0)
					{
						asteroidCounter = 15;
					}

					field[field.length - asteroidCounter] = new Asteroid();

					asteroidCounter--;

				}

				for (int i = 0; i < field.length - asteroidCounter; i++)
				{
					if (field[i].isDestroyed() && field[i].getCounter() == 100)
					{// Keeps the explosion image on the field for a while
						// before erasing.
						field[i] = new Asteroid();
					}
					else if (!field[i].isDestroyed())
					{// If an asteroid is not destroyed, it will be moved along
						// its trajectory.
						field[i].move();
						if (field[i].getX() < 0 - field[i].getWidth())
						{// If an asteroid reaches the opposite side of the
							// field, it gets respawned.
							field[i] = new Asteroid();
						}
					}
					if (field[i].getRec().intersects(player.getRec()) && !field[i].isDestroyed())
					{// Checks for player-asteroid collisions
						if (life > 1)
						{
							player.killPlayer();
							tmrKey.stop();
							tmrBullet.stop();
						}
						else
						{// If the player spends all his lifes the game will
							// inform him about it and close itself
							JOptionPane.showMessageDialog(null, "Thanks for playing!\nYour score: " + score);
						}
					}

				}

				/**
				 * This nested 'for' loop checks for bullet-asteroid collisions.
				 */
				for (int i = 0; i < round.length - bulletCounter; i++)
				{
					for (int j = 0; j < field.length - asteroidCounter; j++)
					{
						if (field[j].getRec().intersects(round[i].getRec())
								&& (!field[j].isDestroyed() && !round[i].isDestroyed()))
						{
							round[i].destroyAsteroid();
							field[j].hit();

							if (field[j].isDestroyed())
							{
								score += field[j].getScore();
							}
						}
					}
				}

				repaint();
			}
		}
	}

	public static void main(String[] args)
	{
		new Game();
	}

}
