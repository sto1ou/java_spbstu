package com.example.tasks.repository.tasks.model;

import com.example.tasks.repository.GlobalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long target;
    private Long created;
    @Column(name = "\"user\"")
    private Long user;
    @Enumerated(STRING)
    @ColumnTransformer(write = "?::global_status")
    private GlobalStatus status;

    public TaskEntity(final Long id, final String name, final Long target, final Long created, final Long user,
                      final String status) {

        this.id = id;
        this.name = name;
        this.target = target;
        this.created = created;
        this.user = user;
        this.status = GlobalStatus.getValue(status);
    }

    @Override
    public final boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy)o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy)this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TaskEntity that = (TaskEntity)o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy)this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
