package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.MapperUtils.roomMapper;
import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.mapper.RoomMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoomMapperUnitTest {

    private RoomMapper victim;

    @BeforeEach
    void setupEach() {
        victim = roomMapper();
    }

    @Test
    void testFromEntityToDTO() {
        var room = newRoomBuilder().id(DEFAULT_ROOM_ID).build();
        var dto = victim.fromEntityToDTO(room);

        assertEquals(room.getId(), dto.getId());
        assertEquals(room.getName() , dto.getName());
        assertEquals(room.getSeats() , dto.getSeats());
    }
}
