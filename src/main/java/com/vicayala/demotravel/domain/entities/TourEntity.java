package com.vicayala.demotravel.domain.entities;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "tour")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<ReservationEntity> reservations;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<TicketEntity> tickets;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

    @PrePersist
    @PreRemove
    public void updateFk(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }
    public void addTicket(TicketEntity ticket){
        if(Objects.isNull(this.tickets)){
            this.tickets = new HashSet<>();
        }
        this.tickets.add(ticket);
        this.tickets.forEach(ticketEntity -> ticketEntity.setTour(this));
    }

    public void removeTicket(UUID id){
        this.tickets.forEach( ticket ->{
            if(ticket.getId().equals(id)){
                ticket.setTour(null);
            }
        });
    }

    public void updateTickets(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

    public void addReservation(ReservationEntity reservation){
        if(Objects.isNull(this.reservations)){
            this.reservations = new HashSet<>();
        }
        this.reservations.add(reservation);
        this.reservations.forEach(reservationEntity -> reservationEntity.setTour(this));
    }

    public void removeReservation(UUID id){
        this.reservations.forEach(reservation ->{
            if(reservation.getId().equals(id)){
                reservation.setTour(null);
            }
        });
    }

    public void updateReservation(){
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }
}
