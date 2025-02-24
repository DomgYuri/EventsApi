package br.com.nwl.events.repository;

import br.com.nwl.events.dto.SubscriptionRankingItem;
import br.com.nwl.events.model.Event;
import br.com.nwl.events.model.Subscription;
import br.com.nwl.events.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    public Subscription findByEventAndSubscriber (Event event, User user);

    @Query(value = "select count(subscription_number) as quantidade, indication_user_id, user_name" +
            "             from db_events.tbl_subscription inner join db_events.tbl_user" +
            "             on tbl_subscription.indication_user_id = tbl_user.user_id " +
            "             where indication_user_id is not null" +
            "                and event_id = :eventId" +
            "             group by indication_user_id" +
            "             order by quantidade desc;", nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking (@Param("eventId") Long eventId);
}
