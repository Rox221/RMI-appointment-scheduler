import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AppointmentService extends Remote {
    void addAppointment(String date, String time, String description) throws RemoteException;
    List<String> getAppointments() throws RemoteException;
    void deleteAppointment(int index) throws RemoteException;
}
