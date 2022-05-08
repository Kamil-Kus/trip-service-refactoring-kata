package kamil.kus.tripservice.trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return userSessionAdapter.getLoggedUser()
                .map(loggedUser2 -> getTripList(user, loggedUser2))
                .orElseThrow(UserNotLoggedInException::new);
    }

    private List<Trip> getTripList(User user, User loggedUser2) {
        for (User friend : user.getFriends()) {
            return getTripFromDatabase(user, loggedUser2, friend);
        }
        return new ArrayList<>();
    }

    private List<Trip> getTripFromDatabase(User user, User loggedUser, User friend) {
        if (isFriend(loggedUser, friend)) {
            return databaseAdapter.extractTripsByUser(user);
        }
        return new ArrayList<>();
    }

    private boolean isFriend(User loggedUser, User friend) {
        return friend.equals(loggedUser);
    }
}
