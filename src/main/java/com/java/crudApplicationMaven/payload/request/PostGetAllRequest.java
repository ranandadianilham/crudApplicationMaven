package com.java.crudApplicationMaven.payload.request;

import com.java.crudApplicationMaven.constant.PaginationConstant;
import com.java.crudApplicationMaven.constant.enumeration.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostGetAllRequest {

    // current page's number
    // start from 0
    private int pageNo;
    // row per page
    private int rowPerPage;
    private int offset = PaginationConstant.DEFAULT_PAGINATION_OFFSET;
    private String sortBy = PaginationConstant.DEFAULT_PAGINATION_SORTBY;
    private String sortType = SortType.DEFAULT_SORT_TYPE.value();

    public int getPageNo() {
        return pageNo;
    }

    public int getRowPerPage() {
        return rowPerPage;
    }

    public String getSortBy() {
        return sortBy;
    }

    public int getOffset() {
        return offset = (getRowPerPage() * getPageNo()) - getRowPerPage();
    }

    public String getSortType() {
        return sortType = StringUtils.hasText(sortType) ? SortType.of(sortType).value()
                : SortType.DEFAULT_SORT_TYPE.value();
    }

}
