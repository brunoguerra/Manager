package com.ajurasz.repository;

import com.ajurasz.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Arek Jurasz
 */
@Repository("itemRepo")
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemByName(String name);
}
