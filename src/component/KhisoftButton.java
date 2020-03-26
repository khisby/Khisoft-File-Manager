/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author khisby
 */
public class KhisoftButton extends JButton {
    Integer index;
    boolean multiSelected;

    public boolean isMultiSelected() {
        return multiSelected;
    }

    public void setMultiSelected(boolean MultiSelected) {
        this.multiSelected = MultiSelected;
    }

    public KhisoftButton() {
    }

    public KhisoftButton(Icon icon) {
        super(icon);
    }

    public KhisoftButton(String text) {
        super(text);
    }

    public KhisoftButton(Action a) {
        super(a);
    }

    public KhisoftButton(String text, Icon icon) {
        super(text, icon);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
