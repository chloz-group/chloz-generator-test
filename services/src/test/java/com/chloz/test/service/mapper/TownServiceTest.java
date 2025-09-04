package com.chloz.test.service.mapper;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.dataaccess.TownDataAccess;
import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.domain.Town;
import com.chloz.test.service.AssertionUtils;
import com.chloz.test.service.Constants;
import com.chloz.test.service.TownService;
import com.chloz.test.service.dto.TownDto;
import com.chloz.test.service.impl.TownServiceImpl;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TownServiceTest {

    @Mock
    private TownDataAccess dataAccess;

    @Mock
    private TownMapper mapper;

    private TownService service;

    @BeforeEach
    void setUp() {
        service = new TownServiceImpl(dataAccess, mapper);
    }

    @Test
    void create_shouldMapSaveAndReturnDto() {
        TownDto input = dto(1L, "Yaoundé");
        Town modelBeforeSave = town(null, "Yaoundé");
        Town saved = town(1L, "Yaoundé");
        TownDto expected = dto(1L, "Yaoundé");

        when(mapper.modelFromDto(input)).thenReturn(modelBeforeSave);
        when(dataAccess.save(modelBeforeSave)).thenReturn(saved);
        when(mapper.mapToDto(saved, "*")).thenReturn(expected);

        TownDto result = service.create(input, "*");

        AssertionUtils.assertObjectEquals(result, expected);
        verify(mapper).modelFromDto(input);
        verify(dataAccess).save(modelBeforeSave);
        verify(mapper).mapToDto(saved, "*");
    }

    @Test
    void bulkCreate_shouldSaveAllAndReturnDtosInOrder() {
        List<TownDto> input = List.of(dto(null, "A"), dto(null, "B"));
        Town a = town(null, "A");
        Town b = town(null, "B");
        Town aSaved = town(10L, "A");
        Town bSaved = town(11L, "B");
        when(mapper.modelFromDto(input.get(0))).thenReturn(a);
        when(mapper.modelFromDto(input.get(1))).thenReturn(b);
        when(dataAccess.saveAll(List.of(a, b))).thenReturn(List.of(aSaved, bSaved));
        when(mapper.mapToDto(aSaved, "g")).thenReturn(dto(10L, "A"));
        when(mapper.mapToDto(bSaved, "g")).thenReturn(dto(11L, "B"));

        List<TownDto> res = service.bulkCreate(input, "g");

        assertThat(res).extracting(TownDto::getId).containsExactly(10L, 11L);
    }

    @Test
    void update_shouldThrowWhenIdNullOrNotFound() {
        TownDto input = dto(null, "X");
        // null id
        assertThatThrownBy(() -> service.update(input, "*"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);

        // id not found
        TownDto withId = dto(99L, "X");
        when(dataAccess.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(withId, "*"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
    }

    @Test
    void update_shouldSaveAndReturnMappedDtoWhenExists() {
        TownDto input = dto(1L, "Douala");
        when(dataAccess.findById(1L)).thenReturn(Optional.of(town(1L, "Old")));
        Town modelFromDto = town(1L, "Douala");
        Town saved = town(1L, "Douala");
        TownDto expected = dto(1L, "Douala");
        when(mapper.modelFromDto(input)).thenReturn(modelFromDto);
        when(dataAccess.save(modelFromDto)).thenReturn(saved);
        when(mapper.mapToDto(saved, "*")).thenReturn(expected);

        TownDto res = service.update(input, "*");
        AssertionUtils.assertObjectEquals(res, expected);
    }

    @Test
    void partialUpdate_shouldThrowWhenNotFound() {
        TownDto patch = dto(7L, null);
        when(dataAccess.findById(7L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.partialUpdate(patch, "*"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
    }

    @Test
    void partialUpdate_shouldPatchSaveAndMap() {
        Town existing = town(7L, "Old");
        TownDto patch = dto(7L, "New");
        when(dataAccess.findById(7L)).thenReturn(Optional.of(existing));
        when(dataAccess.save(existing)).thenReturn(existing);
        when(mapper.mapToDto(existing, "*")).thenReturn(dto(7L, "New"));

        TownDto res = service.partialUpdate(patch, "*");
        assertThat(res.getId()).isEqualTo(7L);
        verify(mapper).partialUpdate(existing, patch);
    }

    @Test
    void bulkUpdate_shouldValidateEachDtoAndThrowOnMissingAny() {
        TownDto ok = dto(1L, "A");
        TownDto missing = dto(2L, "B");
        when(dataAccess.findById(1L)).thenReturn(Optional.of(town(1L, "A")));
        when(dataAccess.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.bulkUpdate(List.of(ok, missing), "*"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
    }

    @Test
    void bulkUpdate_shouldSaveAllWhenAllExist() {
        TownDto a = dto(1L, "A");
        TownDto b = dto(2L, "B");
        Town am = town(1L, "A");
        Town bm = town(2L, "B");
        when(dataAccess.findById(1L)).thenReturn(Optional.of(am));
        when(dataAccess.findById(2L)).thenReturn(Optional.of(bm));
        when(mapper.modelFromDto(a)).thenReturn(am);
        when(mapper.modelFromDto(b)).thenReturn(bm);
        when(dataAccess.saveAll(List.of(am, bm))).thenReturn(List.of(am, bm));
        when(mapper.mapToDto(am, "g")).thenReturn(a);
        when(mapper.mapToDto(bm, "g")).thenReturn(b);

        List<TownDto> res = service.bulkUpdate(List.of(a, b), "g");
        assertThat(res).containsExactly(a, b);
    }

    @Test
    void getAllPaginate_shouldMapEachEntity() {
        Pageable pageable = PageRequest.of(0, 2);
        Town t1 = town(1L, "A");
        Town t2 = town(2L, "B");
        when(dataAccess.findAll("*", pageable)).thenReturn(new PageImpl<>(List.of(t1, t2), pageable, 2));
        when(mapper.mapToDto(t1, "*")).thenReturn(dto(1L, "A"));
        when(mapper.mapToDto(t2, "*")).thenReturn(dto(2L, "B"));

        Page<TownDto> page = service.getAllPaginate(pageable, "*");

        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent()).extracting(TownDto::getId).containsExactly(1L, 2L);
    }

    @Test
    void getById_shouldReturnMappedWhenPresent_elseNull() {
        when(dataAccess.findById(5L, "*")).thenReturn(Optional.of(town(5L, "X")));
        when(mapper.mapToDto(any(Town.class), eq("*"))).thenReturn(dto(5L, "X"));
        assertThat(service.getById(5L, "*")).isNotNull();

        when(dataAccess.findById(6L, "*")).thenReturn(Optional.empty());
        assertThat(service.getById(6L, "*")).isNull();
    }

    @Test
    void enableDisable_delete_deleteAll_shouldDelegateToDataAccess() {
        service.updateEnableStatus(List.of(1L, 2L), true);
        verify(dataAccess).updateEnableStatus(List.of(1L, 2L), true);

        service.deleteById(10L);
        verify(dataAccess).deleteById(10L);

        service.deleteAllById(List.of(7L, 8L));
        verify(dataAccess).deleteAllById(List.of(7L, 8L));
    }

    @Test
    @SuppressWarnings("unchecked")
    void getPageByFilter_shouldMapResultsWhenSupported() {
        Pageable p = PageRequest.of(0, 2);
        // The mock implements FilterDomainDataAccess via extraInterfaces in setUp()
        FilterDomainDataAccess<Town, Long, SimpleTownFilter> fda = (FilterDomainDataAccess<Town, Long, SimpleTownFilter>) dataAccess;
        Town t1 = town(1L, "A");
        Town t2 = town(2L, "B");
        when(fda.findByFilter(any(SimpleTownFilter.class), eq(p), eq("g")))
                .thenReturn(new PageImpl<>(List.of(t1, t2), p, 2));
        when(mapper.mapToDto(t1, "g")).thenReturn(dto(1L, "A"));
        when(mapper.mapToDto(t2, "g")).thenReturn(dto(2L, "B"));

        Page<TownDto> page = service.getPageByFilter(new SimpleTownFilter(), p, "g");
        assertThat(page.getTotalElements()).isEqualTo(2);
    }

    // ---------- helpers ----------
    private static Town town(Long id, String name) {
        Town t = new Town();
        t.setId(id);
        t.setName(name);
        return t;
    }

    private static TownDto dto(Long id, String name) {
        TownDto d = new TownDto();
        d.setId(id);
        d.setName(name);
        return d;
    }
}
