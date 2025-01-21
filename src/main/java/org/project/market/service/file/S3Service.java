package org.project.market.service.file;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.market.global.enums.FileType;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class S3Service implements FileService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadProductImage(MultipartFile file) {
        // 파일명 형식 : product/UUID_원본파일명
        String fileName = FileType.PRODUCT_IMAGE.getFolder() + UUID.randomUUID() + "_" + file.getOriginalFilename();
        return upload(file, fileName);
    }

    public List<String> uploadProductImages(List<MultipartFile> files) {
        return files.stream().map(this::uploadProductImage).toList();
    }

    private String upload(MultipartFile file, String fileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
        } catch(SdkClientException | IOException e) {
            log.warn(e.getMessage(), e);
            throw new CustomException(ErrorEnum.FILE_UPLOAD_FAILED);
        }

        return fileName;
    }

    public void delete(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
    }

    public void delete(List<String> fileNames) {
        fileNames.forEach(this::delete);
    }
}
