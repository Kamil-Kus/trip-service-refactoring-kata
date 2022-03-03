package kamil.kus.tripservice.trip;

import kamil.kus.tripservice.user.User;

import java.util.List;

public class DatabaseAdatper {
    public DatabaseAdatper() {
    }

    protected List<Trip> extractTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }
}