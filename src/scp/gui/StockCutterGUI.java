package scp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
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

  public StockCutterGUI() {
    super("Das Stock-Cutting Problem");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    setSize(800, 600);

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

    JPanel unsortedPanel = new JPanel();
    unsortedPanel.setBackground(Color.white);
    unsortedPanel.setToolTipText("unsorted ScrollPanel");
    JScrollPane unsortedScrollPane = new JScrollPane(unsortedPanel);
    unsortedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    JPanel navigation = new JPanel();
    navigation.setBackground(Color.white);
    navigation.setToolTipText("Navigation");

    JPanel sortedPanel = new JPanel();
    sortedPanel.setBackground(Color.white);
    sortedPanel.setToolTipText("sorted ScrollPanel");
    JScrollPane sortedScrollPane = new JScrollPane(sortedPanel);
    sortedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    setLayout(null);
    
    unsortedScrollPane.setBounds(10, 10, 300, 300);
    add(unsortedScrollPane);
    
    navigation.setBounds(10, 320, 300, 221);
    add(navigation);
    
    sortedScrollPane.setBounds(320, 10, 465, 533);
    add(sortedScrollPane);
    
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == openItem) {
      final JFileChooser fc = new JFileChooser();

      fc.setFileFilter(new FileFilter() {

        @Override
        public boolean accept(File f) {
          return f.isDirectory() || f.getName().toLowerCase().endsWith(".scp");
        }

        @Override
        public String getDescription() {
          return "SCP Dateien (*.scp)";
        }
      });

      int returnVal = fc.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();
        System.out.println("Opening: " + file.getName());
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