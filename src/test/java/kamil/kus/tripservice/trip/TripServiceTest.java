package kamil.kus.tripservice.trip;

import kamil.kus.tripservice.exception.UserNotLoggedInException;
import kamil.kus.tripservice.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    private static final User LOGGED_USER = new User();
    private static final User USER_WITHOUT_FRIENDS = new User();
    private static final User USER_WITH_FRIENDS = new User();
    private static final List<Trip> TRIP_LIST = new ArrayList<>();
    private static final Trip ITALY = new Trip();

    @Mock
    private DatabaseAdapter databaseAdapter;
    @Mock
    private UserSessionAdapter userSessionAdapter;

    @Test
    public void should_throw_exception_when_user_is_not_logged() {
        Mockito.when(userSessionAdapter.getLoggedUser()).thenReturn(Optional.ofNullable(null));
        //When
        TripService tripService = new TripService(databaseAdapter, userSessionAdapter);
        //Given and Then
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(USER_WITHOUT_FRIENDS));
    }

    @Test
    public void should_return_empty_list_when_user_is_not_friend() {
        Mockito.when(userSessionAdapter.getLoggedUser()).thenReturn(Optional.ofNullable(LOGGED_USER));
        //When
        TripService tripService = new TripService(databaseAdapter, userSessionAdapter);
        //Given
        final List<Trip> tripsByUser = tripService.getTripsByUser(USER_WITHOUT_FRIENDS);
        //Then
        assertThat(tripsByUser.isEmpty()).isTrue();
    }

    @Test
    public void should_return_trip_list_when_user_is_friend() {
        Mockito.when(userSessionAdapter.getLoggedUser()).thenReturn(Optional.ofNullable(LOGGED_USER));
        Mockito.when(databaseAdapter.extractTripsByUser(USER_WITH_FRIENDS)).thenReturn(TRIP_LIST);
        //When
        TRIP_LIST.add(ITALY);
        USER_WITH_FRIENDS.addFriend(LOGGED_USER);
        TripService tripService = new TripService(databaseAdapter, userSessionAdapter);
        //Given
        final List<Trip> tripsByUser = tripService.getTripsByUser(USER_WITH_FRIENDS);
        //Then
        assertThat(tripsByUser.isEmpty()).isFalse();
    }
}