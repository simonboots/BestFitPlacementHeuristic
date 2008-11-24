package scp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.xml.bind.JAXBException;

import scp.common.Gap;
import scp.common.IPlaceableObject;
import scp.common.PlacedShape;
import scp.common.Shape;
import scp.common.ShapeSortBySizeComparator;
import scp.common.actionchain.ActionExecutor;
import scp.common.actionchain.ActionGenerator;
import scp.common.actionchain.IAction;
import scp.common.xml.XMLBridge;

/**
 * 
 * @author Benjamin Clauss
 */
@SuppressWarnings("serial")
public class StockCutterGUI extends JFrame implements ActionListener, ChangeListener {

	/**
	 * MenuBar-Items
	 */
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu extrasMenu;
	private JMenu aboutMenu;
	private JMenuItem openItem;
	private JMenuItem closeItem;
	private JMenuItem extrasItem;
	private JMenuItem aboutItem;
	/**
	 * (Scroll-)Panels
	 */
	private JPanel leftPanel;
	private JPanel leftShapeList;
	private JScrollPane leftScrollPanel;
	private JPanel rightShapeList;
	private JScrollPane rightScrollPanel;
	/**
	 * Lists for ScrollPanels
	 */
	private ArrayList<ColoredShape> leftList = new ArrayList<ColoredShape>();
	private ArrayList<IPlaceableObject> rightList = new ArrayList<IPlaceableObject>();
	/**
	 * doQueue, ShapeMagazin, ShapePlacer, ActionExecutor
	 */
	private List<IAction> doQueue = new ArrayList<IAction>();
	private ShapeMagazine magazine = new ShapeMagazine(this);
	private ShapePlacer placer = new ShapePlacer(this);
	private ActionExecutor executor = new ActionExecutor(doQueue, magazine, placer);
	/**
	 * Navigation-Items
	 */
	private JPanel navigationPanel;
	private JButton next;
	private JButton playStop;
	private JButton previous;
	private JButton skipToEnd;
	private JButton skipToStart;
	private JSlider slider;
	private JLabel frequency;
	private JLabel msec;
	/**
	 * Timer
	 */
	private Timer timer;
	/**
	 * Logger
	 */
	private JTextArea logPanel;
	private JScrollPane logScrollPanel;

	public StockCutterGUI() {
		super("The Stock-Cutting-Problem");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocation(250, 100);
		setResizable(false);

		menuBar = new JMenuBar();

		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open", 'f');
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openItem.addActionListener(this);
		closeItem = new JMenuItem("Close", 'e');
		closeItem.addActionListener(this);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(closeItem);

		extrasMenu = new JMenu("Extras");
		extrasItem = new JMenuItem("...");
		extrasMenu.add(extrasItem);

		aboutMenu = new JMenu("?");
		aboutItem = new JMenuItem("About", 'b');
		aboutItem.addActionListener(this);
		aboutMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(extrasMenu);
		menuBar.add(aboutMenu);

		setJMenuBar(menuBar);

		leftShapeList = new LeftDrawingPane(leftList);
		leftShapeList.setBackground(Color.white);
		leftScrollPanel = new JScrollPane(leftShapeList);
		leftScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		leftScrollPanel.setPreferredSize(new Dimension(268, 300));

		navigationPanel = new JPanel();
		navigationPanel.setPreferredSize(new Dimension(268, 150));

		next = new JButton(">");
		next.setToolTipText("next step");
		next.setPreferredSize(new Dimension(41, 25));
		next.addActionListener(this);

		playStop = new JButton("play");
		playStop.setPreferredSize(new Dimension(59, 25));
		playStop.addActionListener(this);

		previous = new JButton("<");
		previous.setToolTipText("previous step");
		previous.setPreferredSize(new Dimension(41, 25));
		previous.addActionListener(this);

		skipToStart = new JButton("<<");
		skipToStart.setToolTipText("goto start");
		skipToStart.setPreferredSize(new Dimension(48, 25));
		skipToStart.addActionListener(this);

		skipToEnd = new JButton(">>");
		skipToEnd.setToolTipText("goto end");
		skipToEnd.setPreferredSize(new Dimension(48, 25));
		skipToEnd.addActionListener(this);

		slider = new JSlider(0, 1000, 500);
		slider.setPreferredSize(new Dimension(250, 40));
		slider.setMajorTickSpacing(250);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);

		frequency = new JLabel("frequency:");
		msec = new JLabel(slider.getValue() + "ms");

		navigationPanel.add(skipToStart);
		navigationPanel.add(previous);
		navigationPanel.add(playStop);
		navigationPanel.add(next);
		navigationPanel.add(skipToEnd);

		navigationPanel.add(slider);
		navigationPanel.add(frequency);
		navigationPanel.add(msec);

		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(leftScrollPanel, BorderLayout.NORTH);
		leftPanel.add(navigationPanel, BorderLayout.CENTER);

		rightShapeList = new RightDrawingPane(rightList);
		rightShapeList.setBackground(Color.white);
		rightScrollPanel = new JScrollPane(rightShapeList);
		rightScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rightScrollPanel.setPreferredSize(new Dimension(418, 450));

		logPanel = new JTextArea();
		logPanel.setEditable(false);
		logPanel.setRows(5);
		logScrollPanel = new JScrollPane(logPanel);
		logScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		timer = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				executor.executeNextAction();
			}
		});

		setLayout(new BorderLayout());

		add(leftPanel, BorderLayout.WEST);
		add(rightScrollPanel, BorderLayout.CENTER);
		add(logScrollPanel, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openItem) {
			final JFileChooser fc = new JFileChooser("./src/");

			fc.setFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml") || f.getName().toLowerCase().endsWith(".scp");
				}

				@Override
				public String getDescription() {
					return "SCP Dateien (*.scp, *.xml)";
				}
			});

			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File xmlFile = fc.getSelectedFile();
				System.out.println("Opening: " + xmlFile.getAbsolutePath());

				XMLBridge bridge;
				try {
					bridge = new XMLBridge();
					bridge.loadFile(xmlFile);

					ActionGenerator generator = new ActionGenerator(bridge.getShapeList(), bridge.getSortedShapeList(), bridge.getPlacementsList(), bridge.getOptimizeList());

					doQueue.addAll(generator.getDoQueue());

					// first action (load list)
					executor.executeNextAction();

				} catch (JAXBException ex) {
					ex.printStackTrace();
				}
			}
		} else if (e.getSource() == closeItem) {
			System.exit(0);
		} else if (e.getSource() == aboutItem) {
			JOptionPane.showMessageDialog(null, "(c)2008 by Simon Stiefel & Benjamin Clauss", "�ber", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == next) {
			executor.executeNextAction();
		} else if (e.getSource() == playStop) {
			if (playStop.getText().equals("play")) {
				timer.start();
				playStop.setText("stop");
				previous.setEnabled(false);
				next.setEnabled(false);
				skipToEnd.setEnabled(false);
				skipToStart.setEnabled(false);
			} else if (playStop.getText().equals("stop")) {
				timer.stop();
				playStop.setText("play");
				previous.setEnabled(true);
				next.setEnabled(true);
				skipToEnd.setEnabled(true);
				skipToStart.setEnabled(true);
			}
		} else if (e.getSource() == previous) {
			executor.executePreviousAction();
		} else if (e.getSource() == skipToEnd) {
			while (executor.hasNextAction()) {
				executor.executeNextAction();
			}
		} else if (e.getSource() == skipToStart) {
			while (executor.hasPreviousAction()) {
				executor.executePreviousAction();
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == slider) {
			timer.setDelay(slider.getValue());
			msec.setText(slider.getValue() + "ms");
		}
	}

	/**
	 * loads a list of shapes into the magazine
	 * 
	 * @param shapelist
	 */
	public void loadMagazine(List<Shape> shapelist) {
		leftList.clear();
		for (Shape shape : shapelist) {
			ColoredShape cs = new ColoredShape(shape);
			cs.setColor(Color.lightGray);
			leftList.add(cs);
		}
		leftShapeList.revalidate();
		leftShapeList.repaint();
	}

	/**
	 * highlights a shape in the magazine
	 * 
	 * @param s
	 */
	public void highlightMagazineShape(Shape s) {
		for (ColoredShape shape : leftList) {
			if (shape.getId() == s.getId()) {
				shape.setColor(Color.red);
			}
		}
		leftShapeList.repaint();
	}

	/**
	 * unhighlights all shapes in the magazine
	 */
	public void unhighlightAllMagazineShapes() {
		for (ColoredShape shape : leftList) {
			if (shape.getColor().equals(Color.red)) {
				shape.setColor(Color.lightGray);
			}
		}
		leftShapeList.repaint();
	}

	/**
	 * inserts a shape into the magazine
	 * 
	 * @param s
	 */
	public void insertShapeIntoMagazine(Shape s) {
		ColoredShape cs = new ColoredShape(s);
		leftList.add(cs);

		Collections.sort(leftList, new ShapeSortBySizeComparator());

		leftShapeList.revalidate();
		leftShapeList.repaint();
	}

	/**
	 * removes a shape from the magazine
	 * 
	 * @param s
	 */
	public void removeShapeFromMagazine(Shape s) {
		ColoredShape shapeToRemove = null;
		for (ColoredShape shape : leftList) {
			if (shape.getId() == s.getId()) {
				shapeToRemove = shape;
			}
		}
		leftList.remove(shapeToRemove);

		leftShapeList.revalidate();
		leftShapeList.repaint();
	}

	/**
	 * places a shape on the stockroll
	 * 
	 * @param s
	 */
	public void placeShape(PlacedShape s) {
		ColoredPlacedShape cps = new ColoredPlacedShape(s);
		cps.setColor(Color.lightGray);

		rightList.add(cps);
		rightShapeList.repaint();
	}

	/**
	 * removes a shape from the stockroll
	 * 
	 * @param s
	 */
	public void removePlacedShape(Shape s) {
		ColoredPlacedShape shapeToRemove = null;
		for (IPlaceableObject obj : rightList) {
			if ((obj instanceof ColoredPlacedShape) && (obj.getId() == s.getId())) {
				shapeToRemove = (ColoredPlacedShape) obj;
			}
		}
		rightList.remove(shapeToRemove);
		rightShapeList.revalidate();
		rightShapeList.repaint();
	}

	/**
	 * highlights a shape on the stockroll
	 * 
	 * @param s
	 */
	public void highlightPlacedShape(Shape s) {
		for (IPlaceableObject obj : rightList) {
			if ((obj instanceof ColoredPlacedShape) && (obj.getId() == s.getId())) {
				((ColoredPlacedShape) obj).setColor(Color.red);
			}
		}
	}

	/**
	 * unhighlights all shapes on the stockroll
	 */
	public void unhighlightAllPlacedShapes() {
		for (IPlaceableObject obj : rightList) {
			if ((obj instanceof ColoredPlacedShape) && ((ColoredPlacedShape) obj).getColor().equals(Color.red)) {
				((ColoredPlacedShape) obj).setColor(Color.lightGray);
			}
		}
	}

	/**
	 * highlights a gap on the stockroll
	 * 
	 * @param g
	 */
	public void highlightGap(Gap g) {
		rightList.add(g);
		rightShapeList.repaint();
	}

	/**
	 * removes the highlighted gap from the stockroll
	 */
	public void removeGap() {
		Gap gapToRemove = null;
		for (IPlaceableObject obj : rightList) {
			if (obj instanceof Gap) {
				gapToRemove = (Gap) obj;
			}
		}
		rightList.remove(gapToRemove);
		rightShapeList.repaint();
	}

	/**
	 * appends a string to the logger
	 * 
	 * @param s
	 */
	public void printToLogger(String s) {
		logPanel.append(s + "\n");
	}

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new StockCutterGUI();
			}
		});
	}
}