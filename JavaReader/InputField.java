import javax.swing.*;
import java.awt.event.ActionListener;

public class InputField {
    private JLabel label = new JLabel();
    private JTextField field = new JTextField();
    private JButton button = new JButton();

    public InputField(String desc, String button_text, int field_width) {
        label.setText(desc + ':');
        field.setColumns(field_width);
        button.setText(button_text);
    }

    public void setListener(ActionListener listener){
        button.addActionListener(listener);
    }

    public void addToFrame(JFrame frame) {
        frame.getContentPane().add(label);
        frame.getContentPane().add(field);
        frame.getContentPane().add(button);
    }

    public String getFieldContent(){
        return field.getText();
    }
}
