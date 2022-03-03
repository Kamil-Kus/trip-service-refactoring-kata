package kamil.kus.tripservice.trip;

import java.util.ArrayList;
import java.util.List;

import kamil.kus.tripservice.exception.UserNotLoggedInException;
import kamil.kus.tripservice.user.User;

public class TripService {

    private final DatabaseAdapter databaseAdapter;
    private final UserSessionAdapter userSessionAdapter;

    public TripService(DatabaseAdapter databaseAdatper, UserSessionAdapter userSessionAdapter) {
        this.databaseAdapter = databaseAdatper;
        this.userSessionAdapter = userSessionAdapter;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        List<Trip> tripList = new ArrayList<Trip>();
        User loggedUser = userSessionAdapter.getLoggedUser();
        boolean isFriend = false;
        if (loggedUser != null) {
            for (User friend : user.getFriends()) {
                if (friend.equals(loggedUser)) {
                    isFriend = true;
                    break;
                }
            }
            if (isFriend) {
                tripList = databaseAdapter.extractTripsByUser(user);
            }
            return tripList;
        } else {
            throw new UserNotLoggedInException();
        }
    }
}
