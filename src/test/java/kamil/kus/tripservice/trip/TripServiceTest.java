package kamil.kus.tripservice.trip;

import kamil.kus.tripservice.exception.UserNotLoggedInException;
import kamil.kus.tripservice.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TripServiceTest {

    private static final User NOT_LOGGED_USER = null;
    private static final User LOGGED_USER = new User();
    private static final User USER_WITHOUT_FRIENDS = new User();
    private static final User USER_WITH_FRIENDS = new User();
    private static final List<Trip> TRIP_LIST = new ArrayList<>();
    private static final Trip ITALY = new Trip();

    @Test
    public void should_throw_exception_when_user_is_not_logged() {
        //When
        TripService tripService = new TripService(new TestableDatabaseAdapter(TRIP_LIST), new TestableUserSessionAdapter(NOT_LOGGED_USER));
        //Given and Then
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(USER_WITHOUT_FRIENDS));
    }

    @Test
    public void should_return_empty_list_when_user_is_not_friend() {
        //When
        TripService tripService = new TripService(new TestableDatabaseAdapter(TRIP_LIST), new TestableUserSessionAdapter(LOGGED_USER));
        //Given
        final List<Trip> tripsByUser = tripService.getTripsByUser(USER_WITHOUT_FRIENDS);
        //Then
        assertThat(tripsByUser.isEmpty()).isTrue();
    }

    @Test
    public void should_return_trip_list_when_user_is_friend() {
        //When
        TRIP_LIST.add(ITALY);
        USER_WITH_FRIENDS.addFriend(LOGGED_USER);
        TripService tripService = new TripService(new TestableDatabaseAdapter(TRIP_LIST), new TestableUserSessionAdapter(LOGGED_USER));
        //Given
        final List<Trip> tripsByUser = tripService.getTripsByUser(USER_WITH_FRIENDS);
        //Then
        assertThat(tripsByUser.isEmpty()).isFalse();
    }
}

class TestableDatabaseAdapter extends DatabaseAdatper {

    private final List<Trip> tripList;

    public TestableDatabaseAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @Override
    public List<Trip> extractTripsByUser(User user) {
        return tripList;
    }
}

class TestableUserSessionAdapter extends UserSessionAdapter {

    private final User user;

    public TestableUserSessionAdapter(User user) {
        this.user = user;
    }

    @Override
    protected User getLoggedUser() {
        return user;
    }
}
