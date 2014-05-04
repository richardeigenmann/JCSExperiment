package jcsexperiment;

import java.util.logging.Logger;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;

/**
 *
 * @author Richard Eigenmann
 */
public class JcsCache {

    /**
     * Defines a logger for this class
     */
    private static final Logger LOGGER = Logger.getLogger( JcsCache.class.getName() );

    private static final String cacheRegionName = "highresCache";

    private CacheAccess<String, CacheableObject> highresMemoryCache;

    private JcsCache() {

        try {
            highresMemoryCache = JCS.getInstance( cacheRegionName );
        } catch ( CacheException ex ) {
            LOGGER.severe( ex.getLocalizedMessage() );
        }
    }

    /**
     * Returns the instance of the JpoCache singleton
     *
     * @return the instance of the cache object
     */
    public static JcsCache getInstance() {
        return JpoCacheHolder.INSTANCE;

    }

    /**
     * Singleton
     */
    private static class JpoCacheHolder {

        private static final JcsCache INSTANCE = new JcsCache();
    }

    /**
     * Returns a text from the JCS with statistics on the cache
     *
     * @return a test with statistics from the cache
     */
    public String getHighresCacheStats() {
        return highresMemoryCache.getStats();
    }


    public CacheAccess<String, CacheableObject> getCacheAccessForTesting() {
        return highresMemoryCache;
    }

}
