package org.mql.java.ui;

import javax.swing.*;

import org.mql.java.models.PackageEntity;

import java.util.List;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame(List<PackageEntity> packages) {
        setTitle("Class Diagram");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ClassDiagramPanel classDiagramPanel = new ClassDiagramPanel(packages);
        JScrollPane scrollPane = new JScrollPane(classDiagramPanel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
