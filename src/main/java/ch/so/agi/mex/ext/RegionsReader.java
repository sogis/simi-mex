package ch.so.agi.mex.ext;

import java.util.List;

public interface RegionsReader {

    /**
     * Reads and returns all region Features of the given ThemePublication
     */
    String readRegionsForThemePub(ThemePublication themePub);
}
