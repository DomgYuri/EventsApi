package br.com.nwl.events.repository;

import br.com.nwl.events.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByEmail (String email);

}
