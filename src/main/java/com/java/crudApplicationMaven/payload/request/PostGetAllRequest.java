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
    private int pageNo = PaginationConstant.DEFAULT_PAGINATION_PAGE_NUMBER;
    // row per page
    private int rowPerPage = PaginationConstant.DEFAULT_PAGINATION_ROW_PER_PAGE;
    private int offset = PaginationConstant.DEFAULT_PAGINATION_OFFSET;
    private String sortBy = PaginationConstant.DEFAULT_PAGINATION_SORTBY;
    private String sortType = SortType.DEFAULT_SORT_TYPE.value();

    public int getPageNo() {
        return pageNo = pageNo < PaginationConstant.DEFAULT_PAGINATION_PAGE_NUMBER
                ? PaginationConstant.DEFAULT_PAGINATION_PAGE_NUMBER
                : pageNo;
    }

    public int getRowPerPage() {
        return rowPerPage = rowPerPage < 1 ? PaginationConstant.DEFAULT_PAGINATION_ROW_PER_PAGE : rowPerPage;
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
