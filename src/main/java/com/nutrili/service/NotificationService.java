package com.nutrili.service;

import com.nutrili.external.DTO.NotificationDTO;
import com.nutrili.external.database.entity.Notification;
import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    public List<NotificationDTO> getNotification(UUID id){
        AtomicInteger index= new AtomicInteger();
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        notificationRepository.findNotification(id).forEach((notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setDateOfNotification(notification.getDateOfNotification().toString());
            notificationDTO.setMessage(notification.getMessage());
            notificationDTO.setSenderName(notification.getSenderName());
            notificationDTO.setId(notification.getId());
            notificationDTO.setStatus(notification.getStatus());
            notificationDTO.setIndex(index.getAndIncrement());
            notificationDTOList.add(notificationDTO);
        }));
        return notificationDTOList;

    }

    public void  updateNotification(UUID notificationId){
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if(notificationOptional.isPresent()){
            Notification notification = notificationOptional.get();
            notification.setStatus(true);
            notificationRepository.save(notification);
        }
    }

}
