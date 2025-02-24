package br.com.nwl.events.service;

import br.com.nwl.events.dto.SubscriptionRankingByUser;
import br.com.nwl.events.dto.SubscriptionRankingItem;
import br.com.nwl.events.dto.SubscriptionResponse;
import br.com.nwl.events.exception.EventNotFound;
import br.com.nwl.events.exception.SubscriptionConflictException;
import br.com.nwl.events.exception.UserIndicatorNotFound;
import br.com.nwl.events.model.Event;
import br.com.nwl.events.model.Subscription;
import br.com.nwl.events.model.User;
import br.com.nwl.events.repository.EventRepository;
import br.com.nwl.events.repository.SubscriptionRepository;
import br.com.nwl.events.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription (String prettyName, User user, Long userId) {
        Event event = eventRepository.findByPrettyName(prettyName);
        if (event == null) {
            throw new EventNotFound("Evento " + prettyName + " Não Existe!");
        }

        User recoveredUser = userRepository.findByEmail(user.getEmail());
        if (recoveredUser == null) {
            recoveredUser = userRepository.save(user);
        }

        User indication = null;
        if (userId != null) {
            indication = userRepository.findById(userId).orElse(null);
            if (indication == null) throw new UserIndicatorNotFound("Id de usuario " + userId+ " de indicação não existe!");
        }

        Subscription sub =  new Subscription();
        sub.setEvent(event);
        sub.setSubscriber(recoveredUser);
        sub.setIndication(indication);

        Subscription tempSubscription = subscriptionRepository.findByEventAndSubscriber(event, recoveredUser);
        if (tempSubscription != null) {
            throw new SubscriptionConflictException("Usuario " + recoveredUser.getName() + " ja cadastrado no Evento");
        }

        Subscription response = subscriptionRepository.save(sub);

        return new SubscriptionResponse(response.getSubscriptionId(), "https://devstage.com/"
                +response.getEvent().getPrettyName()+"/"+response.getSubscriber().getId());
    }

    public List<SubscriptionRankingItem> generateRanking (String prettyName) {
        Event event = eventRepository.findByPrettyName(prettyName);
        if (event == null) throw new EventNotFound("Ranking de inscrições do evento "+ event.getTitle() +" não existe");
        return subscriptionRepository.generateRanking(event.getId());
    }

    public SubscriptionRankingByUser getRankingByUser (String prettyName, Integer userId) {
        List<SubscriptionRankingItem> ranking = generateRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream()
                .filter(i -> i.userId().equals(userId))
                .findFirst().orElse(null);
        if (item == null) throw new UserIndicatorNotFound("Nao há indicações com o id " + userId);

        Integer position = IntStream.range(0, ranking.size())
                .filter(pos -> ranking.get(pos).userId().equals(userId))
                .findFirst().getAsInt();

        return new SubscriptionRankingByUser(item, position+1);
    }

}
