package com.cat.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class FileUtils {

    public static String packToPath(String packageDir) {
        return packageDir.replace('.', '/');
    }

    public static String getRootPath() {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("");
            if (url != null) {
                Path path = Paths.get(url.toURI());
                return path.toString().replace("target\\classes", "");
            }

            //return new File(".").getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getClassPath() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        return url == null ? null : uRLToString(url);
    }

    //create if not exist
    public static Path createFile(String first, String... more) {
        return createFile(getPath(first, more));
    }

    public static Path createFile(Path path) {
        if (path == null) {
            return null;
        }
        if (Files.exists(path)) {
            return path;
        }
        try {
            Path parent = path.getParent();
            if (parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }

            Files.createFile(path);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Path getPath(String first, String... more) {
        if (first == null) {
            return null;
        }

        first = first.replaceAll("file:/", "");

        return Paths.get(first, more);
    }

    public static boolean write(File file, String content, boolean cover) {
        return write(file.toPath(), content, cover);
    }

    public static boolean write(Path path, String content, boolean cover) {
        if (path == null || content == null) {
            return false;
        }
        if (!Files.exists(path)) {
            createFile(path);
            System.err.println("file: " + path.getFileName() + " creating...");
        } else {
            System.err.println("file: " + path.getFileName() + " has exists!");
            if (!cover) {
                return true;
            } else {
                System.err.println("file: " + path.getFileName() + " will be overwrite!");
            }
        }
        if (!Files.exists(path)) {
            return false;
        }
        try {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getClassDir(Class<?> clazz) {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        return url == null ? null : uRLToString(url);
    }

    private static String uRLToString(URL url) {
        if (url == null) {
            return null;
        }
        try {
            return new File(new URI(url.toString())).getCanonicalPath();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<Class<?>> scanPackage(String dir) {
        Set<Class<?>> set = new HashSet<>();

        String path = dir.replace('.', '/');
        URL url = ClassLoader.getSystemClassLoader().getResource(path);
        if (url == null) {
            throw new RuntimeException("No resource for " + path);
        }

        File directory;
        try {
            directory = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(dir + " (" + url + ") does not appear to be a valid URL / URI.", e);
        } catch (IllegalArgumentException e) {
            directory = null;
        }

        //class file
        if (directory != null && directory.exists()) {
            String[] files = directory.list();
            if (files != null && files.length > 0) {
                String suffix = ".class";
                for (String file : files) {
                    if (file.endsWith(suffix)) {
                        String clazz = dir + '.' + file.substring(0, file.length() - suffix.length());
                        try {
                            set.add(Class.forName(clazz));
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("ClassNotFoundException loading " + clazz);
                        }
                    }
                }
            }
            return set;
        }

        //jar
        path = url.getFile();
        JarFile jarFile = null;
        try {
            String jarPath = path.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
            jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.startsWith(path) && entryName.length() > (path.length() + "/".length())) {
                    String clazz = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
                    try {
                        set.add(Class.forName(clazz));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("ClassNotFoundException loading " + clazz);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(dir + " (" + directory + ") does not appear to be a valid package", e);
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return set;
    }

}
