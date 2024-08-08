package com.macro.mall;

import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.service.PmsProductService;
import com.macro.mall.service.impl.PmsProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PmsProductServiceImplTests {

    @Mock
    private PmsProductService pmsProductService;

    @InjectMocks
    private PmsProductServiceImpl pmsProductServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        PmsProductParam productParam = new PmsProductParam();
        when(pmsProductService.create(productParam)).thenReturn(1);

        int result = pmsProductServiceImpl.create(productParam);
        assertEquals(1, result);

        verify(pmsProductService, times(1)).create(productParam);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        PmsProductParam productParam = new PmsProductParam();
        when(pmsProductService.update(id, productParam)).thenReturn(1);

        int result = pmsProductServiceImpl.update(id, productParam);
        assertEquals(1, result);

        verify(pmsProductService, times(1)).update(id, productParam);
    }

    @Test
    public void testUpdateDeleteStatus() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        when(pmsProductService.updateDeleteStatus(ids, 1)).thenReturn(3);

        int result = pmsProductServiceImpl.updateDeleteStatus(ids, 1);
        assertEquals(3, result);

        verify(pmsProductService, times(1)).updateDeleteStatus(ids, 1);
    }
}
