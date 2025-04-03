package cc.rcbb.mini.spring.test.req;

/**
 * <p>
 * TestReq
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/3
 */
public class TestReq {

    private String key;

    private Integer value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestReq{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
