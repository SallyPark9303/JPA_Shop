package springcom.springtest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import springcom.springtest.domain.Member;

import java.util.List;

@Repository  // spring 이 자동으로 bean 관리
public class MemberRepository {

    // spring 이 이 entity manager 를 만들어서 주입해줌
    @PersistenceContext
    private EntityManager em;

    // 사용자 저장
    public void save(Member member){
        em.persist(member); // 저장
    }
    
    // 사용자 조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }
    
    //전체 사용자 조회
    public List<Member> findAll(){
        // qlString : entity 객체에 대한 쿼리
      return em.createQuery("select m from Member m", Member.class)
              .getResultList();
    }
    
    //이름으로 조회
    public List<Member> findByMemId(String memId){
        return em.createQuery("select m from Member m where m.memId = :memId", Member.class)
                .setParameter("memId", memId)
                .getResultList();
    }

}
