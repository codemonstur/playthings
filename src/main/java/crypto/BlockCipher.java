package crypto;

public interface BlockCipher {

    byte[] encrypt(byte[] block);
    byte[] decrypt(byte[] block);

    int blockSize();

}
