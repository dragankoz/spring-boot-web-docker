package org.demostuff;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class ManifestInfoContributor implements InfoContributor, ServletContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(ManifestInfoContributor.class);

    private static final String IMPLEMENTATION_VENDOR_MATCH = "Demo Stuff";
    private static final String ERROR = "ERROR";

    private static ServletContext sc;


    @Override
    public void setServletContext(final ServletContext servletContext) {
        sc = servletContext;
    }

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> manifestDetails = new TreeMap<>();

        Map<String, Map<String, String>> contextManifest = readContextManifest();
        for (Map.Entry<String, Map<String, String>> entry : contextManifest.entrySet()) {
            for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                manifestDetails.put(String.format("%s:%s", entry.getKey(), innerEntry.getKey()), innerEntry.getValue());
            }
        }
        builder.withDetail("manifest", manifestDetails);
    }

    private Map<String, Map<String, String>> readContextManifest() {
        // Return a map of maps, this way we can display info for multiple jars
        Map<String, Map<String, String>> returnedMap = new TreeMap<>();

        // Get the manifest from this executable jar
        LOG.info("Getting {}", JarFile.MANIFEST_NAME);
        Map<String, String> manifestMap = new TreeMap<>();

        try (InputStream warInputStream = sc.getResourceAsStream("/" + JarFile.MANIFEST_NAME)) {
        //try (InputStream localJarInputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(JarFile.MANIFEST_NAME);) {
            addEntry(returnedMap, warInputStream, manifestMap);
            readEmbeddedJarManifests(returnedMap);
        } catch (Exception e) {
            String message = String.format("Could not read application [%s] : %s", JarFile.MANIFEST_NAME, e.getMessage());
            LOG.warn(message);
            manifestMap.put(ERROR, message);
            returnedMap.put(ERROR, manifestMap);
        }

        return returnedMap;
    }

    private void readEmbeddedJarManifests(Map<String, Map<String, String>> returnedMap) throws IOException {
        // Read other libraries included in this executable jar
        // Xml escape any values since that what we'll ultimately be returning
        Enumeration<URL> resources = getClass().getClassLoader().getResources(JarFile.MANIFEST_NAME);
        while (resources.hasMoreElements()) {
            URL element = resources.nextElement();
            Map<String, String> jarManifestMap = new TreeMap<>();
            try (InputStream jarInputStream = element.openStream()) {
                addEntry(returnedMap, jarInputStream, jarManifestMap);
            } catch (IOException e) {
                String message = String.format("Could not read manifest jar [%s] from URL [%s] : %s",
                        JarFile.MANIFEST_NAME, element.toExternalForm(), e.getMessage());
                LOG.warn(message);
                jarManifestMap.put(ERROR, message);
                returnedMap.put(ERROR, jarManifestMap);
            }
        }
    }

    private void addEntry(Map<String, Map<String, String>> returnedMap, Manifest jarManifest, Map<String, String> jarManifestMap) {
        String implementationTitle = jarManifest.getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_TITLE);
        String implementationVendor = jarManifest.getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
        if (implementationTitle != null && (implementationTitle + implementationVendor).toUpperCase().contains(IMPLEMENTATION_VENDOR_MATCH)) {
            addToMap(returnedMap, implementationTitle, jarManifestMap, jarManifest);
        }
    }

    private void addEntry(Map<String, Map<String, String>> returnedMap, InputStream jarInputStream, Map<String, String> jarManifestMap) throws IOException {
        Manifest jarManifest = new Manifest(jarInputStream);
        String implementationTitle = jarManifest.getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_TITLE);
        String implementationVendor = jarManifest.getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
        if (implementationTitle != null && (implementationTitle + implementationVendor).toUpperCase().contains(IMPLEMENTATION_VENDOR_MATCH)) {
            addToMap(returnedMap, implementationTitle, jarManifestMap, jarManifest);
        }
    }

    private void addToMap(Map<String, Map<String, String>> returnedMap, String name, Map<String, String> jarManifestMap, Manifest manifest) {
        for (Map.Entry<Object, Object> entry : manifest.getMainAttributes().entrySet()) {
            jarManifestMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        returnedMap.put(name, jarManifestMap);
    }

}
