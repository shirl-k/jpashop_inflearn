package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //전체 readOnly
@RequiredArgsConstructor
public class ItemService { //상품 서비스는 상품리파지토리에 단순하게 위임만 해주는 클래스
                            // 콘트롤러에서 바로 리포지토리로 접근하는 방식으로 개발해도 됨

    private final ItemRepository itemRepository;

    @Transactional //readOnly는 저장이 안되므로 @Transactional 해주기 - 우선권 가짐
    public void save(Item item) {
        itemRepository.save(item);
    }
    public List<Item> findItem() {
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);  
    }
}
