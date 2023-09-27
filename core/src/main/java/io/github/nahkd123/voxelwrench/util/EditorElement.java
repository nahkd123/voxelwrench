package io.github.nahkd123.voxelwrench.util;

/**
 * <p>Classes that implements this interface will be rendered in the editor.</p>
 */
public interface EditorElement {
	public int getEditorX();
	public void setEditorX(int value);
	public int getEditorY();
	public void setEditorY(int value);
	public int getEditorWidth();
	public void setEditorWidth(int value);
	public int getEditorHeight();
	public void setEditorHeight(int value);
}
