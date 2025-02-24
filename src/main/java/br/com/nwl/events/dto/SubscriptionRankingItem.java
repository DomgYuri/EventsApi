package br.com.nwl.events.dto;

import br.com.nwl.events.model.User;

public record SubscriptionRankingItem(Long subscription, Integer userId, String name) {
}
