package com.java.crudApplicationMaven.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@SuppressWarnings("all")
class PostServiceTest {

    @Mock
    private List<String> mockList;

    @Test
    void TestList() {
        // actual array list
        List<String> mockedList = new ArrayList<>();
        // actually add value into
        mockedList.add("one");

        assertEquals(1, mockedList.size());
        assertEquals("one", mockedList.get(0));
    }

    @Test
    void TestListMock() {
        // when(mockList).add(anyString());
        // Mocking array list
        List mockList = mock(ArrayList.class);
        // Mocking add
        mockList.add("one");
        mockList.add("three");
        mockList.size();
        // mockList.add("two");

        // checking if certain behavior is occured, for example .add("one") function
        // verify add mock
        verify(mockList).add("three");

        // verify need Mock
        // verify(mockedList).add("one");
        // check if value is equal?
        assertEquals(0, mockList.size());
    }

}