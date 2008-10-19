package scp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import scp.common.Shape;
import scp.common.xml.Parser;

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
  
  private ShapeGetter sg = new ShapeGetter();
  
  private ArrayList<Shape> shapeList = new ArrayList<Shape>();

  public StockCutterGUI() {
    super("Das Stock-Cutting Problem");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    setSize(800, 550);

    int x = (screenSize.width - getWidth()) / 2;
    int y = (screenSize.height - getHeight()) / 2;

    setLocation(x, y);
    setResizable(false);

    //--------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------

    leftShapeList = new LeftDrawingPane(shapeList);
    leftShapeList.setBackground(Color.white);
    leftScrollPanel = new JScrollPane(leftShapeList);
    leftScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    leftScrollPanel.setPreferredSize(new Dimension(300, 350));

    JPanel navigationPanel = new JPanel(new FlowLayout());
    navigationPanel.setBackground(Color.white);

    JPanel rightShapeList = new JPanel();
    rightShapeList.setBackground(Color.white);
    JScrollPane rightScrollPanel = new JScrollPane(rightShapeList);
    rightScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    setLayout(null);

    leftScrollPanel.setBounds(10, 10, 300, 350);
    add(leftScrollPanel);

    navigationPanel.setBounds(10, 370, 300, 122);
    add(navigationPanel);

    rightScrollPanel.setBounds(320, 10, 465, 483);
    add(rightScrollPanel);

  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == openItem) {
      final JFileChooser fc = new JFileChooser(".");

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

        Parser p = new Parser(file.getAbsolutePath());
		p.setSchemaFilename("src/StockCuttingProblem.xsd");
		p.setShapeCallback(sg);
        try {
          p.parse();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, ex.toString(), "Fehler beim Parsen!", JOptionPane.ERROR_MESSAGE);
          ex.printStackTrace();
        }
        
        for (Shape shape : sg.getShapeList()) {
          shapeList.add(shape);
        }
        
        leftScrollPanel.revalidate();
        leftScrollPanel.repaint();
        
      } else {
        System.out.println("Open command cancelled by user.");
      }
    } else if (e.getSource() == closeItem) {
      System.exit(0);
    } else if (e.getSource() == aboutItem) {
      JOptionPane.showMessageDialog(null, "(c)2008 by Simon Stiefel & Benjamin Clauss", "Über", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public static void main(String[] args) {
    StockCutterGUI scg = new StockCutterGUI();
    scg.setVisible(true);
  }
}