import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

public class TurnosGUI extends JFrame implements ActionListener {
    private JTextField tfNombre, tfEdad, tfAfiliacion, tfCondicion;
    private JTextArea taTurnos;
    private JButton btnNuevoTurno, btnExtenderTiempo;
    private JLabel lblTurno, lblTiempoRestante, lblTurnosPendientes;
    private Queue<Paciente> colaTurnos;
    private Paciente pacienteEnCurso;
    private int tiempoRestante;

    public TurnosGUI() {
        // Configurar la ventana
        setTitle("Asignación de turnos en EPS");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear los componentes de la interfaz
        tfNombre = new JTextField();
        tfEdad = new JTextField();
        tfAfiliacion = new JTextField();
        tfCondicion = new JTextField();
        taTurnos = new JTextArea();
        btnNuevoTurno = new JButton("Nuevo turno");
        btnExtenderTiempo = new JButton("Extender tiempo");
        lblTurno = new JLabel("Turno en curso:");
        lblTiempoRestante = new JLabel("Tiempo restante:");
        lblTurnosPendientes = new JLabel("Turnos pendientes:");

        // Añadir los componentes a la ventana
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Nombre y apellidos:"));
        panel.add(tfNombre);
        panel.add(new JLabel("Edad:"));
        panel.add(tfEdad);
        panel.add(new JLabel("Afiliación (POS o PC):"));
        panel.add(tfAfiliacion);
        panel.add(new JLabel("Condición especial (embarazo o limitación motriz):"));
        panel.add(tfCondicion);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevoTurno);
        panelBotones.add(btnExtenderTiempo);

        JPanel panelTurnos = new JPanel();
        panelTurnos.setLayout(new BoxLayout(panelTurnos, BoxLayout.Y_AXIS));
        panelTurnos.add(lblTurno);
        panelTurnos.add(lblTiempoRestante);
        panelTurnos.add(lblTurnosPendientes);
        panelTurnos.add(taTurnos);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(panelBotones, BorderLayout.CENTER);
        container.add(panelTurnos, BorderLayout.SOUTH);

        colaTurnos = new LinkedList<Paciente>();

        btnNuevoTurno.addActionListener(this);
        btnExtenderTiempo.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevoTurno) {
            String nombre = tfNombre.getText();
            int edad = Integer.parseInt(tfEdad.getText());
            String afiliacion = tfAfiliacion.getText();
            String condicion = tfCondicion.getText();
            Paciente paciente = new Paciente(nombre, edad, afiliacion, condicion);

            colaTurnos.offer(paciente);

taTurnos.setText("Turnos pendientes:\n");
for (Paciente p : colaTurnos) {
taTurnos.append("- " + p.getNombre() + "\n");
}
        if (pacienteEnCurso == null) {
            atenderSiguientePaciente();
        }
    } else if (e.getSource() == btnExtenderTiempo) {
        tiempoRestante += 5;
        actualizarTiempoRestante();
    }
}

private void atenderSiguientePaciente() {
    pacienteEnCurso = colaTurnos.poll();

    lblTurno.setText("Turno en curso: " + pacienteEnCurso.getNombre());

    tiempoRestante = 5;
    actualizarTiempoRestante();
    taTurnos.setText("Turnos pendientes:\n");
    for (Paciente p : colaTurnos) {
        taTurnos.append("- " + p.getNombre() + "\n");
    }
    if (!colaTurnos.isEmpty()) {
        Timer timer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atenderSiguientePaciente();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

private void actualizarTiempoRestante() {
    lblTiempoRestante.setText("Tiempo restante: " + tiempoRestante + " segundos");
    if (tiempoRestante == 0) {
        atenderSiguientePaciente();
    } else {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                actualizarTiempoRestante();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

private static class Paciente {
    private String nombre;
    private int edad;
    private String afiliacion;
    private String condicion;

    public Paciente(String nombre, int edad, String afiliacion, String condicion) {
        this.nombre = nombre;
        this.edad = edad;
        this.afiliacion = afiliacion;
        this.condicion = condicion;
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

    public String getCondicion() {
        return condicion;
    }
}

public static void main(String[] args) {
    TurnosGUI gui = new TurnosGUI();
    gui.setVisible(true);
}
}