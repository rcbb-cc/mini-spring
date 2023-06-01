package cc.rcbb.mini.spring.beans.factory.config;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * ArgumentValues
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/4
 */
public class ConstructorArgumentValues {

    private final List<ConstructorArgumentValue> constructorArgumentValueList = new LinkedList<>();

    public ConstructorArgumentValues() {
    }

    public void addArgumentValue(ConstructorArgumentValue constructorArgumentValue) {
        this.constructorArgumentValueList.add(constructorArgumentValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        ConstructorArgumentValue constructorArgumentValue = this.constructorArgumentValueList.get(index);
        return constructorArgumentValue;
    }

    public int getArgumentCount() {
        return (this.constructorArgumentValueList.size());
    }

    public boolean isEmpty() {
        return (this.constructorArgumentValueList.isEmpty());
    }

}
