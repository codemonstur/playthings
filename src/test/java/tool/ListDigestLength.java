package tool;

import fr.cryptohash.Digest;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;

public enum ListDigestLength {;

    public static void main(final String... args) {
        for (final var clas : listClassesInPackage("fr.cryptohash")) {
            if (isRealDigest(clas)) try {
                Digest digest = newInstance(clas);
                if (digest.getDigestLength() == 32)
                    System.out.println(digest.getClass().getSimpleName());
            } catch (Exception ignored) {}
        }
    }

    private static List<Class<?>> listClassesInPackage(final String packageName) {
        try (final var scanResult = new ClassGraph().acceptPackages(packageName).scan()) {
            return scanResult.getAllClasses().loadClasses();
        }
    }

    private static boolean isRealDigest(final Class<?> clas) {
        return clas != null && Digest.class.isAssignableFrom(clas)
            && !clas.isInterface() && !Modifier.isAbstract(clas.getModifiers());
    }

    private static void printDigestLength(final Digest digest) {
        System.out.println(digest.getClass().getSimpleName() + ": " + digest.getDigestLength());
    }

    private static Digest newInstance(final Class<?> clas)
            throws NoSuchMethodException, InvocationTargetException,
                InstantiationException, IllegalAccessException {
        return (Digest) clas.getDeclaredConstructor().newInstance();
    }

}
