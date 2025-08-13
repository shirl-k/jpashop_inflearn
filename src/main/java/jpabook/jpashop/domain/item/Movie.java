package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorColumn(name = "M")
public class Movie extends Item {

    private String director;
    private String actor;
}
