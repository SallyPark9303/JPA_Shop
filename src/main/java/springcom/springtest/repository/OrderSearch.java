package springcom.springtest.repository;

import lombok.Getter;
import lombok.Setter;
import springcom.springtest.domain.OrderStatus;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
