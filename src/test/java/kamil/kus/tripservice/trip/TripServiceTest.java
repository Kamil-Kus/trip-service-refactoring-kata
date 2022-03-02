package kamil.kus.tripservice.trip;

import kamil.kus.tripservice.exception.UserNotLoggedInException;
import kamil.kus.tripservice.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TripServiceTest {

    private final User notLoggedUser = null;
    private final User loggedUser = new User();
    private final User userWithoutFriends = new User();

    @Test
    public void should_throw_exception_when_user_is_not_logged() {
        TestableService tripService = new TestableService(notLoggedUser);
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(null));
    }

    @Test
    public void should_return_empty_list_when_user_is_not_friend() {
        TestableService tripService = new TestableService(loggedUser);
        final List<Trip> tripsByUser = tripService.getTripsByUser(userWithoutFriends);
        assertThat(tripsByUser.isEmpty()).isTrue();
    }
}

class TestableService extends TripService {
    private final User loggedUser;

    @Override
    protected User getLoggedUser() {
        return loggedUser;
    }

    public TestableService(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
