package domain;

import io.vavr.collection.List;

public enum Estado {
    DESCONOCIDO,
    EN_PROCESO,
    DISPONIBLE;

    public static Estado of (String name) {
        return List.of(Estado.values()).find(t -> t.name().equals(name)).getOrElse(Estado.DESCONOCIDO);
    }
}
