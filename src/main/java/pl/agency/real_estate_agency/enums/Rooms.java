package pl.agency.real_estate_agency.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Rooms {
    LIVING_ROOM("Pokój dzienny"),
    BATHROOM("Łazienka"),
    BEDROOM("Sypialnia"),
    KITCHEN("Kuchnia"),
    DINING_ROOM( "Jadalnia"),
    OTHER_ROOM("Inny pokój");

    private final String description;
}