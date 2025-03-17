package cc.rcbb.mini.spring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ArgumentValues
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/17
 */
public class ArgumentValues {

    private final List<ArgumentValue> argumentValueList = new ArrayList<ArgumentValue>();

    public ArgumentValues() {
    }

    public void addArgumentValue(ArgumentValue argumentValue) {
        this.argumentValueList.add(argumentValue);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        ArgumentValue argumentValue = this.argumentValueList.get(index);
        return argumentValue;
    }

    public int getArgumentCount() {
        return this.argumentValueList.size();
    }

    public boolean isEmpty() {
        return this.argumentValueList.isEmpty();
    }
}
