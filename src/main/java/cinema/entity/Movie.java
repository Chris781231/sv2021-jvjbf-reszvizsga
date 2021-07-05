package cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Movie {

    private Long id;
    private String title;
    private LocalDateTime date;
    private int maxSpaces;
    private int freeSpaces;

    public Movie(Long id, String title, LocalDateTime date, int maxSpaces) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.maxSpaces = maxSpaces;
        this.freeSpaces = maxSpaces;
    }

    public void reserveSpace(int spaces) {
        if (spaces > freeSpaces) {
            throw new IllegalStateException();
        }
        freeSpaces -= spaces;
    }
}
