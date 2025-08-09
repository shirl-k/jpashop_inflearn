package jpabook.jpashop;
//엔티티 찾아주는 Repository DAO와 비슷

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
//회원 저장소
@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;  //엔티티 매니저 스프링부트 -> 스프링 컨테이너 위에서 다 동작 어노테이션 있으면 해당하는거 주입해줌

    public Long save(Member member) {
        em.persist(member); //persist() 메소드: 엔티티를 영속성 컨텍스트에 저장함
        return member.getId();  //커맨드랑 쿼리를 분리 (리턴값을 만들지 않고 id 조회 정도로 설계 해두면 좋음)
    }
    public Member find(Long id) {
        return em.find(Member.class, id);  // id 조회
    }
}
