package springcom.springtest.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springcom.springtest.domain.Member;
import springcom.springtest.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    //service, repository 참조
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    

    @Test
    public void 회원_조회() throws Exception{
      List<Member> result =   memberRepository.findAll();
      System.out.println(result.size());
    }

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setMemName("kim");
        member.setMemId("kim123");
        member.setPassword("kim1234");
        //when
        Long savedId =  memberService.join(member);
        assertEquals(member,memberRepository.findOne(savedId));
        //then
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setMemId("test1");
        Member member2 = new Member();
        member2.setMemId("test1s");
        //when
        memberService.join(member1);
        try{
            memberService.join(member2); // 예외가 발생해야 함.
        }catch (IllegalStateException e){
            return;
        }
        //then
        fail("예외가 발생해야 합니다.");
    }
}