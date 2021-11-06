package com.nutrili.Utils;

import com.nutrili.external.database.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {

    public static User getContextUser()
    {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
