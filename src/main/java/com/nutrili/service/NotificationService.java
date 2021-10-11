package com.nutrili.service;

import com.nutrili.external.database.entity.Notification;
import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    public void createNotification(User user, String name,String message){
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setReceiverUser(user);
        notification.setDateOfNotification(new Date());
        notification.setSenderName(name);
        notification.setStatus(false);
        notificationRepository.save(notification);

    }

    public List<Notification> getNotification(UUID id){
        return notificationRepository.findNotification(id);

    }

    public void  updateNotification(int notificationId){
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if(notificationOptional.isPresent()){
            Notification notification = notificationOptional.get();
            notification.setStatus(true);
            notificationRepository.save(notification);
        }
    }

}
