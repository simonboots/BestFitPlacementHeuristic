package scp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;
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
 * @author Benjamin Clauss
 */
@SuppressWarnings("serial")
public class StockCutterGUI extends JFrame implements ActionListener, ChangeListener {

	/**
	 * Icons/Buttons
	 */
	URLClassLoader cl = (URLClassLoader)this.getClass().getClassLoader();	
	
	private ImageIcon stop = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("stop.gif")));
	private ImageIcon play = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("play.gif")));
	private ImageIcon previousStep = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("previous.gif")));
	private ImageIcon nextStep = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("next.gif")));
	private ImageIcon skipStart = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("skipStart.gif")));
	private ImageIcon skipEnd = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("skipEnd.gif")));
	private ImageIcon save = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("save.gif")));
	private ImageIcon scrollUp = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("scrollUp.gif")));
	private ImageIcon clear = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("clear.gif")));

	/**
	 * MenuBar-Items
	 */
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu aboutMenu;
	private JMenuItem openItem;
	private JMenuItem closeItem;
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
	private List<IAction> doQueue = null;
	private ShapeMagazine magazine = null;
	private ShapePlacer placer = null;
	private ActionExecutor executor = null;
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
	private JPanel loggerPanel;
	private JPanel logButtons;
	private JButton clearLog;
	private JButton scrollUpLog;
	private JButton saveLog;
	private JTextArea logPanel;
	private JScrollPane logScrollPanel;

	/**
	 * standard constructor
	 */
	public StockCutterGUI() {
		super("The Stock-Cutting-Problem");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocation(100, 50);
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

		aboutMenu = new JMenu("?");
		aboutItem = new JMenuItem("About", 'b');
		aboutItem.addActionListener(this);
		aboutMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);

		setJMenuBar(menuBar);

		leftShapeList = new LeftDrawingPane(leftList);
		leftShapeList.setBackground(Color.white);
		leftScrollPanel = new JScrollPane(leftShapeList);
		leftScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		leftScrollPanel.setPreferredSize(new Dimension(268, 300));

		navigationPanel = new JPanel();
		navigationPanel.setPreferredSize(new Dimension(268, 150));

		next = new JButton(nextStep);
		next.setToolTipText("next step");
		next.setRolloverIcon(nextStep);
		next.setPreferredSize(new Dimension(30, 30));
		next.addActionListener(this);

		playStop = new JButton(play);
		playStop.setActionCommand("play");
		playStop.setToolTipText("play");
		playStop.setRolloverIcon(play);
		playStop.setPreferredSize(new Dimension(30, 30));
		playStop.addActionListener(this);

		previous = new JButton(previousStep);
		previous.setToolTipText("previous step");
		previous.setRolloverIcon(previousStep);
		previous.setPreferredSize(new Dimension(30, 30));
		previous.addActionListener(this);

		skipToStart = new JButton(skipStart);
		skipToStart.setToolTipText("skip to start");
		skipToStart.setRolloverIcon(skipStart);
		skipToStart.setPreferredSize(new Dimension(30, 30));
		skipToStart.addActionListener(this);

		skipToEnd = new JButton(skipEnd);
		skipToEnd.setToolTipText("skip to end");
		skipToEnd.setRolloverIcon(skipEnd);
		skipToEnd.setPreferredSize(new Dimension(30, 30));
		skipToEnd.addActionListener(this);

		slider = new JSlider(0, 1000, 500);
		slider.setPreferredSize(new Dimension(186, 40));
		slider.setMajorTickSpacing(250);
		slider.setSnapToTicks(false);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);

		frequency = new JLabel("placement period:");
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

		logButtons = new JPanel(new GridLayout(3, 0));

		saveLog = new JButton(save);
		saveLog.setToolTipText("save log");
		saveLog.setRolloverIcon(save);
		saveLog.setPreferredSize(new Dimension(30, 30));
		saveLog.addActionListener(this);

		scrollUpLog = new JButton(scrollUp);
		scrollUpLog.setToolTipText("go to first line");
		scrollUpLog.setRolloverIcon(scrollUp);
		scrollUpLog.setPreferredSize(new Dimension(30, 30));
		scrollUpLog.addActionListener(this);

		clearLog = new JButton(clear);
		clearLog.setToolTipText("clear log");
		clearLog.setRolloverIcon(clear);
		clearLog.setPreferredSize(new Dimension(30, 30));
		clearLog.addActionListener(this);

		logButtons.add(saveLog);
		logButtons.add(scrollUpLog);
		logButtons.add(clearLog);

		loggerPanel = new JPanel(new BorderLayout());
		loggerPanel.add(logScrollPanel, BorderLayout.CENTER);
		loggerPanel.add(logButtons, BorderLayout.WEST);

		timer = new Timer(slider.getValue(), this);

		add(leftPanel, BorderLayout.WEST);
		add(rightScrollPanel, BorderLayout.CENTER);
		add(loggerPanel, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			if (executor.hasNextAction()) {
				executor.executeNextAction();
			} else {
				playStop.doClick();
			}
		} else if (e.getSource() == openItem) {
			final JFileChooser fc = new JFileChooser("./src/");

			fc.setFileFilter(new ExtensionFilter());

			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// clear all lists
				resetAll();

				File xmlFile = fc.getSelectedFile();
				System.out.println("Opening: " + xmlFile.getAbsolutePath());

				XMLBridge bridge;
				try {
					bridge = new XMLBridge();
					bridge.loadFile(xmlFile);

					ActionGenerator generator = new ActionGenerator(bridge.getShapeList(), bridge.getSortedShapeList(), bridge.getPlacementsList(), bridge.getOptimizeList());

					magazine = new ShapeMagazine(this);
					placer = new ShapePlacer(this);

					doQueue = new ArrayList<IAction>();
					doQueue.addAll(generator.getDoQueue());

					executor = new ActionExecutor(doQueue, magazine, placer);

					// first action (load list)
					executor.executeNextAction();

				} catch (JAXBException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
				}
			}
		} else if (e.getSource() == closeItem) {
			System.exit(0);
		} else if (e.getSource() == aboutItem) {
			JOptionPane.showMessageDialog(null, "(c)2008 by Simon Stiefel & Benjamin Clauss", "About", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == next) {
			executor.executeNextAction();
		} else if (e.getSource() == playStop) {
			if (playStop.getActionCommand().equals("play")) {
				timer.start();
				playStop.setToolTipText("stop");
				playStop.setIcon(stop);
				playStop.setRolloverIcon(stop);
				playStop.setActionCommand("stop");
				previous.setEnabled(false);
				next.setEnabled(false);
				skipToEnd.setEnabled(false);
				skipToStart.setEnabled(false);
			} else if (playStop.getActionCommand().equals("stop")) {
				timer.stop();
				playStop.setToolTipText("play");
				playStop.setIcon(play);
				playStop.setRolloverIcon(play);
				playStop.setActionCommand("play");
				previous.setEnabled(true);
				next.setEnabled(true);
				skipToEnd.setEnabled(true);
				skipToStart.setEnabled(true);
			}
		} else if (e.getSource() == previous) {
			unhighlightAllGaps();
			executor.executePreviousAction();
		} else if (e.getSource() == skipToEnd) {
			while (executor.hasNextAction()) {
				executor.executeNextAction();
			}
		} else if (e.getSource() == skipToStart) {
			while (executor.hasPreviousAction()) {
				executor.executePreviousAction();
			}
		} else if (e.getSource() == saveLog) {
			final JFileChooser fc = new JFileChooser();
			fc.setSelectedFile(new File("log.txt"));

			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				FileWriter writer = null;
				try {
					writer = new FileWriter(file);
					logPanel.write(writer);
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		} else if (e.getSource() == scrollUpLog) {
			logPanel.scrollRectToVisible(new Rectangle(new Point(0, 0)));
		} else if (e.getSource() == clearLog) {
			logPanel.setText("");
		}
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == slider) {
			timer.setDelay(slider.getValue());
			msec.setText(slider.getValue() + "ms");
		}
	}

	/**
	 * loads a list of shapes into the magazine
	 * @param shapelist list of shapes
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
	 * @param s shape to be highlighted
	 */
	public void highlightMagazineShape(Shape s) {
		for (ColoredShape shape : leftList) {
			if (shape.getId() == s.getId()) {
				shape.setColor(Color.red);
			}
		}
		leftShapeList.revalidate();
		leftShapeList.repaint();
		
		((LeftDrawingPane) leftShapeList).setScrollToRed(true);
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
	 * @param s shape to be inserted
	 */
	public void insertShapeIntoMagazine(Shape s) {
		Shape clonedShape = (Shape) s.clone();
		clonedShape.makeWiderThanHigher();
		ColoredShape cs = new ColoredShape(clonedShape);
		leftList.add(cs);

		Collections.sort(leftList, new ShapeSortBySizeComparator());

		leftShapeList.revalidate();
		leftShapeList.repaint();
	}

	/**
	 * removes a shape from the magazine
	 * @param s shape to be removed
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
	 * @param s shape to be placed
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
	 * @param s shape to be removed
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
	 * @param s shape to be highlighted
	 */
	public void highlightPlacedShape(Shape s) {
		for (IPlaceableObject obj : rightList) {
			if ((obj instanceof ColoredPlacedShape) && (obj.getId() == s.getId())) {
				((ColoredPlacedShape) obj).setColor(Color.red);
			}
		}
		rightShapeList.repaint();
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
		rightShapeList.repaint();
	}

	/**
	 * highlights a gap on the stockroll
	 * @param g gap to be highlighted
	 */
	public void highlightGap(Gap g) {
		rightList.add(g);
		rightShapeList.repaint();
	}

	/**
	 * unhighlights all gaps on the stockroll
	 */
	public void unhighlightAllGaps() {
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
	 * @param s string to be appended
	 */
	public void printToLogger(String s) {
		logPanel.append(s + "\n");
	}
	
	/**
	 * highlights the skyline
	 */
	public void highlightSkyline() {
		int maxHeight = 0;
		
		for(IPlaceableObject obj : rightList) {
			if(obj instanceof ColoredPlacedShape) {
				if((obj.getY() + obj.getHeight()) > maxHeight) {
					maxHeight = (obj.getY() + obj.getHeight());
				}
			}
		}
		
		printToLogger("highlighted\tskyline\tnoID\t@ 0," + maxHeight);
		
		rightList.add(new Skyline(maxHeight));
		rightShapeList.repaint();
	}
	
	/**
	 * unhighlights the skyline
	 */
	public void unhighlightSkyline() {
		Skyline skyToRemove = null;
		for (IPlaceableObject obj : rightList) {
			if (obj instanceof Skyline) {
				skyToRemove = (Skyline) obj;
			}
		}
		printToLogger("unhighlighted\tskyline\tnoID\t@ 0," + skyToRemove.getMaxHeight());
		rightList.remove(skyToRemove);
		rightShapeList.repaint();
	}

	/**
	 * resets the GUI
	 */
	public void resetAll() {
		leftList.clear();
		leftShapeList.revalidate();
		leftShapeList.repaint();
		rightList.clear();
		rightShapeList.revalidate();
		rightShapeList.repaint();
		logPanel.setText("");
	}

	/**
	 * main method
	 * @param args not used in a swing-application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new StockCutterGUI();
			}
		});
	}
}