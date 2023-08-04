/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

/**
 *
 * @author afgha
 */
public class reset {
    public void resetTextFields(Container container) {
    Component[] components = container.getComponents();
    for (Component component : components) {
        if (component instanceof JTextField) {
            JTextField textField = (JTextField) component;
            textField.setText("");
        } else if (component instanceof Container) {
            resetTextFields((Container) component);
        }
    }
    
    for (Component component : components) {
        if (component instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) component;
            comboBox.setSelectedIndex(0);
        } else if (component instanceof Container) {
            resetTextFields((Container) component);
        }
    }
    
    for (Component component : components) {
        if (component instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) component;
            checkBox.setSelected(false);
        } else if (component instanceof Container) {
            resetTextFields((Container) component);
        }
    }
}

}
