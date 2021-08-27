package crypto;

public interface SymmetricBlockCipher extends BlockCipher {

    default byte[] decrypt(byte[] block) {
        return encrypt(block);
    }

}
