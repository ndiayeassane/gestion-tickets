package net.ennov.gestion_ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ennov.gestion_ticket.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
