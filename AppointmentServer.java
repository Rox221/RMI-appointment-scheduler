import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AppointmentServer extends UnicastRemoteObject implements AppointmentService {
    private List<String> appointments;

    public AppointmentServer() throws RemoteException {
        super();
        this.appointments = new ArrayList<>();
    }

    @Override
    public synchronized void addAppointment(String date, String time, String description) throws RemoteException {
        appointments.add("Date: " + date + ", Time: " + time + ", Description: " + description);
    }

    @Override
    public synchronized List<String> getAppointments() throws RemoteException {
        return new ArrayList<>(appointments);
    }

    @Override
    public synchronized void deleteAppointment(int index) throws RemoteException {
        if (index >= 0 && index < appointments.size()) {
            appointments.remove(index);
        }
    }

    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            AppointmentService appointmentService = new AppointmentServer();
            java.rmi.Naming.rebind("AppointmentService", appointmentService);
            System.out.println("Appointment Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
                               }
