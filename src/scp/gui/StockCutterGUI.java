package scp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;
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
import scp.common.PlacedShape;
import scp.common.Shape;

/**
 *
 * @author Benjamin Clauss
 */
public class StockCutterGUI extends JFrame implements ActionListener {

  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenu extrasMenu;
  private JMenu aboutMenu;
  private JMenuItem openItem;
  private JMenuItem closeItem;
  private JMenuItem extrasItem;
  private JMenuItem aboutItem;
  private JPanel leftShapeList;
  private JScrollPane leftScrollPanel;
  private JPanel navigationPanel;
  private JPanel rightShapeList;
  private JScrollPane rightScrollPanel;
  private Vector<Shape> shapeList = new Vector<Shape>();
  private HashMap<Integer, PlacedShape> sortedList = new HashMap<Integer, PlacedShape>();
  private JButton next;
  private JButton playStop;
  private JButton previous;
  private JTextArea logPanel;
  private JScrollPane logScrollPanel;
  private JPanel leftPanel;

  public StockCutterGUI() {
    super("Das Stock-Cutting Problem");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setLocation(250, 100);
    setResizable(false);

    menuBar = new JMenuBar();

    fileMenu = new JMenu("Datei");
    openItem = new JMenuItem("Öffnen", 'f');
    openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    openItem.addActionListener(this);
    closeItem = new JMenuItem("Beenden", 'e');
    closeItem.addActionListener(this);
    fileMenu.add(openItem);
    fileMenu.addSeparator();
    fileMenu.add(closeItem);

    extrasMenu = new JMenu("Extras");
    extrasItem = new JMenuItem("...");
    extrasMenu.add(extrasItem);

    aboutMenu = new JMenu("?");
    aboutItem = new JMenuItem("Über", 'b');
    aboutItem.addActionListener(this);
    aboutMenu.add(aboutItem);

    menuBar.add(fileMenu);
    menuBar.add(extrasMenu);
    menuBar.add(aboutMenu);

    setJMenuBar(menuBar);


    leftShapeList = new LeftDrawingPane(shapeList);
    leftShapeList.setBackground(Color.white);
    leftScrollPanel = new JScrollPane(leftShapeList);
    leftScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    leftScrollPanel.setPreferredSize(new Dimension(318, 300));

    navigationPanel = new JPanel();
    navigationPanel.setPreferredSize(new Dimension(318, 150));

    next = new JButton(">>");
    next.setPreferredSize(new Dimension(75, 26));
    next.addActionListener(this);

    playStop = new JButton("play");
    playStop.setPreferredSize(new Dimension(75, 26));
    playStop.addActionListener(this);

    previous = new JButton("<<");
    previous.setPreferredSize(new Dimension(75, 26));
    previous.addActionListener(this);

    navigationPanel.add(previous);
    navigationPanel.add(playStop);
    navigationPanel.add(next);

    leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(leftScrollPanel, BorderLayout.NORTH);
    leftPanel.add(navigationPanel, BorderLayout.CENTER);

    rightShapeList = new RightDrawingPane(sortedList);
    rightShapeList.setBackground(Color.white);
    rightScrollPanel = new JScrollPane(rightShapeList);
    rightScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    rightScrollPanel.setPreferredSize(new Dimension(418, 450));

    logPanel = new JTextArea("=== MESSAGE LOG ===\n\nhier\nsoll\nd'r\nLog\nrein!");
    logPanel.setEditable(false);
    logPanel.setRows(4);
    logScrollPanel = new JScrollPane(logPanel);
    logScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    setLayout(new BorderLayout());

    add(leftPanel, BorderLayout.WEST);
    add(rightScrollPanel, BorderLayout.CENTER);
    add(logScrollPanel, BorderLayout.SOUTH);

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
        File file = fc.getSelectedFile();
        System.out.println("Opening: " + file.getAbsolutePath());
      }
    } else if (e.getSource() == closeItem) {
      System.exit(0);
    } else if (e.getSource() == aboutItem) {
      JOptionPane.showMessageDialog(null, "(c)2008 by Simon Stiefel & Benjamin Clauss", "Über", JOptionPane.INFORMATION_MESSAGE);
    } else if (e.getSource() == next) {
      sortedList.put(1, new PlacedShape(12, 50, 100, 0, 0));
      rightScrollPanel.revalidate();
      rightScrollPanel.repaint();
    } else if (e.getSource() == playStop) {
      if (playStop.getText().equals("play")) {
        playStop.setText("stop");
      } else if (playStop.getText().equals("stop")) {
        playStop.setText("play");
      }
    } else if (e.getSource() == previous) {
      sortedList.remove(1);
      rightScrollPanel.revalidate();
      rightScrollPanel.repaint();
    }
  }

  public static void main(String[] args) {
    StockCutterGUI scg = new StockCutterGUI();
    scg.pack();
    scg.setVisible(true);
  }
}