package kamil.kus.tripservice.trip;

import kamil.kus.tripservice.user.User;
import kamil.kus.tripservice.user.UserSession;

import java.util.Optional;

public class UserSessionAdapter {
    public UserSessionAdapter() {
    }

    protected Optional<User> getLoggedUser() {
        return Optional.ofNullable(UserSession.getInstance().getLoggedUser());
    }
}