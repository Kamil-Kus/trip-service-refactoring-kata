package kamil.kus.tripservice.trip;

import kamil.kus.tripservice.user.User;
import kamil.kus.tripservice.user.UserSession;

public class UserSessionAdapter {
    public UserSessionAdapter() {
    }

    protected User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }
}