package main.ui;

import javax.swing.*;

// This is the super class for StudentModulePanel, and StaffModulePanel.
public abstract class ModulePanel extends JPanel {
    abstract void redraw();

    abstract void filter(String toFilter);
}
