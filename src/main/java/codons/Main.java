package codons;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public class Main {

    private static final Map<String, String> codonMap = newCodonMap();
    private static final Map<String, String> mirrorCodon = newMirrorCodonMap();

    public static void main(final String... args) {
        System.out.println("Codon to AminoAcid map:");
        for (final var entry : codonMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nCodon to mirror codon map:");
        for (final var entry : mirrorCodon.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nMirror amino acid");
        for (final var entry : codonMap.entrySet()) {
            final var mirror = mirrorCodon.get(entry.getKey());
            System.out.println(entry.getKey() + ":" + entry.getValue() + " - " + mirror + ":" + codonMap.get(mirror));
        }

    }

    private static Map<String, String> newCodonMap() {
        final var map = new HashMap<String, String>();
        addAminoAcid(map, "Isoleucine", "I", "ATT,ATC,ATA");
        addAminoAcid(map, "Leucine", "L", "CTT,CTC,CTA,CTG,TTA,TTG");
        addAminoAcid(map, "Valine", "V", "GTT,GTC,GTA,GTG");
        addAminoAcid(map, "Phenylalanine", "F", "TTT,TTC");
        addAminoAcid(map, "Methionine", "M", "ATG");
        addAminoAcid(map, "Cysteine", "C", "TGT,TGC");
        addAminoAcid(map, "Alanine", "A", "GCT,GCC,GCA,GCG");
        addAminoAcid(map, "Glycine", "G", "GGT,GGC,GGA,GGG");
        addAminoAcid(map, "Proline", "P", "CCT,CCC,CCA,CCG");
        addAminoAcid(map, "Threonine", "T", "ACT,ACC,ACA,ACG");
        addAminoAcid(map, "Serine", "S", "TCT,TCC,TCA,TCG,AGT,AGC");
        addAminoAcid(map, "Tyrosine", "Y", "TAT,TAC");
        addAminoAcid(map, "Tryptophan", "W", "TGG");
        addAminoAcid(map, "Glutamine", "Q", "CAA,CAG");
        addAminoAcid(map, "Asparagine", "N", "AAT,AAC");
        addAminoAcid(map, "Histidine", "H", "CAT,CAC");
        addAminoAcid(map, "Glutamic acid", "E", "GAA,GAG");
        addAminoAcid(map, "Aspartic acid", "D", "GAT,GAC");
        addAminoAcid(map, "Lysine", "K", "AAA,AAG");
        addAminoAcid(map, "Arginine", "R", "CGT,CGC,CGA,CGG,AGA,AGG");
        addAminoAcid(map, "Stop codon", "Stop", "TAA,TAG,TGA");
        return map;
    }

    private static void addAminoAcid(final Map<String, String> map, final String name,
                                     final String slc, final String codons) {
        for (final var codon : codons.split(",")) {
            map.put(codon, name);
        }
    }

    private static Map<String, String> newMirrorCodonMap() {
        final var map = new HashMap<String, String>();
        for (final var entry : codonMap.entrySet()) {
            map.put(entry.getKey(), toMirror(entry.getKey()));
        }
        return map;
    }

    private static String toMirror(final String codon) {
        return toMirrorNucleotide(codon.charAt(0))
            + toMirrorNucleotide(codon.charAt(1))
            + toMirrorNucleotide(codon.charAt(2));
    }

    private static String toMirrorNucleotide(final char c) {
        return switch (c) {
            case 'A' -> "T";
            case 'C' -> "G";
            case 'G' -> "C";
            case 'T' -> "A";
            default -> throw new IllegalArgumentException("Invalid nucleotide in codon " + c);
        };
    }

}
