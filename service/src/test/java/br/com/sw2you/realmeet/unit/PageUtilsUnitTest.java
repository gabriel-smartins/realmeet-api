package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.domain.entity.Allocation.SORTABLE_FIELDS;
import static br.com.sw2you.realmeet.util.PageUtils.newPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.exception.InvalidOrderByFilterException;

import java.util.Collections;
import org.springframework.data.domain.Sort;

import org.junit.jupiter.api.Test;

public class PageUtilsUnitTest extends BaseUnitTest {

    @Test
    void testNewPageableWhenPageIsNullAndLimitIsNullAndOrderByIsNull() {
        var pageable = newPageable(null, null, 10, null, SORTABLE_FIELDS);
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        assertEquals(Sort.unsorted(), pageable.getSort());
    }

    @Test
    void testNewPageableWhenPageIsInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> newPageable(-1, null, 10, null, SORTABLE_FIELDS));
    }

    @Test
    void testNewPageableWhenLimitIsInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> newPageable(null, 0, 10, null, SORTABLE_FIELDS));
    }

    @Test
    void testNewPageableWhenLimitExceedsMaximum() {
        var pageable = newPageable(null, 30, 10, null, SORTABLE_FIELDS);
        assertEquals(10, pageable.getPageSize());
    }

    @Test
    void testNewPageableWhenOrderByIsNull() {
        assertThrows(IllegalArgumentException.class, () -> newPageable(null, null, 10, null, null));
    }

    @Test
    void testNewPageableWhenOrderByIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> newPageable(null, null, 10, null, Collections.emptyList()));
    }

    @Test
    void testNewPageableWhenOrderByAscIsValid() {
        var pageable = newPageable(null, 30, 10, SORTABLE_FIELDS.getFirst(), SORTABLE_FIELDS);

        assertEquals(Sort.by(Sort.Order.asc(SORTABLE_FIELDS.getFirst())), pageable.getSort());
    }

    @Test
    void testNewPageableWhenOrderByDescIsValid() {
        var pageable = newPageable(null, 30, 10, "-" + SORTABLE_FIELDS.getFirst(), SORTABLE_FIELDS);

        assertEquals(Sort.by(Sort.Order.desc(SORTABLE_FIELDS.getFirst())), pageable.getSort());
    }

    @Test
    void testNewPageableWhenOrderByFieldsIsInvalid() {
        assertThrows(InvalidOrderByFilterException.class, () -> newPageable(null, null, 10, "invalid", SORTABLE_FIELDS));
    }
}
