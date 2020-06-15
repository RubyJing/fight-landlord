package role;

/**
 * 农民
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 10:59
 */
public class Farmer extends Role {
    @Override
    public Long getId() {
        return 1L;
    }

    @Override
    public String getRoleName() {
        return "农民";
    }
}
