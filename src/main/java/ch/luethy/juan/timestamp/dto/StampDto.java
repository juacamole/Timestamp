package ch.luethy.juan.timestamp.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StampDto {

    @Size(max = 23)
    private int hour;
    @Size(max = 59)
    private int minute;
    private int userid;
}
