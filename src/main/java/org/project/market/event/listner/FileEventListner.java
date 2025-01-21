package org.project.market.event.listner;

import lombok.RequiredArgsConstructor;
import org.project.market.event.model.DeleteFileEvent;
import org.project.market.service.file.FileService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class FileEventListner {

    private final FileService fileService;

    /**
     * 트랜잭션이 실패하면 비동기적으로 파일 삭제 이벤트를 처리합니다.
     *
     * @param event 파일을 삭제하는 이벤트
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleDeleteFileEvent(DeleteFileEvent event) {
        fileService.delete(event.getFileNames());
    }
}
