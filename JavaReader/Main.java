//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import javax.swing.*;

void main() {
    JFrame frame = new JFrame();
    frame.setResizable(false);
    frame.setContentPane(new JPanel());
    frame.setSize(300, 400);

    InputField turbid_secchi_field = new InputField("Turbid Secchi depth", "Submit", 4);
    turbid_secchi_field.setListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CalculatorEngine.set_secchi_depth_turbid(Float.parseFloat(turbid_secchi_field.getFieldContent()));
        }
    });

    InputField control_secchi_field = new InputField("Control Secchi depth", "Submit", 4);
    control_secchi_field.setListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CalculatorEngine.set_secchi_depth_control(Float.parseFloat(control_secchi_field.getFieldContent()));
        }
    });

    InputField turbid_conductivity = new InputField("Turbid conductivity", "Submit", 4);
    turbid_conductivity.setListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CalculatorEngine.set_conductivity_turbid(Float.parseFloat(turbid_conductivity.getFieldContent()));
        }
    });

    InputField control_conductivity = new InputField("Control conductivity", "Submit", 4);
    control_conductivity.setListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CalculatorEngine.set_conductivity_control(Float.parseFloat(control_conductivity.getFieldContent()));
        }
    });

    turbid_secchi_field.addToFrame(frame);
    control_secchi_field.addToFrame(frame);
    turbid_conductivity.addToFrame(frame);
    control_conductivity.addToFrame(frame);

    CalculatorEngine.createEngine(frame);
    frame.setVisible(true);
}
