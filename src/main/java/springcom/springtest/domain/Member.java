package springcom.springtest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="tMember")
public class Member {
    @Id @GeneratedValue
    @Column(name ="member_id")
    private Long id;
    private String memId;
    private String memName;
    private String password;


    @OneToMany
    @JoinColumn(name="member")
    private List<Order> orders = new ArrayList<>();


}
