import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
private class Paciente {
    private final String nombre;
    private final int edad;
    private final String afiliacion;
    private final boolean embarazo;
    private final boolean limitacion;
    private final String turno;

    public Paciente(String nombre, int edad, String afiliacion, boolean embarazo, boolean limitacion) {
        this.nombre = nombre;
        this.edad = edad;
        this.afiliacion = afiliacion;
        this.embarazo = embarazo;
        this.limitacion = limitacion;
        this.turno = generarTurno();
        }
        public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getAfiliacion() {
        return afiliacion;
    }

    public boolean isEmbarazo() {
        return embarazo;
    }

    public boolean isLimitacion() {
        return limitacion;
    }

    public String getTurno() {
        return turno;
    }

    public boolean esPrioritario() {
        return edad >= 60 || edad <= 12 || embarazo || limitacion || afiliacion.equals("PC");
    }

    private String generarTurno() {
        String letra = "";
        if (colaTurnos.isEmpty()) {
            letra = "A";
        } else {
            String ultimoTurno;
            ultimoTurno = colaTurnos.getLast().getTurno();
            char ultimaLetra = ultimoTurno.charAt(0);
            if (ultimaLetra == 'Z') {
                letra = "AA";
            } else if (ultimoTurno.length() == 1) {
                letra = String.valueOf((char) (ultimaLetra + 1));
            } else {
                char penultimaLetra = ultimoTurno.charAt(1);
                if (penultimaLetra == 'Z') {
                    letra = String.valueOf((char) (ultimaLetra + 1)) + "A";
                } else {
                    letra = String.valueOf(ultimaLetra) + String.valueOf((char) (penultimaLetra + 1));
                }
            }
        }
        return letra + (colaTurnos.size() + 1);
    }
}

public class TurnosEPS extends JFrame implements ActionListener {
    private final JTextField nombreTxt;
    private final JTextField edadTxt;
    private final JComboBox<String> afiliacionCmb;
    private final JCheckBox embarazoChk;
    private final JCheckBox limitacionChk;
    private final JButton crearBtn;
    private JButton extenderBtn;
    private JLabel turnoLbl;
    private JLabel tiempoLbl;
    private JLabel pendientesLbl;
    private Queue<Paciente> colaTurnos;
    private final Timer timer;
    private int tiempoRestante;

    public TurnosEPS() {
        super("EPS - Asignación de turnos");
        JLabel nombreLbl = new JLabel("Nombre y apellidos:");
        nombreTxt = new JTextField(20);
        JLabel edadLbl = new JLabel("Edad:");
        edadTxt = new JTextField(3);
        JLabel afiliacionLbl = new JLabel("Afiliación:");
        String[] afiliaciones = {"POS", "Plan Complementario"};
        afiliacionCmb = new JComboBox<>(afiliaciones);
        JLabel condicionLbl = new JLabel("Condición especial:");
        embarazoChk = new JCheckBox("Embarazo");
        limitacionChk = new JCheckBox("Limitación motriz");
        crearBtn = new JButton("Crear turno");
        extenderBtn = new JButton("Extender tiempo");
        turnoLbl = new JLabel("");
        tiempoLbl = new JLabel("");
        pendientesLbl = new JLabel("");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy = 0; panel.add(nombreLbl, c);
        c.gridx = 1; c.gridy = 0; panel.add(nombreTxt, c);
        c.gridx = 0; c.gridy = 1; panel.add(edadLbl, c);
        c.gridx = 1; c.gridy = 1; panel.add(edadTxt, c);
        c.gridx = 0; c.gridy = 2; panel.add(afiliacionLbl, c);
        c.gridx = 1; c.gridy = 2; panel.add(afiliacionCmb, c);
        c.gridx = 0; c.gridy = 3; panel.add(condicionLbl, c);
        c.gridx = 1; c.gridy = 3; panel.add(embarazoChk, c);
        c.gridx = 2; c.gridy = 3; panel.add(limitacionChk, c);
        c.gridx = 0; c.gridy = 4; panel.add(crearBtn, c);
        c.gridx = 1; c.gridy = 4; panel.add(extenderBtn, c);
        c.gridx = 0; c.gridy = 5; panel.add(new JLabel("Turno en curso:"), c);
        c.gridx = 1; c.gridy = 5; panel.add(turnoLbl, c);
        c.gridx = 0; c.gridy = 6; panel.add(new JLabel("Tiempo restante:"), c);
crearBtn.addActionListener(this);
    extenderBtn.addActionListener(this);
    extenderBtn.setEnabled(false);

    add(panel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setVisible(true);

    colaTurnos = new LinkedList<>();

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            if (!colaTurnos.isEmpty()) {
                tiempoRestante--;
                if (tiempoRestante == 0) {
                    Paciente paciente = colaTurnos.remove();
                    turnoLbl.setText(paciente.getTurno());
                    tiempoLbl.setText("");
                    pendientesLbl.setText(Integer.toString(colaTurnos.size()));
                    extenderBtn.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "¡Turno " + paciente.getTurno() + "!\n" + paciente.getNombre() + "\n¡Pasar a la ventanilla!");
                } else {
                    tiempoLbl.setText(Integer.toString(tiempoRestante) + " segundos");
                }
            }
        }
    }, 0, 1000);
}

public static void main(String[] args) {
        TurnosEPS turnosEPS = new TurnosEPS();
}

@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == crearBtn) {
        String nombre = nombreTxt.getText();
        int edad = Integer.parseInt(edadTxt.getText());
        String afiliacion = (String) afiliacionCmb.getSelectedItem();
        boolean embarazo = embarazoChk.isSelected();
        boolean limitacion = limitacionChk.isSelected();
        Paciente paciente = new Paciente(nombre, edad, afiliacion, embarazo, limitacion);

        boolean prioritario = paciente.esPrioritario();
        if (prioritario && !colaTurnos.isEmpty()) {
            // Insertar paciente prioritario en el primer lugar de la cola
            colaTurnos.add(paciente);
            int i = 0;
            for (Paciente p : colaTurnos) {
                if (p == paciente) {
                    break;
                }
                i++;
            }
            colaTurnos.add(colaTurnos.remove(i));
        } else {
            colaTurnos.add(paciente);
        }

        turnoLbl.setText(paciente.getTurno());
        tiempoLbl.setText("");
        pendientesLbl.setText(Integer.toString(colaTurnos.size() - 1));
        extenderBtn.setEnabled(true);
        tiempoRestante = 300;
    } else if (e.getSource() == extenderBtn) {
        tiempoRestante += 5;
    }
}
}