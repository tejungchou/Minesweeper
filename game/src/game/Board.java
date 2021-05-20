package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements Serializable{

	    private final int imageNums = 13;
	    private final int tileSize = 15;
		public JMenuBar menubar;
		public JMenuItem Save;
	    private final int tileCover = 10;
	    private final int tileMark = 10;
	    private final int emptyTile = 0;
	    private final int mineTile = 9;	
	    private final int drawCover = 10;
	    private final int drawMark = 11;
	    private final int drawWrongMark = 12;

	    private int alltiles;
	    private final int drawMine = 9;

	    private final int covered_mineTile = mineTile + tileCover;
	    private final int marked_mineTile = covered_mineTile + tileMark;
	    private final JLabel status,remainTime;
		public Timer timer;
	    private final int mineNum = 40;
	    private final int rowsNum = 16;
	    private final int columnsNum = 16;

		public int second=1001;
	    private final int BOARD_WIDTH = columnsNum * tileSize + 1;
	    private final int BOARD_HEIGHT = rowsNum * tileSize + 1;

	    public int[] field;
	    public boolean gaming;
	    public int minesLeft;
	    private Image[] img;

	
		
	    public Board(JLabel status,JLabel remainTime) {

	        this.status = status;
	        this.remainTime=remainTime;
	        initBoard();
	    }
	    private void initBoard() {
	    	timer=new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					second--;
					remainTime.setText("Remain Time : "+second);
					if (second==0) {
						timer.stop();
						gaming=false;
						newGame();
			            repaint();
					}
				}
				
			});

	        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
	        img = new Image[imageNums];

	        for (int i = 0; i < imageNums; i++) {
	        	
	          // var imagePath =""+i + ".png";
	           
	         //  System.out.println(imagePath);
	            var imagePath =i + ".png";

	        	img[i] = (new ImageIcon(imagePath)).getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
	        }

	        addMouseListener(new MinesAdapter());
	        newGame();
	    }
	    private void newGame() {

	        int tile;
	        timer.start();
	        var random = new Random();
	        gaming = true;
	        minesLeft = mineNum;

	        alltiles = rowsNum * columnsNum;
	        field = new int[alltiles];

	        for (int i = 0; i < alltiles; i++) {

	            field[i] = tileCover;
	        }

	        status.setText(Integer.toString(minesLeft));
	        second=1001;
	        int i = 0;

	        while (i < mineNum) {

	            int position = (int) (alltiles * random.nextDouble());

	            if ((position < alltiles)
	                    && (field[position] != covered_mineTile)) {

	                int current_col = position % columnsNum;
	                field[position] = covered_mineTile;
	                i++;

	                if (current_col > 0) {
	                    tile = position - 1 - columnsNum;
	                    if (tile >= 0) {
	                        if (field[tile] != covered_mineTile) {
	                            field[tile] += 1;
	                        }
	                    }
	                    tile = position - 1;
	                    if (tile >= 0) {
	                        if (field[tile] != covered_mineTile) {
	                            field[tile] += 1;
	                        }
	                    }

	                    tile = position + columnsNum - 1;
	                    if (tile < alltiles) {
	                        if (field[tile] != covered_mineTile) {
	                            field[tile] += 1;
	                        }
	                    }
	                }

	                tile = position - columnsNum;
	                if (tile >= 0) {
	                    if (field[tile] != covered_mineTile) {
	                        field[tile] += 1;
	                    }
	                }

	                tile = position + columnsNum;
	                if (tile < alltiles) {
	                    if (field[tile] != covered_mineTile) {
	                        field[tile] += 1;
	                    }
	                }

	                if (current_col < (columnsNum - 1)) {
	                    tile = position - columnsNum + 1;
	                    if (tile >= 0) {
	                        if (field[tile] != covered_mineTile) {
	                            field[tile] += 1;
	                        }
	                    }
	                    tile = position + columnsNum + 1;
	                    if (tile < alltiles) {
	                        if (field[tile] != covered_mineTile) {
	                            field[tile] += 1;
	                        }
	                    }
	                    tile = position + 1;
	                    if (tile < alltiles) {
	                        if (field[tile] != covered_mineTile) {
	                            field[tile] += 1;
	                        }
	                    }
	                }
	            }
	        }
	    }

	    private void find_emptyTiles(int j) {

	        int current_col = j % columnsNum;
	        int tile;

	        if (current_col > 0) {
	            tile = j - columnsNum - 1;
	            if (tile >= 0) {
	                if (field[tile] > mineTile) {
	                    field[tile] -= tileCover;
	                    if (field[tile] == emptyTile) {
	                        find_emptyTiles(tile);
	                    }
	                }
	            }

	            tile = j - 1;
	            if (tile >= 0) {
	                if (field[tile] > mineTile) {
	                    field[tile] -= tileCover;
	                    if (field[tile] == emptyTile) {
	                        find_emptyTiles(tile);
	                    }
	                }
	            }

	            tile = j + columnsNum - 1;
	            if (tile < alltiles) {
	                if (field[tile] > mineTile) {
	                    field[tile] -= tileCover;
	                    if (field[tile] == emptyTile) {
	                        find_emptyTiles(tile);
	                    }
	                }
	            }
	        }

	        tile = j - columnsNum;
	        if (tile >= 0) {
	            if (field[tile] > mineTile) {
	                field[tile] -= tileCover;
	                if (field[tile] == emptyTile) {
	                    find_emptyTiles(tile);
	                }
	            }
	        }

	        tile = j + columnsNum;
	        if (tile < alltiles) {
	            if (field[tile] > mineTile) {
	                field[tile] -= tileCover;
	                if (field[tile] == emptyTile) {
	                    find_emptyTiles(tile);
	                }
	            }
	        }

	        if (current_col < (columnsNum - 1)) {
	            tile = j - columnsNum + 1;
	            if (tile >= 0) {
	                if (field[tile] > mineTile) {
	                    field[tile] -= tileCover;
	                    if (field[tile] == emptyTile) {
	                        find_emptyTiles(tile);
	                    }
	                }
	            }

	            tile = j + columnsNum + 1;
	            if (tile < alltiles) {
	                if (field[tile] > mineTile) {
	                    field[tile] -= tileCover;
	                    if (field[tile] == emptyTile) {
	                        find_emptyTiles(tile);
	                    }
	                }
	            }

	            tile = j + 1;
	            if (tile < alltiles) {
	                if (field[tile] > mineTile) {
	                    field[tile] -= tileCover;
	                    if (field[tile] == emptyTile) {
	                        find_emptyTiles(tile);
	                    }
	                }
	            }
	        }

	    }

	    @Override
	    public void paintComponent(Graphics g) {

	        int uncover = 0;

	        for (int i = 0; i < rowsNum; i++) {

	            for (int j = 0; j < columnsNum; j++) {

	                int tile = field[(i * columnsNum) + j];

	                if (gaming && tile == mineTile) {

	                    gaming = false;
	                }

	                if (!gaming) {

	                    if (tile == covered_mineTile) {
	                        tile = drawMine;
	                    } else if (tile == marked_mineTile) {
	                        tile = drawMark;
	                    } else if (tile > covered_mineTile) {
	                        tile = drawWrongMark;
	                    } else if (tile > mineTile) {
	                        tile = drawCover;
	                    }

	                } else {

	                    if (tile > covered_mineTile) {
	                        tile = drawMark;
	                    } else if (tile > mineTile) {
	                        tile = drawCover;
	                        uncover++;
	                    }
	                }

	                g.drawImage(img[tile], (j * tileSize),
	                        (i * tileSize), this);
	            }
	        }

	        if (uncover == 0 && gaming) {

	            gaming = false;
	            status.setText("You won!");

	        } else if (!gaming) {
	            status.setText("Game Lost!");
	            timer.stop();
	        }
	    }

	    private class MinesAdapter extends MouseAdapter {

	        @Override
	        public void mousePressed(MouseEvent e) {

	            int x = e.getX();
	            int y = e.getY();

	            int cCol = x / tileSize;
	            int cRow = y / tileSize;

	            boolean doRepaint = false;

	            if (!gaming) {

	                newGame();
	                repaint();
	            }

	            if ((x < columnsNum * tileSize) && (y < rowsNum * tileSize)) {

	                if (e.getButton() == MouseEvent.BUTTON3) {

	                    if (field[(cRow * columnsNum) + cCol] > mineTile) {

	                        doRepaint = true;

	                        if (field[(cRow * columnsNum) + cCol] <= covered_mineTile) {

	                            if (minesLeft > 0) {
	                                field[(cRow * columnsNum) + cCol] += tileMark;
	                                minesLeft--;
	                                String msg = Integer.toString(minesLeft);
	                                status.setText(msg);
	                            } else {
	                                status.setText("No more marks");
	                            }
	                        } else {

	                            field[(cRow * columnsNum) + cCol] -= tileMark;
	                            minesLeft++;
	                            String msg = Integer.toString(minesLeft);
	                            status.setText(msg);
	                        }
	                    }

	                } else {

	                    if (field[(cRow * columnsNum) + cCol] > covered_mineTile) {

	                        return;
	                    }

	                    if ((field[(cRow * columnsNum) + cCol] > mineTile)
	                            && (field[(cRow * columnsNum) + cCol] < marked_mineTile)) {

	                        field[(cRow * columnsNum) + cCol] -= tileCover;
	                        doRepaint = true;

	                        if (field[(cRow * columnsNum) + cCol] == mineTile) {
	                            gaming = false;
	                        }

	                        if (field[(cRow * columnsNum) + cCol] == emptyTile) {
	                            find_emptyTiles((cRow * columnsNum) + cCol);
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
