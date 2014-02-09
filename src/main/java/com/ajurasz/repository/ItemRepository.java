package com.ajurasz.repository;

import com.ajurasz.model.Company;
import com.ajurasz.model.Item;
import com.ajurasz.model.ItemType;
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
    Item findItemByNameAndCompany(String name, Company company);
    Item findItemByIdAndCompany(Long id, Company company);

    List<Item> findAllByTypeAndCompany(ItemType type, Company company);
    List<Item> findAllByCompany(Company company);
}
