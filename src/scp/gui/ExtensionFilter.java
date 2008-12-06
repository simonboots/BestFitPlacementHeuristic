package scp.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * filter is used to select only *.scp and *.xml files
 * @author Benjamin Clauss
 */
public class ExtensionFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml") || f.getName().toLowerCase().endsWith(".scp");
	}

	@Override
	public String getDescription() {
		return "SCP Dateien (*.scp, *.xml)";
	}

}
