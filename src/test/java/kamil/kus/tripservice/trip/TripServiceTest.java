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
        TestableService tripService = new TestableService(NOT_LOGGED_USER, TRIP_LIST);
        //Given and Then
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(USER_WITHOUT_FRIENDS));
    }

    @Test
    public void should_return_empty_list_when_user_is_not_friend() {
        //When
        TestableService tripService = new TestableService(LOGGED_USER, TRIP_LIST);
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
        TestableService tripService = new TestableService(LOGGED_USER, TRIP_LIST);
        //Given
        final List<Trip> tripsByUser = tripService.getTripsByUser(USER_WITH_FRIENDS);
        //Then
        assertThat(tripsByUser.isEmpty()).isFalse();
    }
}

class TestableService extends TripService {
    private final User loggedUser;
    private final List<Trip> tripList;

    @Override
    protected User getLoggedUser() {
        return loggedUser;
    }

    @Override
    protected List<Trip> extractTripsByUser(User user) {
        return tripList;
    }

    public TestableService(User loggedUser, List<Trip> tripList) {
        this.loggedUser = loggedUser;
        this.tripList = tripList;
    }
}
