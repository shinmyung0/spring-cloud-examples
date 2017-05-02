package example.store.tickets;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by shiyoon on 5/2/17.
 */
@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor(force = true)
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    private final String name;
    private final Long eventId;
    private final BigDecimal price;
    private final TicketStatus status;

}
