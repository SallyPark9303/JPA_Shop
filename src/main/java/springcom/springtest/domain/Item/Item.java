package springcom.springtest.domain.Item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import springcom.springtest.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속받은 객체의 컬럼을 한 테이블로 생성
@DiscriminatorColumn(name="dtype")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private  int stockQuantity;

   // private List<Category> categories = new ArrayList<>();
    // 도메인 주도 설계 : setter를 사용하는것 보다 비지니스 로직을 사용하는게 응집도가 더 높음
    //== 비지니스 로직==//
    /*
        stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    /*
     stock 감소
    */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock <0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
