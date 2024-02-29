import javax.swing.*;
import java.rmi.Naming;
import java.util.List;

public class AppointmentClient {
    private static AppointmentService appointmentService;

    public static void main(String[] args) {
        try {
            appointmentService = (AppointmentService) Naming.lookup("rmi://localhost/AppointmentService");

            while (true) {
                showMenu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showMenu() {
        String[] options = {"Add Appointment", "View Appointments", "Delete Appointment", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Appointment Scheduler Menu",
                "Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        switch (choice) {
            case 0:
                addAppointment();
                break;
            case 1:
                viewAppointments();
                break;
            case 2:
                deleteAppointment();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Exiting the Appointment Scheduler. Goodbye!");
                System.exit(0);
            default:
                JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
        }
    }

    private static void addAppointment() {
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
                "Date:", dateField,
                "Time:", timeField,
                "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Appointment", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String date = dateField.getText();
            String time = timeField.getText();
            String description = descriptionField.getText();

            try {
                appointmentService.addAppointment(date, time, description);
                JOptionPane.showMessageDialog(null, "Appointment added successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error adding appointment: " + e.getMessage());
            }
        }
    }

    private static void viewAppointments() {
        try {
            List<String> appointments = appointmentService.getAppointments();

            if (appointments.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No appointments available.");
            } else {
                StringBuilder message = new StringBuilder("Appointments:\n");
                for (int i = 0; i < appointments.size(); i++) {
                    message.append((i + 1)).append(". ").append(appointments.get(i)).append("\n");
                }
                JOptionPane.showMessageDialog(null, message.toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving appointments: " + e.getMessage());
        }
    }

    private static void deleteAppointment() {
        String input = JOptionPane.showInputDialog("Enter the index of the appointment to delete:");

        if (input != null) {
            try {
                int indexToDelete = Integer.parseInt(input) - 1;
                appointmentService.deleteAppointment(indexToDelete);
                JOptionPane.showMessageDialog(null, "Appointment deleted successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error deleting appointment: " + e.getMessage());
            }
        }
    }
                  }
