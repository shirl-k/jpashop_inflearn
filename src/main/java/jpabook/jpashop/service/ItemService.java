package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Book;
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
    public void saveItem(Item item) {
        itemRepository.save(item);
    }
    //준영속 엔티티를 수정하는 2가지 방법 (새로운 객체긴 하지만 식별자가 db에 정확하게 있어서 jpa가 관리 안함. 값을 변경해도 db에 변경사항이 업데이트되지 않음)
    //1. 변경 감지 기능 사용 (merge말고 변경 감지 기능 권장)
    @Transactional
    public void updateItem(Long itemId, int price,String name, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        //findItem.change(price,name,stockQuantity); set보다 의미있는 메서드로 역추적할 수 있도록 설계하는 것이 좋음)

        //아무것도 호출할 필요가 없음. findItem으로 찾아온 것들 영속상태. 트랜잭션에 의해 커밋되고, flush됨.  바뀐 값을 업데이트함
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);  
    }



}
