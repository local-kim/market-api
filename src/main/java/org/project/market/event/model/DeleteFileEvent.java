package org.project.market.event.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteFileEvent {

    private List<String> fileNames;
}
