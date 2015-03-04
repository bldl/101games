package inf101.games.gui;

import inf101.games.IGame;
import inf101.grid.IArea;
import inf101.grid.IPosition;
import inf101.grid.Position;
import inf101.grid.Rectangle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;

import java.util.List;

/**
 * @author Anna Maria Eilertsen
 * @author Alexandre Vivmond
 * @author Anya Helene Bagge
 *
 */
public class GameGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2030937455049555857L;
	/**
	 * Her ligger cellene
	 */
	private final ImagePanel mainPanel;
	/**
	 * Viser tid nederst
	 */
	private final JPanel statusPanel;
	/**
	 * 'Nytt games' knapp
	 */
	private final JPanel controlPanel;
	/**
	 * Knapper for å starte btnNew games
	 */
	private final JButton btnNew, btnStart, btnStep, btnSpeed;
	/**
	 * For å finne X og Y til knappen som ble trykket
	 */
	private final Map<JButton, IPosition> buttonMap = new HashMap<JButton, IPosition>();

	/**
	 * Referanse til spillet
	 */
	private final List<IGame> games;

	private IGame selectedGame;
	/**
	 * Vekker oss hvert halve sekund
	 */
	private javax.swing.Timer timer;
	private JComboBox<String> sizes;
	private final JComboBox<String> gameSelection;
	private JComboBox<String> gameMenu;
	private boolean paused = true;
	private static final String[] boardSizes = new String[] { "10x10", "12x12",
			"15x15", "20x15", "30x20" };
	private int currentSpeed = 0;
	private final int DELAY_NORMAL = 400, DELAY_FAST = 150, DELAY_FASTER = 50;
	private final int[] speeds = new int[] { DELAY_NORMAL, DELAY_FAST,
			DELAY_FASTER };

	public GameGUI(IGame spill) {
		this(Arrays.asList(spill));
	}

	/**
	 * Oppretter en ny games-GameGUI
	 * 
	 * @param games
	 *            Spillet som skal kontrolleres
	 */
	public GameGUI(List<IGame> spill) {
		super();
		setLayout(new BorderLayout());

		this.games = new ArrayList<IGame>(spill);
		Collections.sort(games, new GameComparator());
		this.selectedGame = spill.get(0);

		String[] gameNames = new String[games.size()];
		int i = 0;
		for (IGame g : games)
			gameNames[i++] = g.getName();
		gameSelection = new JComboBox<String>(gameNames);
		gameSelection.setSelectedItem(selectedGame.getName());

		JPanel dummyControlPanel = new JPanel();
		dummyControlPanel.setLayout(new BorderLayout());
		dummyControlPanel.setForeground(Style.FOREGROUND);
		dummyControlPanel.setBackground(Style.BACKGROUND);
		controlPanel = new JPanel();
		controlPanel.setForeground(Style.FOREGROUND);
		controlPanel.setBackground(Style.BACKGROUND);
		dummyControlPanel.add(controlPanel, BorderLayout.WEST);

		btnStart = new JButton();
		btnStart.setToolTipText("Play");
		btnStart.addActionListener(this);
		btnSpeed = new JButton();
		btnSpeed.addActionListener(this);
		btnStep = new JButton(ImageLoader.getImage("gui/images/step"));
		btnStep.setToolTipText("Step");
		btnStep.addActionListener(this);
		btnNew = new JButton(ImageLoader.getImage("gui/images/new"));
		btnNew.setToolTipText("New");
		btnNew.addActionListener(this);

		setPaused();
		updateSpeed();

		mainPanel = new ImagePanel();
		mainPanel.setForeground(Style.BACKGROUND);
		mainPanel.setBackground(Style.FOREGROUND);
		statusPanel = new JPanel();
		statusPanel.setForeground(Style.FOREGROUND);
		statusPanel.setBackground(Style.BACKGROUND);

		// ekstra panel for å få riktig bakgrunn på resten
		JPanel dummyPanel = new JPanel();
		dummyPanel.setForeground(Style.FOREGROUND);
		dummyPanel.setBackground(Style.BACKGROUND);

		add(dummyControlPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.WEST);
		add(statusPanel, BorderLayout.SOUTH);
		add(dummyPanel, BorderLayout.CENTER);
	}

	public void initialize() {
		initializeControl();
		initializeBoard();
		setVisible(true);
	}

	private void initializeControl() {
		controlPanel.removeAll();

		controlPanel.add(gameSelection);

		List<String> bSizes = selectedGame.getBoardSizes();
		if (bSizes == null)
			bSizes = Arrays.asList(boardSizes);
		// sørg for at vi har vår egen lokale kopi som vi kan endre på
		bSizes = new ArrayList<String>(bSizes);
		String size = selectedGame.getWidth() + "x" + selectedGame.getHeight();
		if (!bSizes.contains(size)) {
			bSizes.add(size);
		}
		Collections.sort(bSizes);
		if (sizes != null)
			sizes.removeActionListener(this);
		sizes = new JComboBox<String>(bSizes.toArray(new String[bSizes.size()]));
		sizes.setSelectedItem(size);
		sizes.addActionListener(this);

		controlPanel.add(sizes);

		controlPanel.add(btnNew);

		if (selectedGame.hasStartStopButtons()) {
			controlPanel.add(btnStart);
		}

		if (selectedGame.hasStepButton()) {
			controlPanel.add(btnStep);
		}
		
		controlPanel.add(btnSpeed);


		if (gameMenu != null) {
			gameMenu.removeActionListener(this);
			gameMenu = null;
		}
		List<String> choices = selectedGame.getMenuChoices();
		if (choices != null) {
			gameMenu = new JComboBox<String>(choices.toArray(new String[choices.size()]));
			gameMenu.addActionListener(this);
			controlPanel.add(gameMenu);
		}

	}

	private void initializeBoard() {
		int width = selectedGame.getWidth();
		int height = selectedGame.getHeight();

		mainPanel.removeAll();
		mainPanel.setLayout(new GridLayout(height + 1, width + 1));
		buttonMap.clear();

		for (int y = 0; y < height; y++) {
			mainPanel.add(new CoordLabel(height - y - 1));
			for (int x = 0; x < width; x++) {
				JPanel panel = new JPanel(new BorderLayout());
				JButton button = new JButton();
				button.addActionListener(this);
				button.setMargin(new Insets(0, 0, 0, 0));
				button.setContentAreaFilled(false);

				panel.add(button);
				panel.setOpaque(false);
				mainPanel.add(panel);
				buttonMap.put(button, new Position(x, height - y - 1));
			}
		}

		mainPanel.add(new CoordLabel(""));
		for (int x = 0; x < width; x++)
			mainPanel.add(new CoordLabel(x));
		oppdater();
		updateFrame();
	}

	private void updateFrame() {
		Container parent = getParent();
		while (parent != null) {
			if (parent instanceof JFrame) {
				JFrame frame = (JFrame) parent;
				frame.setTitle(selectedGame.getName());
				frame.pack();
				return;
			} else if (parent instanceof JApplet) {
				JApplet applet = (JApplet) parent;
				applet.setName(selectedGame.getName());
				applet.resize(getPreferredSize());
				applet.validate();
				return;
			}
			parent = parent.getParent();
		}

	}

	/**
	 * Går gjennom og oppdaterer grafikken til alle brikkene, og viser oppdatert
	 * informasjon i displayet
	 */
	private void oppdater() {
		for (Entry<JButton, IPosition> e : buttonMap.entrySet()) {
			e.getKey().setIcon(
					ImageLoader.getImage(selectedGame.getIconAt(e.getValue().getX(),
							e.getValue().getY())));
		}
	}

	private void setPlaying() {
		btnStart.setToolTipText("Pause");
		btnStart.setIcon(ImageLoader.getImage("gui/images/pause"));
		paused = false;

	}

	private void setPaused() {
		btnStart.setToolTipText("Play");
		btnStart.setIcon(ImageLoader.getImage("gui/images/play"));
		paused = true;
	}

	private void updateSpeed() {
		if (currentSpeed == 0) {
			btnSpeed.setIcon(ImageLoader.getImage("gui/images/normal"));
			btnSpeed.setToolTipText("Current speed: Normal");
		} else if (currentSpeed == 1) {
			btnSpeed.setIcon(ImageLoader.getImage("gui/images/fast"));
			btnSpeed.setToolTipText("Current speed: Fast");

		} else if (currentSpeed == 2) {
			btnSpeed.setIcon(ImageLoader.getImage("gui/images/faster"));
			btnSpeed.setToolTipText("Current speed: Faster");

		}
		if (timer != null) {
			timer.removeActionListener(this);
			timer.stop();
		}
		timer = new javax.swing.Timer(speeds[currentSpeed], this);
		if (!paused)
			timer.start();

	}

	/**
	 * Denne blir kalt av Java hver gang brukeren trykker på en knapp, eller
	 * hver gang timer-signalet avfyres.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNew) {
			selectedGame.newGame();
			oppdater();
		} else if (e.getSource() == btnStart) {
			if (paused) {
				setPlaying();
				selectedGame.timeStep();
				oppdater();
				timer.start();
			} else {
				setPaused();
				timer.stop();
			}
		} else if (e.getSource() == btnStep) {
			setPaused();
			timer.stop();
			selectedGame.timeStep();
			oppdater();
		} else if (e.getSource() == btnSpeed) {
			currentSpeed = (currentSpeed + 1) % speeds.length;

			System.out.println("new speed: " + speeds[currentSpeed]);
			updateSpeed();

		} else if (e.getSource() == timer) {
			selectedGame.timeStep();
			oppdater();
			timer.restart();
		} else if (e.getSource() == sizes) {
			String size = (String) sizes.getSelectedItem();
			IArea p = boardSize(size);
			selectedGame.setSize(p.getWidth(), p.getHeight());
			if (!selectedGame.canChangeSize())
				selectedGame.newGame();
			initializeBoard();
			oppdater();
		} else if (e.getSource() == gameMenu) {
			selectedGame.setMenuChoice((String) gameMenu.getSelectedItem());
			initializeBoard();
			oppdater();
		} else if (e.getSource() instanceof JButton) {
			IPosition point = buttonMap.get(e.getSource());
			selectedGame.select(point.getX(), point.getY());
			oppdater();
		}
	}

	private static IArea boardSize(String size) {
		String[] split = size.split("x");
		if (split.length != 2)
			throw new IllegalArgumentException("Should be on WxH: " + size);
		return new Rectangle(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
	}

	private static final class GameComparator implements Comparator<IGame>,
			Serializable {
		private static final long serialVersionUID = 6647481037039732094L;

		@Override
		public int compare(IGame arg0, IGame arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}
	}

}
