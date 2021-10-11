package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Notification;
import com.nutrili.external.database.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification,UUID> {
    @Query("select n from Notification n where n.receiverUser.id =:userId")
    List<Notification> findNotification (@Param("userId") UUID userId);
}
