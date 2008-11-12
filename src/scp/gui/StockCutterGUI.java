package scp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;
import scp.common.PlacedShape;
import scp.common.Shape;
import scp.common.actionchain.ActionExecutor;
import scp.common.actionchain.IAction;
import scp.common.actionchain.actions.PlaceShapeAction;
import scp.common.xml.XMLBridge;

/**
 *
 * @author Benjamin Clauss
 */
public class StockCutterGUI extends JFrame implements ActionListener {

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
  private HashMap<Integer, ColoredShape> leftList = new HashMap<Integer, ColoredShape>();
  private HashMap<Integer, ColoredPlacedShape> rightList = new HashMap<Integer, ColoredPlacedShape>();
  /**
   * ShapeMagazin, ShapePlacer, ActionExecutor
   */
  private List<Shape> shapelist = new ArrayList<Shape>();
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
  private JButton reset;
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
    next.setPreferredSize(new Dimension(75, 26));
    next.addActionListener(this);

    playStop = new JButton("play");
    playStop.setPreferredSize(new Dimension(75, 26));
    playStop.addActionListener(this);

    previous = new JButton("<");
    previous.setToolTipText("previous step");
    previous.setPreferredSize(new Dimension(75, 26));
    previous.addActionListener(this);

    reset = new JButton("reset");
    reset.setPreferredSize(new Dimension(75, 26));
    reset.addActionListener(this);

    navigationPanel.add(previous);
    navigationPanel.add(playStop);
    navigationPanel.add(next);
    navigationPanel.add(reset);

    leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(leftScrollPanel, BorderLayout.NORTH);
    leftPanel.add(navigationPanel, BorderLayout.CENTER);

    rightShapeList = new RightDrawingPane(rightList);
    rightShapeList.setBackground(Color.white);
    rightScrollPanel = new JScrollPane(rightShapeList);
    rightScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    rightScrollPanel.setPreferredSize(new Dimension(468, 450));

    logPanel = new JTextArea();
    logPanel.setEditable(false);
    logPanel.setRows(5);
    logScrollPanel = new JScrollPane(logPanel);
    logScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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

          // bridge.getLists to ActionGenerator
          Shape s1 = new Shape(1, 20, 30);
          Shape s2 = new Shape(2, 50, 100);

          shapelist.add(s1);
          shapelist.add(s2);

          loadMagazine(shapelist);

          removeColoredShape(s2);

          PlacedShape tempShape1 = new PlacedShape(1, 12, 12, 0, 0);
          PlacedShape tempShape2 = new PlacedShape(2, 24, 24, 12, 0);
          PlacedShape tempShape3 = new PlacedShape(3, 77, 77, 36, 0);

          PlaceShapeAction psa1 = new PlaceShapeAction(tempShape1);
          PlaceShapeAction psa2 = new PlaceShapeAction(tempShape2);
          PlaceShapeAction psa3 = new PlaceShapeAction(tempShape3);

          doQueue.add(psa1);
          doQueue.add(psa2);
          doQueue.add(psa3);

        } catch (JAXBException ex) {
          ex.printStackTrace();
        }
      }
    } else if (e.getSource() == closeItem) {
      System.exit(0);
    } else if (e.getSource() == aboutItem) {
      JOptionPane.showMessageDialog(null, "(c)2008 by Simon Stiefel & Benjamin Clauss", "Über", JOptionPane.INFORMATION_MESSAGE);
    } else if (e.getSource() == next) {
      executor.executeNextAction();
    } else if (e.getSource() == previous) {
      executor.executePreviousAction();
    } else if (e.getSource() == reset) {
      resetAll();
    }
  }

  /**
   * loads shapes out of xml into magazine
   * @param shapelist 
   */
  public void loadMagazine(List<Shape> shapelist) {

    int pos = 0;
    for (Shape shape : shapelist) {
      ColoredShape cs = new ColoredShape(shape);
      cs.setColor(Color.lightGray);
      leftList.put(pos++, cs);
    }

    leftShapeList.revalidate();
    leftShapeList.repaint();
    appendToLog("loaded Shapes from XML");
  }

  /**
   * adds a shape to the stockroll
   * @param s PlacedShape
   */
  public void placeColoredPlacedShape(PlacedShape s) {
    ColoredPlacedShape cps = new ColoredPlacedShape(s);
    cps.setColor(Color.lightGray);
    rightList.put(s.getId(), cps);
    rightShapeList.revalidate();
    rightShapeList.repaint();
    appendToLog("placed Shape (ID: " + s.getId() + " @ " + s.getX() + "," + s.getY() + ")");
  }

  /**
   * highlights a shape in the magazine
   * @param s Shape
   */
  public void highlightColoredShape(Shape s) {
    for (int i = 0; i < leftList.size(); i++) {
      if (leftList.get(i).getId() == s.getId()) {
        ColoredShape cs = new ColoredShape(s);
        cs.setColor(Color.red);
        leftList.put(i, cs);
      }
    }
  }

  /**
   * highlights a shape on the stockroll
   * @param s PlacedShape
   */
  public void highlightColoredPlacedShape(PlacedShape s) {
    rightList.remove(s);
    ColoredPlacedShape cps = new ColoredPlacedShape(s);
    cps.setColor(Color.red);
    rightList.put(s.getId(), cps);
  }

  /**
   * unhighlights all shapes in the magazine
   * @param s Shape
   */
  public void unhighlightColoredShapes() {
    for (int i = 0; i < leftList.size(); i++) {
      if (leftList.get(i).getColor().equals(Color.red)) {
        leftList.get(i).setColor(Color.lightGray);
      }
    }
  }
  
  /**
   * unhighlights all shapes on the stockroll
   */
  public void unhighlightColoredPlacedShapes() {
    Iterator itr = rightList.keySet().iterator();

    while (itr.hasNext()) {
      Object key = itr.next();
      if (rightList.get(key).getColor().equals(Color.red)) {
        rightList.get(key).setColor(Color.lightGray);
      }
    }
  }
  
  /**
   * removes a shape from the magazine
   * @param s Shape
   */
  public void removeColoredShape(Shape s) {
    for (int i = 0; i < leftList.size(); i++) {
      if (leftList.get(i).getId() == s.getId()) {
        leftList.remove(i);
      }
    }
  }
  
  /**
   * inserts a shape to the magazine
   * @param s Shape
   */
  public void insertColoredShape(Shape s) {
  }

  /**
   * removes a shape from the stockroll
   * @param s PlacedShape
   */
  public void removeColoredPlacedShape(PlacedShape s) {
    rightList.remove(s.getId());
    rightShapeList.revalidate();
    rightShapeList.repaint();
    appendToLog("removed Shape (ID: " + s.getId() + " @ " + s.getX() + "," + s.getY() + ")");
  }

  /**
   * resets the GUI
   */
  public void resetAll() {
    logPanel.setText("");
    rightList.clear();
    rightShapeList.revalidate();
    rightShapeList.repaint();
    leftList.clear();
    leftShapeList.revalidate();
    leftShapeList.repaint();
  }

  /**
   * appends a String to the logger
   * @param s String with message
   */
  public void appendToLog(String s) {
    logPanel.append(s + "\n");
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        new StockCutterGUI();
      }
    });
  }
}