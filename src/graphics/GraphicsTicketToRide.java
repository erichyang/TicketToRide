package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import core.GameEvent;
import core.PlayerEvent;
import core.TicketToRide;

@SuppressWarnings("serial")
public class GraphicsTicketToRide extends JPanel implements MouseListener {
	private static JFrame window;
	private GraphicsBoard board;
	private TicketToRide game;

	public static void main(String[] args) throws FileNotFoundException {
		GraphicsTicketToRide test = new GraphicsTicketToRide();
		window = new JFrame("Ticket to Ride");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1920, 1080);
		window.setVisible(true);
		window.add(test);
		window.addMouseListener(test);
		window.setResizable(false);
		window.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));
		while (true)
			test.repaint();

	}

	private class playerAction extends AbstractAction {

		private int num;

		public playerAction(int num) {
			this.num = num;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println(board.getDraw() || board.ended());
			if (board.getDraw() || board.ended()) {
				// System.out.println("two");
				return;
			}
			// System.out.println("one");
			if (num == -1)
				game.onGameEvent(new GameEvent(3, this));
			game.onPlayerEvent(new PlayerEvent(num));
		}
	}

	playerAction PLAYER_DRAW_ONE = new playerAction(0);
	playerAction PLAYER_DRAW_TWO = new playerAction(1);
	playerAction PLAYER_DRAW_THREE = new playerAction(2);
	playerAction PLAYER_DRAW_FOUR = new playerAction(3);
	playerAction PLAYER_DRAW_FIVE = new playerAction(4);
	playerAction PLAYER_DRAW_DECK = new playerAction(5);
	playerAction END_GAME = new playerAction(-1);

	public GraphicsTicketToRide() throws FileNotFoundException {
		board = new GraphicsBoard();
		game = new TicketToRide();
		game.setView(board);
		initilizeKeyBindings();
	}

	public void initilizeKeyBindings() {
		// System.out.println("hello");
		getInputMap().put(KeyStroke.getKeyStroke('1'), "key 1");
		getActionMap().put("key 1", PLAYER_DRAW_ONE);
		getInputMap().put(KeyStroke.getKeyStroke('2'), "key 2");
		getActionMap().put("key 2", PLAYER_DRAW_TWO);
		getInputMap().put(KeyStroke.getKeyStroke('3'), "key 3");
		getActionMap().put("key 3", PLAYER_DRAW_THREE);
		getInputMap().put(KeyStroke.getKeyStroke('4'), "key 4");
		getActionMap().put("key 4", PLAYER_DRAW_FOUR);
		getInputMap().put(KeyStroke.getKeyStroke('5'), "key 5");
		getActionMap().put("key 5", PLAYER_DRAW_FIVE);
		getInputMap().put(KeyStroke.getKeyStroke(' '), "key SPACE");
		getActionMap().put("key SPACE", PLAYER_DRAW_DECK);
		getInputMap().put(KeyStroke.getKeyStroke("ctrl released ENTER"), "ctrl enter");
		getActionMap().put("ctrl enter", END_GAME);
	}

	@Override
	protected void paintComponent(Graphics fakeG) {
		super.paintComponent(fakeG);
		Graphics2D g = (Graphics2D) fakeG;

		board.draw(g);
		double x = MouseInfo.getPointerInfo().getLocation().getX() - window.getLocationOnScreen().x;
		double y = MouseInfo.getPointerInfo().getLocation().getY() - window.getLocationOnScreen().y;
		board.setLoc(new Point2D.Float((float) x, (float) y));
		board.graphSetRails();
		g.setStroke(new BasicStroke(4));
		g.setColor(new Color(129, 9, 255));
		g.draw(new Line2D.Double(x - 5, y, x + 5, y));
		g.draw(new Line2D.Double(x, y - 5, x, y + 5));
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//System.out.println(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		PlayerEvent action = board.contains(new Point2D.Float(arg0.getX(), arg0.getY()));
		if (action != null)
			game.onPlayerEvent(action);
	}

}
