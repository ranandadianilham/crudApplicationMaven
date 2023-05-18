package com.java.crudApplicationMaven.payload.response;

import com.java.crudApplicationMaven.constant.ListPaginationConstant;
import com.java.crudApplicationMaven.constant.enumeration.SortType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPaginationResponse<T> {

    @Builder
    private static class AttributePagination {
        private int pageNo;
        private int rowPerPage;
        private int pageTotal;
        private int rowTotal;
        private String sortBy;
        private SortType sortType;
    }

    private int pageNo = ListPaginationConstant.DEFAULT_PAGINATION_PAGE_NUMBER;
    private int rowPerPage = ListPaginationConstant.DEFAULT_PAGINATION_ROW_PER_PAGE;
    private int pageTotal;
    private int rowTotal;
    private String sortBy = ListPaginationConstant.DEFAULT_PAGINATION_SORTBY;
    private SortType sortType = SortType.DEFAULT_SORT_TYPE;
    private List<T> list;

    public int getPageTotal() {
        return pageTotal = (getRowTotal() / getRowPerPage()) + ((getRowTotal() % getRowPerPage() > 0) ? 1 : 0);
    }

    // build atribute
    @JsonIgnore
    public AttributePagination getAttributes() {
        return new AttributePagination.AttributePaginationBuilder()
                .pageNo(pageNo)
                .rowPerPage(rowPerPage)
                .pageTotal(pageTotal)
                .rowTotal(rowTotal)
                .sortBy(sortBy)
                .sortType(sortType)
                .build();
    }

    @JsonIgnore
    public void setAttributes(AttributePagination attributes) {
        pageNo = attributes.pageNo;
        rowPerPage = attributes.rowPerPage;
        pageTotal = attributes.pageTotal;
        rowTotal = attributes.rowTotal;
        sortBy = attributes.sortBy;
        sortType = attributes.sortType;
    }
}
