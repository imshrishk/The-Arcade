package FlappyBird;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import Main.Main;

public class FlappyBird extends JPanel implements ActionListener, MouseListener, KeyListener {

	private static final int WIDTH = 800, HEIGHT = 800;
	private static final int BIRD_WIDTH = 20, BIRD_HEIGHT = 20;
	private static final int PIPE_WIDTH = 100, PIPE_MIN_HEIGHT = 50;
	private static final int PIPE_SPACE = 300;
	private static final int GROUND_HEIGHT = 120;
	private static final int PIPE_SPEED = 10;
	private static final int TICK_INTERVAL = 20;
	private static final int GRAVITY = 2;
	private static final int JUMP_MOTION = 10;

	private Rectangle bird;
	private ArrayList<Rectangle> pipes;
	private int ticks, yMotion, score;
	private boolean gameOver, started;
	private Random random;

	public FlappyBird() {
		Timer timer = new Timer(TICK_INTERVAL, this);
		random = new Random();

		bird = new Rectangle(WIDTH / 2 - BIRD_WIDTH / 2, HEIGHT / 2 - BIRD_HEIGHT / 2, BIRD_WIDTH, BIRD_HEIGHT);
		pipes = new ArrayList<>();

		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		for (int i = 0; i < 4; i++) {
			addPipe(true);
		}

		timer.start();
	}

	private void addPipe(boolean start) {
		int height = PIPE_MIN_HEIGHT + random.nextInt(300);
		int x = start ? WIDTH + pipes.size() * 300 : pipes.get(pipes.size() - 1).x + 600;

		pipes.add(new Rectangle(x, HEIGHT - height - GROUND_HEIGHT, PIPE_WIDTH, height));
		pipes.add(new Rectangle(x, 0, PIPE_WIDTH, HEIGHT - height - PIPE_SPACE));
	}

	private void paintPipe(Graphics g, Rectangle pipe) {
		g.setColor(Color.green.darker());
		g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
	}

	private void jump() {
		if (gameOver) {
			bird = new Rectangle(WIDTH / 2 - BIRD_WIDTH / 2, HEIGHT / 2 - BIRD_HEIGHT / 2, BIRD_WIDTH, BIRD_HEIGHT);
			pipes.clear();
			yMotion = 0;
			score = 0;

			for (int i = 0; i < 4; i++) {
				addPipe(true);
			}

			gameOver = false;
		}

		if (!started) {
			started = true;
		} else if (!gameOver) {
			if (yMotion > 0) {
				yMotion = 0;
			}
			yMotion -= JUMP_MOTION;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = PIPE_SPEED;
		ticks++;

		if (started) {
			for (int i = 0; i < pipes.size(); i++) {
				Rectangle pipe = pipes.get(i);
				pipe.x -= speed;
			}

			if (ticks % 2 == 0 && yMotion < 15) {
				yMotion += GRAVITY;
			}

			for (int i = 0; i < pipes.size(); i++) {
				Rectangle pipe = pipes.get(i);
				if (pipe.x + pipe.width < 0) {
					pipes.remove(pipe);
					if (pipe.y == 0) {
						addPipe(false);
					}
				}
			}

			bird.y += yMotion;

			for (Rectangle pipe : pipes) {
				if (pipe.y == 0 && bird.x + bird.width / 2 > pipe.x + pipe.width / 2 - 10 && bird.x + bird.width / 2 < pipe.x + pipe.width / 2 + 10) {
					score++;
				}

				if (pipe.intersects(bird)) {
					gameOver = true;
					if (bird.x <= pipe.x) {
						bird.x = pipe.x - bird.width;
					} else {
						if (pipe.y != 0) {
							bird.y = pipe.y - bird.height;
						} else if (bird.y < pipe.height) {
							bird.y = pipe.height;
						}
					}
				}
			}

			if (bird.y > HEIGHT - GROUND_HEIGHT || bird.y < 0) {
				gameOver = true;
			}

			if (bird.y + yMotion >= HEIGHT - GROUND_HEIGHT) {
				bird.y = HEIGHT - GROUND_HEIGHT - bird.height;
				gameOver = true;
			}
		}

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // Call JPanel's paintComponent method to ensure the panel is correctly rendered

		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT - GROUND_HEIGHT, WIDTH, GROUND_HEIGHT);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - GROUND_HEIGHT, WIDTH, 20);

		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);

		for (Rectangle pipe : pipes) {
			paintPipe(g, pipe);
		}

		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 100));

		if (!started) {
			g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
		}

		if (gameOver) {
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
		}

		if (!gameOver && started) {
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}

		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			Main.flappyBird.setVisible(false);
			Main.obJFrame.dispose();
			Main.showScreen();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
}