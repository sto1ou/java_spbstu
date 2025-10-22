package com.example.tasks.repository.notifications.model;

import com.example.tasks.repository.GlobalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@ToString
@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "\"user\"")
    private Long user;
    private Long created;
    private String data;
    @Enumerated(STRING)
    @ColumnTransformer(write = "?::global_status")
    private GlobalStatus status;

    @Override
    public final boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy)o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy)this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        NotificationEntity that = (NotificationEntity)o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy)this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
