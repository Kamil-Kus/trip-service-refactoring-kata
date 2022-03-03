package kamil.kus.tripservice.trip;

import kamil.kus.tripservice.user.User;

import java.util.List;

public class DatabaseAdapter {
    public DatabaseAdapter() {
    }

    protected List<Trip> extractTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }
}