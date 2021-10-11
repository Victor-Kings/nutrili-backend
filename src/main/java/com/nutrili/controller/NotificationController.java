package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.database.entity.User;
import com.nutrili.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/getNotification")
    @Secured({RoleConst.ROLE_PATIENT,RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity getNotification() {
        return ResponseEntity.ok(notificationService.getNotification(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }

    @PutMapping("/updateNotification")
    @Secured({RoleConst.ROLE_PATIENT,RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity updateNotification(@NotNull @RequestParam UUID notificationId){
        notificationService.updateNotification(notificationId);
        return ResponseEntity.ok("Notification is no longer a new notification");
    }


}
