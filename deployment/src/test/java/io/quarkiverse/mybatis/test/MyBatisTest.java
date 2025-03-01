package io.quarkiverse.mybatis.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSessionFactory;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

public class MyBatisTest {
    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest().withConfigurationResource("application.properties")
            .setArchiveProducer(
                    () -> ShrinkWrap.create(JavaArchive.class)
                            .addClasses(UserMapper.class, User.class, UuidTypeHandler.class, UuidJdbcTypeHandler.class));

    @Inject
    UserMapper userMapper;

    @Inject
    SqlSessionFactory sqlSessionFactory;

    @Test
    public void test() throws Exception {
        assertTrue(sqlSessionFactory.getConfiguration().getMapperRegistry().hasMapper(UserMapper.class));
        User user = userMapper.getUser(1);
        assertEquals(user.getId(), 1);
        assertEquals(user.getName(), "Test User1");
        assertEquals(user.getExternalId(), UUID.fromString("8c5034fe-1a00-43b7-9c75-f83ef14e3507"));
    }
}
