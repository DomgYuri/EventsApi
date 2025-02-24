package br.com.nwl.events.repository;

import br.com.nwl.events.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

    public Event findByPrettyName (String prettyName);

}
