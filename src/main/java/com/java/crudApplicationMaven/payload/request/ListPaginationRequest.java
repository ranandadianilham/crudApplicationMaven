package com.java.crudApplicationMaven.payload.request;

import java.util.List;

import com.java.crudApplicationMaven.constant.ListPaginationConstant;
import com.java.crudApplicationMaven.constant.enumeration.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPaginationRequest {

    // current page's number
    private int pageNo = ListPaginationConstant.DEFAULT_PAGINATION_PAGE_NUMBER;
    // row per page
    private int rowPerPage = ListPaginationConstant.DEFAULT_PAGINATION_ROW_PER_PAGE;
    private int offset = ListPaginationConstant.DEFAULT_PAGINATION_OFFSET;
    private String sortBy = ListPaginationConstant.DEFAULT_PAGINATION_SORTBY;
    private String sortType = SortType.DEFAULT_SORT_TYPE.value();

    public int getPageNo() {
        return pageNo = pageNo < ListPaginationConstant.DEFAULT_PAGINATION_PAGE_NUMBER
                ? ListPaginationConstant.DEFAULT_PAGINATION_PAGE_NUMBER
                : pageNo;
    }

    public int getRowPerPage() {
        return rowPerPage = rowPerPage < 1 ? ListPaginationConstant.DEFAULT_PAGINATION_ROW_PER_PAGE : rowPerPage;
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
