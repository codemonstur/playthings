package crypto.impl;

import java.util.Map;

public class DES {

    public static final Map<String, String> sBox = Map.ofEntries(
        Map.entry("000000", "0010"),
        Map.entry("000010", "1100"),
        Map.entry("000100", "0100"),
        Map.entry("000110", "0001"),
        Map.entry("001000", "0111"),
        Map.entry("001010", "1010"),
        Map.entry("001100", "1011"),
        Map.entry("001110", "0110"),
        Map.entry("010000", "1000"),
        Map.entry("010010", "0101"),
        Map.entry("010100", "0011"),
        Map.entry("010110", "1111"),
        Map.entry("011000", "1101"),
        Map.entry("011010", "0000"),
        Map.entry("011100", "1110"),
        Map.entry("011110", "1001")

    );

}
