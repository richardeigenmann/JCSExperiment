package jcsexperiment;

import java.io.Serializable;

/**
 *
 * @author Richard Eigenmann
 */
public class CacheableObject implements Serializable {

    private static final long serialVersionUID = 1;
    private final byte[] bytes;

    /**
     * Constructs a new CacheableObject object
     *
     * @param bytes the bytes to remember
     */
    public CacheableObject( byte[] bytes ) {
        this.bytes = bytes;
    }

    /**
     * Returns the bytes from the stored bytes array
     *
     * @return the bytes[]
     */
    public byte[] getBytes() {
        return bytes;
    }

}
