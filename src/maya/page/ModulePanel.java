package maya.page;

import javax.swing.*;

public abstract class ModulePanel extends JPanel {
    abstract void redraw();

    abstract void filter(String toFilter);
}
