package forte.test;

import java.util.function.Supplier;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum TestEm {

    EN1(() -> "这是一个EN1"),
    EN2(() -> "咱是EN2")
    ;

    public final Supplier<String> sup;
    TestEm (Supplier<String> sup) {
        this.sup = sup;
    }
    TestEm(){
        this.sup = () -> "default";
    }



}
