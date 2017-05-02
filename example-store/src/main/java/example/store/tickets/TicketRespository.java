package example.store.tickets;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by shiyoon on 5/2/17.
 */

@RepositoryRestResource(collectionResourceRel = "tickets", path = "tickets")
public interface TicketRespository extends PagingAndSortingRepository<Ticket, Long> {
}
