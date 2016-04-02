package com.jfixby.red.android.filesystem;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileHash;
import com.jfixby.cmns.api.file.FileInputStream;
import com.jfixby.cmns.api.file.FileOutputStream;
import com.jfixby.cmns.api.file.FileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.path.AbsolutePath;
import com.jfixby.cmns.api.util.path.RelativePath;
import com.jfixby.red.filesystem.AbstractRedFile;
import com.jfixby.red.filesystem.FilesList;
import com.jfixby.red.filesystem.RedFileHash;

public class AndroidFile extends AbstractRedFile implements File {

    final private AbsolutePath<FileSystem> absolute_path;
    private AndroidFileSystem fs;
    private String absolute_path_string;

    public AndroidFile(AbsolutePath<FileSystem> output_file_path, AndroidFileSystem windowsFileSystem) {
	this.absolute_path = output_file_path;
	this.fs = windowsFileSystem;
	absolute_path_string = this.getAbsoluteWindowsPathString();
    }

    @Override
    public AbsolutePath<FileSystem> getAbsoluteFilePath() {
	return this.absolute_path;
    }

    @Override
    public boolean isFile() {
	java.io.File f = new java.io.File(this.getAbsoluteWindowsPathString());
	return f.isFile();
    }

    @Override
    public long lastModified() {
	java.io.File f = new java.io.File(this.getAbsoluteWindowsPathString());
	return f.lastModified();
    }

    @Override
    public boolean isFolder() {
	java.io.File f = new java.io.File(this.getAbsoluteWindowsPathString());
	return f.isDirectory();
    }

    @Override
    public boolean exists() {
	java.io.File f = new java.io.File(this.getAbsoluteWindowsPathString());
	return f.exists();
    }

    @Override
    public boolean rename(String new_name) {
	java.io.File f = new java.io.File(this.getAbsoluteWindowsPathString());
	AndroidFile new_file = new AndroidFile(this.absolute_path.parent().child(new_name), this.fs);
	return f.renameTo(new java.io.File(new_file.getAbsoluteWindowsPathString()));
    }

    @Override
    public boolean makeFolder() {
	java.io.File f = new java.io.File(this.getAbsoluteWindowsPathString());
	return f.mkdirs();
    }

    @Override
    public boolean delete() {
	if (this.isFolder()) {
	    this.clearFolder();
	}
	java.io.File f = new java.io.File(getAbsoluteWindowsPathString());

	return f.delete();

	// boolean result = f.delete();
	// L.d("result", result);
    }

    public String getAbsoluteWindowsPathString() {
	String mount_point_path_string = "";
	String relative = toNativePathString(absolute_path.getRelativePath().getPathString());
	if (relative.length() > 0) {
	    relative = AndroidFileSystem.OS_SEPARATOR + relative;
	}
	return AndroidFileSystem.OS_SEPARATOR + mount_point_path_string + relative;
    }

    public static String toNativePathString(String string) {
	return string.replaceAll(RelativePath.SEPARATOR, AndroidFileSystem.OS_SEPARATOR);
    }

    @Override
    public ChildrenList listChildren() {
	java.io.File file = new java.io.File(getAbsoluteWindowsPathString());
	if (!file.exists()) {
	    throw new Error("File does not exist: " + file);
	}
	if (file.isDirectory()) {
	    String[] list = file.list();

	    List<String> files = Collections.newList(list);
	    FilesList listFiles = new FilesList();
	    for (int i = 0; i < files.size(); i++) {
		String file_i = files.getElementAt(i);
		//
		// String parent =
		// absolute_path.getRelativePath().getPathString();
		// RelativePath relative = RedTriplane.Java().newRelativePath(
		// parent + RelativePath.SEPARATOR + file_i);
		// AbsolutePath absolute_file = new WinAbsolutePath(
		// (WinMountPoint) absolute_path.getMountPoint(), relative);

		AbsolutePath<FileSystem> absolute_file = absolute_path.child(file_i);
		listFiles.add(absolute_file.getMountPoint().newFile(absolute_file));
	    }
	    // L.d("listFiles", listFiles);

	    //
	    return listFiles;
	} else {
	    throw new Error("This is not a folder: " + this.absolute_path);
	}
    }

    @Override
    public void clearFolder() {
	if (this.isFolder()) {
	    ChildrenList children = listChildren();
	    for (int i = 0; i < children.size(); i++) {
		// WinFile file = new WinFile(child);
		File child = children.getElementAt(i);
		child.delete();
		// L.d("deleting", child.getAbsoluteFilePath());
	    }
	} else {
	    L.e("Unable to clear", absolute_path);
	    L.e("       this is not a folder.");
	}
    }

    @Override
    public String toString() {
	return AndroidFileSystem.OS_SEPARATOR + absolute_path + "";
    }

    @Override
    public File child(String child_name) {
	return new AndroidFile(this.getAbsoluteFilePath().child(child_name), this.getFileSystem());
    }

    @Override
    public String getName() {
	java.io.File f = new java.io.File(this.getAbsoluteWindowsPathString());
	return f.getName();
    }

    @Override
    public AndroidFileSystem getFileSystem() {
	return fs;
    }

    @Override
    public String nameWithoutExtension() {
	java.io.File file = new java.io.File(this.getAbsoluteWindowsPathString());
	String name = file.getName();
	int dotIndex = name.lastIndexOf('.');
	if (dotIndex == -1)
	    return name;
	return name.substring(0, dotIndex);
    }

    public String toAbsolutePathString() {
	return this.getAbsoluteWindowsPathString();
    }

    @Override
    public FileHash calculateHash() throws IOException {
	return new RedFileHash(this.fs.md5Hex(this));
    }

    @Override
    public FileInputStream newInputStream() throws IOException {
	return absolute_path.getMountPoint().newFileInputStream(this);
    }

    @Override
    public FileOutputStream newOutputStream() throws IOException {
	return absolute_path.getMountPoint().newFileOutputStream(this);
    }

    public java.io.File getJavaFile() {
	java.io.File file = new java.io.File(this.getAbsoluteWindowsPathString());
	return file;
    }

    @Override
    public long getSize() {
	java.io.File file = new java.io.File(this.getAbsoluteWindowsPathString());
	if (file.isFile()) {
	    return file.length();
	} else {
	    return 0;
	}
    }

    @Override
    public java.io.File toJavaFile() {
	java.io.File f = new java.io.File(this.getAbsoluteWindowsPathString());
	return f;
    }

    @Override
    public File parent() {
	if (!this.absolute_path.isRoot()) {
	    return new AndroidFile(this.absolute_path.parent(), this.fs);
	}
	throw new Error("This is already a root file. No parent available: " + this);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((absolute_path_string == null) ? 0 : absolute_path_string.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AndroidFile other = (AndroidFile) obj;
	if (absolute_path_string == null) {
	    if (other.absolute_path_string != null)
		return false;
	} else if (!absolute_path_string.equals(other.absolute_path_string))
	    return false;
	return true;
    }

}
