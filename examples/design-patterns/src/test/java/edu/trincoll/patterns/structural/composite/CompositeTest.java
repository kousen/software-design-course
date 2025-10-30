package edu.trincoll.patterns.structural.composite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Composite Pattern Tests")
class CompositeTest {

    @Test
    @DisplayName("File has correct name and size")
    void fileHasCorrectNameAndSize() {
        File file = new File("test.txt", 100);

        assertThat(file.getName()).isEqualTo("test.txt");
        assertThat(file.getSize()).isEqualTo(100);
    }

    @Test
    @DisplayName("Empty directory has zero size")
    void emptyDirectoryHasZeroSize() {
        Directory dir = new Directory("empty");

        assertThat(dir.getSize()).isZero();
    }

    @Test
    @DisplayName("Directory with files calculates total size")
    void directoryWithFilesCalculatesTotalSize() {
        Directory dir = new Directory("documents");
        dir.add(new File("file1.txt", 100));
        dir.add(new File("file2.txt", 200));

        assertThat(dir.getSize()).isEqualTo(300);
    }

    @Test
    @DisplayName("Nested directories calculate total size recursively")
    void nestedDirectoriesCalculateSizeRecursively() {
        Directory root = new Directory("root");
        Directory subDir = new Directory("subdir");

        root.add(new File("file1.txt", 100));
        root.add(subDir);

        subDir.add(new File("file2.txt", 200));
        subDir.add(new File("file3.txt", 150));

        assertThat(root.getSize()).isEqualTo(450);
        assertThat(subDir.getSize()).isEqualTo(350);
    }

    @Test
    @DisplayName("Can treat files and directories uniformly")
    void treatFilesAndDirectoriesUniformly() {
        FileSystemElement file = new File("test.txt", 100);
        FileSystemElement dir = new Directory("test");

        // Both have same interface
        assertThat(file).isInstanceOf(FileSystemElement.class);
        assertThat(dir).isInstanceOf(FileSystemElement.class);

        // Both have getName() and getSize()
        assertThat(file.getName()).isNotNull();
        assertThat(dir.getName()).isNotNull();
    }

    @Test
    @DisplayName("Remove file from directory")
    void removeFileFromDirectory() {
        Directory dir = new Directory("documents");
        File file1 = new File("file1.txt", 100);
        File file2 = new File("file2.txt", 200);

        dir.add(file1);
        dir.add(file2);
        assertThat(dir.getSize()).isEqualTo(300);

        dir.remove(file1);
        assertThat(dir.getSize()).isEqualTo(200);
    }
}
