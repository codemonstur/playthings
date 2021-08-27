package crypto.padding;

public interface PaddingAlgorithm {

    void padBlock(byte[] block, int offset);

}
