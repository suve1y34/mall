package com.intea.common;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass //공동 정보
@EntityListeners(AuditingEntityListener.class) //생성시간,수정시간 자동화
public class CommonEntity {
    @CreatedDate
    private LocalDateTime insertTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
