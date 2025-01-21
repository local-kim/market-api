package org.project.market.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.market.dto.input.product.CreateProductInput;
import org.project.market.dto.request.product.CreateProductRequest;
import org.project.market.global.response.CommonResponse;
import org.project.market.mapper.ProductMapper;
import org.project.market.service.file.FileService;
import org.project.market.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Tag(name = "상품 API", description = "상품에 관한 API")
@RequestMapping("/api/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;
    private final ProductMapper productMapper;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "상품 등록 성공"),
        @ApiResponse(responseCode = "400", description = "1. 잘못된 입력 형식 2. 파일 업로드 실패"),
        @ApiResponse(responseCode = "401", description = "토큰 인증 실패"),
    })
    @Operation(summary = "상품 등록", description = "인증된 사용자만 접근 가능. 상품 정보의 data 파트와 상품 이미지의 file 파트로 구분됨")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestPart("data") @Valid CreateProductRequest request,
                                           @RequestPart("file") @Nullable List<MultipartFile> images) {
        // 상품 이미지 업로드
        List<String> imageUrls = null;
        if(images != null && !images.isEmpty()) {
             imageUrls = fileService.uploadProductImages(images);
        }

        // 상품 정보 저장
        CreateProductInput input = productMapper.toInput(request);
        Long productId = productService.createProduct(input, imageUrls);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CommonResponse.success("상품 등록 성공", productId));
    }
}
