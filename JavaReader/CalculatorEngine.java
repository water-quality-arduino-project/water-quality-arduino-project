import java.awt.*;
import javax.swing.*;
import java.lang.Math;
import java.text.DecimalFormat;

public class CalculatorEngine {
    private static CalculatorEngine instance;

    private float secchi_depth_turbid = -1;
    private float secchi_depth_control = -1;
    private float conductivity_turbid = -1;
    private float conductivity_control = -1;

    private CalculatorResults results = new CalculatorResults();
    private boolean resultsGenerated = false;

    private JFrame frame;
    private JLabel control_data = new JLabel();
    private JLabel turbid_data = new JLabel();

    private CalculatorEngine(){}

    public static CalculatorEngine createEngine(JFrame frame){
        if(instance == null){
            instance = new CalculatorEngine();
        }
        instance.frame = frame;

        frame.add(instance.control_data);
        frame.add(instance.turbid_data);
        return instance;
    }

    public static void set_secchi_depth_turbid(float s) {
        instance.secchi_depth_turbid = s;
        if (instance.secchi_depth_turbid >= 0 && instance.secchi_depth_control >= 0 && instance.conductivity_turbid >= 0 && instance.conductivity_control >= 0){
            instance.perform_calcs();
        }
    }

    public static void set_secchi_depth_control(float s) {
        instance.secchi_depth_control = s;
        if (instance.secchi_depth_turbid >= 0 && instance.secchi_depth_control >= 0 && instance.conductivity_turbid >= 0 && instance.conductivity_control >= 0){
            instance.perform_calcs();
        }
    }

    public static void set_conductivity_control(float c) {
        instance.conductivity_control = c;
        if (instance.secchi_depth_turbid >= 0 && instance.secchi_depth_control >= 0 && instance.conductivity_turbid >= 0 && instance.conductivity_control >= 0){
            instance.perform_calcs();
        }
    }

    public static void set_conductivity_turbid(float c) {
        instance.conductivity_turbid = c;
        if (instance.secchi_depth_turbid >= 0 && instance.secchi_depth_control >= 0 && instance.conductivity_turbid >= 0 && instance.conductivity_control >= 0){
            instance.perform_calcs();
        }
    }

    private static int[] convert_ntu_to_rgb(double ntu){
        if (ntu <= 250) {
            return new int[]{250, (int) Math.floor(250 - ntu / 2.5), (int) Math.floor(250 - ntu)};
        }else if (ntu <= 880){
            return new int[]{(int) Math.floor(250 * (1000 - ntu)/750), (int) Math.floor(150 * (1000 - ntu)/750), 0};
        }else{
            return new int[]{40, 24, 0};
        }
    }
    private static void perform_calcs(){
        instance.results.turbid_ntu = 9.41 * Math.pow(instance.secchi_depth_turbid, -1.24);
        instance.results.control_ntu = 9.41 * Math.pow(instance.secchi_depth_control, -1.24);

        instance.results.turbid_colour = convert_ntu_to_rgb(instance.results.turbid_ntu);
        instance.results.control_colour = convert_ntu_to_rgb(instance.results.control_ntu);

        instance.results.turbid_salinity = 0.51 * instance.conductivity_turbid - 5.99;
        instance.results.control_salinity = 0.51 * instance.conductivity_control - 5.99;

        instance.resultsGenerated = true;
        drawData();
    }

    private static void drawData(){
        Graphics g = instance.frame.getGraphics();
        DecimalFormat rounder = new DecimalFormat("#.#");

        instance.control_data.setText("Control (left): " + rounder.format(get_results().control_ntu) + " NTU - Salinity " + rounder.format(get_results().control_salinity) + " mg/L");

        instance.turbid_data.setText("Turbid (right): " + rounder.format(get_results().turbid_ntu) + " NTU - Salinity " + rounder.format(get_results().turbid_salinity) + " mg/L");
        instance.frame.add(instance.turbid_data);

        g.setColor(new Color(get_results().control_colour[0], get_results().control_colour[1], get_results().control_colour[2]));
        g.fillRect(40, 250, 100, 100);

        g.setColor(new Color(get_results().turbid_colour[0], get_results().turbid_colour[1], get_results().turbid_colour[2]));
        g.fillRect(160, 250, 100, 100);

    }

    public static CalculatorResults get_results(){
        return instance.results;
    }

    public static boolean check_results_generated(){
        return instance.resultsGenerated;
    }
}
