package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { //item 은 jpa에 저장히기 전까지 id값이 없음. id 없으면 persist에서 호출해오기
            em.persist(item); //조건 성립 시 em.persist에 item 저장
        }else {
            em.merge(item); //merge : 업데이트랑 비슷함 . id가 있으면 merge 수행해서 강제 업데이트(비슷하지만 다름)
        }
    }
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
