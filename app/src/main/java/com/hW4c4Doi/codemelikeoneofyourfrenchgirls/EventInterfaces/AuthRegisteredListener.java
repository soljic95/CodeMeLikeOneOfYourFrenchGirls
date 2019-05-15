package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces;

import com.google.firebase.auth.AuthResult;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;

/**
 * Interface for managing events when user is registered in database
 */
public interface AuthRegisteredListener {

    // Event that is sent when user is successfully  registered on Firebase
    void UserAuthenticatedInFirebase(AuthResult authResult, User user);


}
