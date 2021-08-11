
package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.SmsToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Transactional
public interface SmsTokenRepository extends JpaRepository<SmsToken,Integer> {
    @Modifying
    @Query("delete from SmsToken pt where :currentTime - pt.createTime >1500")
    void deleteOldTokens(@Param("currentTime")long currentTime);

    SmsToken findByNumberAndCode(@Param("number") String phone, @Param("code") String code);
}
