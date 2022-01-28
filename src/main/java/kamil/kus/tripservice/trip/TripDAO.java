package kamil.kus.tripservice.trip;

import java.util.List;

import kamil.kus.tripservice.exception.CollaboratorCallException;
import kamil.kus.tripservice.user.User;

public class TripDAO {

	public static List<Trip> findTripsByUser(User user) {
		throw new CollaboratorCallException(
				"TripDAO should not be invoked on an unit test.");
	}
	
}