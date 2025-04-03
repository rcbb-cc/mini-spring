package cc.rcbb.mini.spring.beans;

/**
 * <p>
 * PropertyEditor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/2
 */
public interface PropertyEditor {

    void setAsText(String text);

    void setValue(Object value);

    Object getValue();

    String getAsText();

}
