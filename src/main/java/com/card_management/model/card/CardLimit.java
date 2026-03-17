package com.card_management.model.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "card_limit")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class CardLimit {

    @Id
    @Column(name = "card_id")
    private UUID cardId;

    @Column(name = "daily_limit")
    private Integer dailyLimit;

    //TODO: can be improved to use Lookup tables in db, so here just pass int id
    @Column(name = "daily_limit_currency")
    private String dailyLimitCurrency;

    @Column(name = "used_today")
    private Integer usedToday;

    @Column(name = "last_reset_time")
    private ZonedDateTime lastResetTime;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CardLimit cardLimit = (CardLimit) o;
        return getCardId() != null && Objects.equals(getCardId(), cardLimit.getCardId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
