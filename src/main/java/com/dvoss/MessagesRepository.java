package com.dvoss;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dan on 6/21/16.
 */
public interface MessagesRepository extends CrudRepository<Message, Integer> {
}
