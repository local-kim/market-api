package org.project.market.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.project.market.global.entity.BaseEntity;
import org.project.market.global.enums.ProductStatusEnum;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
@Entity
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private MemberEntity sellerEntity;

    // 상품명
    private String name;

    // 설명
    private String description;

    // 가격
    private Long price;

    /**
     * 상태
     * 판매중, 예약중, 완료
     */
    private ProductStatusEnum status;

    // 수량
    private Integer quantity;

    // 재고수량
    private Integer stock;

    /**
     * 이미지 URL
     * JSON Array
     */
    @Type(JsonType.class)
    private List<String> imageUrls;
}
