package com.java.crudApplicationMaven.service.listPagination;

import com.java.crudApplicationMaven.constant.enumeration.SortType;
import com.java.crudApplicationMaven.payload.request.ListPaginationRequest;
import com.java.crudApplicationMaven.payload.response.ListPaginationResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Service
@RequestScope
public abstract class BaseListPagination<T> {

    @SuppressWarnings("unchecked")
    protected ListPaginationResponse<T> getListPaginationResponse(
            Class<T> tClass, ListPaginationRequest listPagingRequest) {

        String originalSortBy = listPagingRequest.getSortBy();
        String originalSortType = listPagingRequest.getSortType();

        boolean isDefaultSortBy = !StringUtils.hasText(listPagingRequest.getSortBy());
        boolean isDefaultSortType = !StringUtils.hasText(listPagingRequest.getSortType());

        ListPaginationResponse<T> listPagingResponse = new ListPaginationResponse<T>();
        listPagingResponse.setPageNo(listPagingRequest.getPageNo());
        listPagingResponse.setRowPerPage(listPagingRequest.getRowPerPage());
        listPagingResponse.setSortBy(listPagingRequest.getSortBy());
        listPagingResponse.setSortType(SortType.of(listPagingRequest.getSortType()));
        // listPagingResponse.setRowTotal(getCountAllData(listPagingRequest.getKeyword()));

        listPagingResponse.setList(getListPaginationData(listPagingRequest));

        /*
         * listPagingResponse.setList(
         * isDefaultSortBy?
         * (List<T>)
         * BaseItemList.sortByUpToDateList(getListPaginationData(listPagingRequest),
         * tClass) :
         * getListPaginationData(listPagingRequest)
         * );
         */

        // Back to the original data request sortby
        listPagingResponse.setSortBy(isDefaultSortBy ? originalSortBy : listPagingRequest.getSortBy());

        // Back to the original data request sortType
        listPagingResponse.setSortType(
                SortType.of(isDefaultSortType ? originalSortType : listPagingRequest.getSortType()));

        return listPagingResponse;

        // return getListPaginationResponse(listPagingResponse);
    }

    private ListPaginationResponse<T> getListPaginationResponse(ListPaginationResponse<T> listPagingResponse) {

        List<T> listAllData = getAllListData(
                listPagingResponse.getSortBy(), listPagingResponse.getSortType());

        List<T> listDataFiltered = null;

        if (listAllData != null && !listAllData.isEmpty()) {

            int allDataRowTotal = listPagingResponse.getRowTotal();
            int pageTotal = listPagingResponse.getPageTotal();
            int rowPerPage = listPagingResponse.getRowPerPage();
            int pageNumber = listPagingResponse.getPageNo();

            int totalRowInLastPage = allDataRowTotal % rowPerPage;
            int totalPageFullRow = pageTotal - (totalRowInLastPage > 0 ? 1 : 0);
            int startIndexInLastPage = totalPageFullRow * rowPerPage;

            if (pageNumber > 0 && pageNumber <= totalPageFullRow)

                for (int page = 1, indexRow = 0;

                        page <= totalPageFullRow && indexRow < startIndexInLastPage;

                        page++, indexRow++

                        )
                    if (pageNumber == page) {

                        for (int row = 1; row <= rowPerPage; row++, indexRow++) {

                            if (listDataFiltered == null)
                                listDataFiltered = new ArrayList<T>();

                            listDataFiltered.add(listAllData.get(indexRow));
                        }

                        break;
                    }

                    else // noinspection ConstantConditions
                    if (totalRowInLastPage > 0 && pageNumber == totalPageFullRow + 1) {

                        startIndexInLastPage = totalPageFullRow * rowPerPage;

                        for (indexRow = startIndexInLastPage; indexRow <= totalRowInLastPage; indexRow++) {

                            if (listDataFiltered == null)
                                listDataFiltered = new ArrayList<T>();

                            listDataFiltered.add(listAllData.get(indexRow));
                        }
                    }
        }

        listPagingResponse.setList(listDataFiltered);

        return listPagingResponse;
    }

    public abstract Integer getCountAllData(String keyword);

    public abstract List<T> getListPaginationData(ListPaginationRequest listPaginationRequest);

    public abstract List<T> getAllListData(String sortBy, SortType sortType);
}
