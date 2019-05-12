package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;

/**
 * Interface for managing events when user is updated with UID and DocumentReference in firebase
 */
public interface UserUpdatedListener {

    // Event that is sent when user is successfully updated in database with UID and DocumentRef
    void UserUpdatedInFirebase(User user);

}
