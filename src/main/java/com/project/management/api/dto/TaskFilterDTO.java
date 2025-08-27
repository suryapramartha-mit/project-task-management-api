package com.project.management.api.dto;

import com.project.management.api.enums.SortDirectionEnum;
import com.project.management.api.enums.TaskSortEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskFilterDTO {
    private Long projectId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private int page = 0;
    private int size = 10;

    private SortDirectionEnum sort = SortDirectionEnum.ASC;
    private TaskSortEnum sortBy = TaskSortEnum.PRIORITY;

    public SortDirectionEnum getSort() {
        return sort == null ? SortDirectionEnum.ASC : sort;
    }
    public TaskSortEnum getSortBy() {
        return sortBy == null ? TaskSortEnum.PRIORITY : sortBy;
    }
}

