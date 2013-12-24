package com.ajurasz.repository;

import com.ajurasz.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Arek Jurasz
 */
@Repository("itemRepo")
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemByName(String name);
}
