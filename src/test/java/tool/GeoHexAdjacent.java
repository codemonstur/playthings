package tool;

import org.geohex.geohex4j.GeoHex;

public class GeoHexAdjacent {

    public static void main(final String... args) {
        final var zone = GeoHex.getZoneByCode("XM488586");
        printZoneForXY(zone.x+1, zone.y, zone.level);
        printZoneForXY(zone.x+1, zone.y+1, zone.level);
        printZoneForXY(zone.x, zone.y+1, zone.level);
        printZoneForXY(zone.x, zone.y-1, zone.level);
        printZoneForXY(zone.x-1, zone.y, zone.level);
        printZoneForXY(zone.x-1, zone.y-1, zone.level);
    }

    private static void printZoneForXY(final long x, final long y, final int level) {
        GeoHex.Zone zoneByXY = GeoHex.getZoneByXY(x, y, level);
        System.out.println(zoneByXY.code);
    }

}
