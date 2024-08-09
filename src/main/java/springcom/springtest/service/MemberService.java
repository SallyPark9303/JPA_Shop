package springcom.springtest.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcom.springtest.domain.Member;
import springcom.springtest.repository.MemberRepository;

import java.util.List;

@Service
@Transactional// 기본적으로 트랜젝션안에서 데이터 변경하는 것은 트랜젝션이 꼭 있어야함.
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    // 회원 가입
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //Exception 같은 id가 있는지 검증
        List<Member> findMembers = memberRepository.findByMemId(member.getMemId());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원아이디 입니다.");
        }
    }

    //회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    
    // 회원 단건 조회
    @Transactional(readOnly = true)
    public Member findOne(Long Id){
        return memberRepository.findOne(Id);
    }

}
