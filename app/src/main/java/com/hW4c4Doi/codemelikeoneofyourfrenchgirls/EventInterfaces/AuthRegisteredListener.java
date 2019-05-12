package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;

public interface AuthRegisteredListener {

    void UserAuthenticatedInFirebase(AuthResult authResult, User user);

    void UserUpdatedInFirebase(User user);
}
