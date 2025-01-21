package org.project.market.service.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.market.dto.input.product.CreateProductInput;
import org.project.market.entity.MemberEntity;
import org.project.market.entity.ProductEntity;
import org.project.market.event.model.DeleteFileEvent;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.repository.ProductRepository;
import org.project.market.util.SecurityUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SecurityUtil securityUtil;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long createProduct(CreateProductInput input, List<String> imageUrls) {
        // 트랜잭션 롤백 시 S3에 저장된 파일을 삭제하는 이벤트 발행
        eventPublisher.publishEvent(new DeleteFileEvent(imageUrls));

        // Security Context에서 현재 회원 엔티티 조회
        MemberEntity sellerEntity = securityUtil.getCurrentMember()
            .orElseThrow(() -> new CustomException(ErrorEnum.AUTHORIZATION_FAILED));

        // 상품 엔티티 저장
        ProductEntity productEntity = input.toEntity(sellerEntity, imageUrls);
        productRepository.save(productEntity);

        return productEntity.getId();
    }
}
