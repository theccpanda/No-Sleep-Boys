package Minisweep;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Field extends JPanel {
	private final int NUM_IMS = 6;
	private final int SCALE = 15;
	
	private int mines = 10;
	private int rows = 10;
	private int columns = 10;
	private int width = rows * 15;
	private int height = columns * 15;

	private int mystery = 5;
	private int cya = 4;
	private int phew = 0;
	private int surprise = cya + mystery;
	
	public int box;
	private int[] field;
	private boolean active;
	private Image[] img;
	

	public Field() {
		load();
	}

	private void load() {

		setPreferredSize(new Dimension(width, height));
		img = new Image[NUM_IMS];
		for (int i = 0; i < NUM_IMS; i++) {
			String path = "src/resources/" + i + ".png";
			img[i] = (new ImageIcon(path)).getImage();
		}
		addMouseListener(new MouseInput());
		start();
	}

	private void start() {
		var random = new Random();
		active = true;
		field = new int[rows * columns];
		for (int i = 0; i < rows * columns; i++) {
			field[i] = mystery;
		}
		int i = 0;
		while (i < mines) {
			int position = (int) (rows * columns * random.nextDouble());
			if ((position < rows * columns) && (field[position] != surprise)) {
				int current_col = position % columns;
				field[position] = surprise;
				i++;
				if (current_col > 0) {
					box = position - 1 - columns;
					if (box >= 0 && field[box] != surprise)
						field[box] += 1;
					box = position - 1;
					if (box >= 0 && field[box] != surprise)
						field[box] += 1;
					box = position + columns - 1;
					if (box < rows * columns && field[box] != surprise)
						field[box] += 1;
				}
				box = position - columns;
				if (box >= 0 && field[box] != surprise)
					field[box] += 1;
				box = position + columns;
				if (box < rows * columns && field[box] != surprise)
					field[box] += 1;
				if (current_col < (columns - 1)) {
					box = position - columns + 1;
					if (box >= 0 && field[box] != surprise)
						field[box] += 1;
					box = position + columns + 1;
					if (box < rows * columns && field[box] != surprise)
						field[box] += 1;
					box = position + 1;
					if (box < rows * columns && field[box] != surprise)
						field[box] += 1;
				}
			}
		}
	}

	private void freELO(int j) {
		int current_col = j % columns;
		if (current_col > 0) {
			box = j - columns - 1;
			if (box >= 0 && field[box] > cya) {
				field[box] -= mystery;
				if (field[box] == phew)
					freELO(box);
			}
			box = j - 1;
			if (box >= 0 && field[box] > cya) {
				field[box] -= mystery;
				if (field[box] == phew)
					freELO(box);
			}
			box = j + columns - 1;
			if (box < rows * columns && field[box] > cya) {
				field[box] -= mystery;
				if (field[box] == phew)
					freELO(box);
			}
		}
		box = j - columns;
		if (box >= 0 && field[box] > cya) {
			field[box] -= mystery;
			if (field[box] == phew)
				freELO(box);
		}
		box = j + columns;
		if (box < rows * columns && field[box] > cya) {
			field[box] -= mystery;
			if (field[box] == phew)
				freELO(box);
		}
		if (current_col < (columns - 1)) {
			box = j - columns + 1;
			if (box >= 0 && field[box] > cya) {
				field[box] -= mystery;
				if (field[box] == phew)
					freELO(box);
			}
			box = j + columns + 1;
			if (box < rows * columns && field[box] > cya) {
				field[box] -= mystery;
				if (field[box] == phew)
					freELO(box);
			}
			box = j + 1;
			if (box < rows * columns && field[box] > cya) {
				field[box] -= mystery;
				if (field[box] == phew)
					freELO(box);
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		int uncover = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int box = field[(i * columns) + j];
				if (active && box == cya) {
					active = false;
				}
				if (!active) {
					if (box == surprise) {
						box = cya;
					} else if (box > cya) {
						box = mystery;
					}
				} else {
					if (box > cya) {
						box = mystery;
						uncover++;
					}
				}
				g.drawImage(img[box], (j * SCALE), (i * SCALE), this);
			}
		}
		if (uncover == 0 && active) {
			active = false;
		}
	}

	private class MouseInput extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int cCol = x / SCALE;
			int cRow = y / SCALE;
			boolean doRepaint = false;
			if (!active) {
				start();
				repaint();
			}
			if ((x < columns * SCALE) && (y < rows * SCALE)) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (field[(cRow * columns) + cCol] > cya) {
						doRepaint = true;
					}
				} else {
					if (field[(cRow * columns) + cCol] > cya) {
						field[(cRow * columns) + cCol] -= mystery;
						doRepaint = true;
						if (field[(cRow * columns) + cCol] == cya) {
							active = false;
						}
						if (field[(cRow * columns) + cCol] == phew) {
							freELO((cRow * columns) + cCol);
						}
					}
				}

				if (doRepaint) {
					repaint();
				}
			}
		}
	}
}