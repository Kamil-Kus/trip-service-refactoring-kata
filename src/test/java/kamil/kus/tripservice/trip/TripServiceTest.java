package kamil.kus.tripservice.trip;

import kamil.kus.tripservice.exception.UserNotLoggedInException;
import kamil.kus.tripservice.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceTest {
    @Test
    public void should_throw_exception_when_user_is_not_logged() {
        TestableService tripService = new TestableService();
       assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(null));
    }
}

class TestableService extends TripService {

    @Override
    protected User getLoggedUser() {
        return null;
    }

}
