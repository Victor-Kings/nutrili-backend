package com.nutrili.external;

import com.nutrili.external.database.entity.User;

public interface InsertUserInDataBase {
    User execute(User user);
}
