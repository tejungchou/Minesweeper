package game;


public class GameMineSweeper implements Runnable{

	Minesweeper gp=new Minesweeper();
	
	public static void main(String[] args) {
		new Thread(new GameMineSweeper()).start();
	}
	
	@Override
	public void run() {
		while(true) {
			gp.repaint();
		}
		
	}

}
