package br.com.nwl.events.exception;

public class SubscriptionConflictException extends RuntimeException {
  public SubscriptionConflictException(String message) {
    super(message);
  }
}
