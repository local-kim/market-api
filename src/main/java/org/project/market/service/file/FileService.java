package org.project.market.service.file;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadProductImage(MultipartFile file);

    List<String> uploadProductImages(List<MultipartFile> files);

    void delete(String fileName);

    void delete(List<String> fileNames);
}
