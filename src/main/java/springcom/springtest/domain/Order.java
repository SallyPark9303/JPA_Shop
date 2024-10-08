package springcom.springtest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tOrder")
@Getter
@Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id") //맵핑할 컬럼
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne
    @JoinColumn(name="delivery_id") // 일대일 관계에서는 엑세스를 많이 하는 쪽을 연관관계 주인으로 함
    private  Delivery delivery;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private  OrderStatus status; //  주문상태 [ORDER, CANCEL]

    //==연관관계 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    // 생성하는 지점에서 이 메서드만 변경하면됨
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    //==비지니스 로직==//
    /*
    주문 취소
     */
    public void cancel() {
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
    
    //== 조회 로직==//
    /*
        전체 주문 가격 조회
     */
    public int getTotalPrice(){
      /*  int totalPrice =0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();

        }
        return totalPrice;*/
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
