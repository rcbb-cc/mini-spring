package cc.rcbb.mini.spring.batis;

/**
 * <p>
 * SqlSessionFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/21
 */
public interface SqlSessionFactory {

    SqlSession openSession();

    MapperNode getMapperNode(String name);

}
