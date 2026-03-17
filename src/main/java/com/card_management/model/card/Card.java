package com.card_management.model.card;

import com.card_management.model.customer.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "card")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Card {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "card_number")
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;

    //TODO: can be improved to use Lookup tables in db, so here just pass int id
    @Column(name = "card_type")
    private String cardType; //(DEBIT / CREDIT)

    //TODO: can be improved to use Lookup tables in db, so here just pass int id
    @Column(name = "status")
    private String status; //(ACTIVE / BLOCKED / EXPIRED)

    @Column(name = "expiry_time")
    private ZonedDateTime expiryTime;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_time")
    private ZonedDateTime modifiedTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Card card = (Card) o;
        return getId() != null && Objects.equals(getId(), card.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
