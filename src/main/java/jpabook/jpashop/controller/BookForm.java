package jpabook.jpashop.controller;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;
    private String name;      //추적할 수 있는 이유: ItemController 의 빈 new bookForm() 폼을 넘겨줬기 때문에 가능!
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}
