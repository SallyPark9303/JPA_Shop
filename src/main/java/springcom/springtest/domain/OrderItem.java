package springcom.springtest.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import springcom.springtest.domain.Item.Item;

@Entity
@Getter
@Setter
@Table(name="order_item")
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id")
    private  Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    // 여러군데서 생성 되는것을 막음
    // 직접생성하지 않고 생성 메서드나 다른방법으로 생성해야 한다는 것을 알려줌,
    // 이런식으로 제약하는 스타일로 짜는 것이 좋음 -> 좋은 설계외 유지보수가 가능
    protected  OrderItem(){
        
    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item , int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
    
    //==비지니스 로직==//
    public void cancel(){
        getItem().addStock(count);
    }

    
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
