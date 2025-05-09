package cc.rcbb.mini.spring.test;

import java.time.LocalDate;

/**
 * <p>
 * TestUser
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/27
 */
public class TestUser {

    private String id;
    private String name;
    private Integer age;
    private LocalDate birthday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAge(String age) {
        this.age = Integer.valueOf(age);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
