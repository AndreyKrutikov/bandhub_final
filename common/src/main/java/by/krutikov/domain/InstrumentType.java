package by.krutikov.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstrumentType {
    VOCALS("VOCALS", 1),
    GUITAR("GUITAR", 2),
    BASS("BASS", 3),
    DRUMS("DRUMS", 4),
    SYNTH("SYNTH", 5);

    private final String name;
    private final Integer id;

    public static InstrumentType valueOf(int id) {
        for (InstrumentType type : InstrumentType.values()) {
            if (type.id == id) return type;
        }
        throw new IllegalArgumentException(String.format("no instrument by id %d found: ", id)); //return a default value?
    }
}
